package com.jonsson.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jonsson.dao.CategoryDao;
import com.jonsson.dao.RoleMenuListDao;
import com.jonsson.entity.Category;
import com.jonsson.entity.vo.CategoryVO;
import com.jonsson.security.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CategoryService extends ServiceImpl<CategoryDao, Category> {
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private RoleMenuListDao roleMenuListDao;
    @Autowired
    private RoleService roleService;

    public IPage<Category> selectPage(CategoryVO categoryVO) {
        List<Long> longs = userService.selectChild(SecurityUtil.getCurrentUser().getId(), true);
        return lambdaQuery().like(StrUtil.isNotBlank(categoryVO.getName()), Category::getName, categoryVO.getName())
                .in(Category::getCreator, longs)
                .eq(null != categoryVO.getState(), Category::getState, categoryVO.getState())
                .eq(null != categoryVO.getParentId(), Category::getParentId, categoryVO.getParentId())
                .between(null != categoryVO.getStartTime() && null != categoryVO.getEndTime(), Category::getCreateTime, categoryVO.getStartTime(), categoryVO.getCreateTime())
                .orderByDesc(Category::getCreateTime)
                .page(new Page<>(categoryVO.getPageNum(), categoryVO.getPageSize()));
    }

    /**
     * 查询子集,返回非嵌套数据结构
     *
     * @param id
     * @param bool 是否包含自己
     * @return
     */
    public List<Category> selectList(Long id, Boolean bool) {
        Category category = getById(id);
        List<Category> categories = selectByPath(category.getId(), category.getPath());
        if (bool) categories.add(category);
        return categories;
    }

    /**
     * 查询子集,不包含自己,返回非嵌套数据结构
     *
     * @param id   分类的id
     * @param path 分类的路径
     * @return
     */
    public List<Category> selectByPath(Long id, String path) {
        return categoryDao.selectByPath((path == null ? "" : path) + id + "-");
    }

    /**
     * 查询子集,返回嵌套数据结构
     *
     * @return
     */
    public List<Category> selectChilds(Long creator) {
        List<Long> longs = userService.selectChild(creator, true);
        List<Category> categories = lambdaQuery()
                .or(categoryLambdaQueryWrapper -> categoryLambdaQueryWrapper.isNull(Category::getParentId).or().eq(Category::getParentId, ""))
                .in(Category::getCreator, longs)
                .list();
        for (Category category : categories) {
            List<Category> categories1 = selectChild(category.getId());
            category.setCategorys(categories1);
        }
        return categories;
    }

    /**
     * 查询子集,不包含自己,返回嵌套数据结构
     *
     * @param id
     * @return
     */
    public List<Category> selectChild(Long id) {
        List<Category> categoryList = lambdaQuery().eq(Category::getParentId, id).list();
        if (CollectionUtil.isEmpty(categoryList)) return null;
        for (Category category1 : categoryList) {
            List<Category> categoryList1 = selectChild(category1.getId());
            category1.setCategorys(categoryList1);
        }
        return categoryList;
    }
}