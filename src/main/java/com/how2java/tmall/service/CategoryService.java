package com.how2java.tmall.service;

import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.util.Page;
import java.util.List;

public interface CategoryService{
    //重构分页方式 废弃total方法和参数化的list
    //    int total();
    //    List<Category> list(Page page);

    List<Category> list();
    void add(Category category);
    void delete(int id);
    Category get(int id);
    void update(Category category);
}