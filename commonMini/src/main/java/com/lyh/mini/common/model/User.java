package com.lyh.mini.common.model;

import java.io.Serializable;

/**
 * @author 梁懿豪
 * @version 1.0
 */

/**
 * 用户模型
 */
public class User implements Serializable {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

}
