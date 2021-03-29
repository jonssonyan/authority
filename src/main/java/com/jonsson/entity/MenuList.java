package com.jonsson.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@TableName("`menu_list`")
public class MenuList implements Serializable {
    private static final long serialVersionUID = 6371298069578228652L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long parentId;
    private String name;
    private String icon;
    private Integer priority;
    private String router;
    private Integer state;
    private Date createTime;
    private Date updateTime;

    @TableField(exist = false)
    private List<MenuList> menuLists;
}
