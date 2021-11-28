package com.jonssonyan.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jonssonyan.entity.Category;
import com.jonssonyan.entity.vo.CategoryVo;

import java.util.List;

public interface CategoryService extends IService<Category> {
    /**
     * 分页查询分类
     *
     * @param categoryVO
     * @return
     */
    IPage<Category> selectPage(CategoryVo categoryVO);

    /**
     * 查询子集,返回非嵌套数据结构
     *
     * @param id
     * @param bool 是否包含自己
     * @return
     */
    List<Category> selectList(Long id, Boolean bool);

    /**
     * 查询子集,不包含自己,返回非嵌套数据结构
     *
     * @param id   分类的id
     * @param path 分类的路径
     * @return
     */
    List<Category> selectByPath(Long id, String path);

    /**
     * 查询子集,返回嵌套数据结构
     *
     * @return
     */
    List<Category> selectChilds(Long creator);

    /**
     * 查询子集,不包含自己,返回嵌套数据结构
     *
     * @param id
     * @return
     */
    List<Category> selectChild(Long id);
}
