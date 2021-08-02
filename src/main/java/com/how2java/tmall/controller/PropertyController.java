package com.how2java.tmall.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Property;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.PropertyService;
import com.how2java.tmall.util.Page;

@Controller
@RequestMapping("")
public class PropertyController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    PropertyService propertyService;

    @RequestMapping("admin_property_add")
    public String add(Model model, Property p) {
        propertyService.add(p);
        return "redirect:admin_property_list?cid="+p.getCid();
    }

    @RequestMapping("admin_property_delete")
    public String delete(int id) {
        Property p = propertyService.get(id);
        propertyService.delete(id);
        return "redirect:admin_property_list?cid="+p.getCid();
    }

    @RequestMapping("admin_property_edit")
    public String edit(Model model, int propertyId) {
        Property p = propertyService.get(propertyId);
        Category c = categoryService.get(p.getCid());
        p.setCategory(c);
        model.addAttribute("p", p);
        return "admin/editProperty";
    }

    @RequestMapping("admin_property_update")
    public String update(Property p) {
        propertyService.update(p);
        return "redirect:admin_property_list?cid="+p.getCid();
    }

    @RequestMapping("admin_property_list")
    public String list(int cid, Model model,  Page page) {
        Category c = categoryService.get(cid);

        //pagehelp 插件 只对紧跟后的一条list有效
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Property> propertyList = propertyService.list(cid);

        int total = (int) new PageInfo<>(propertyList).getTotal();
        System.out.println("the total value:"+ total);
        page.setTotal(total);
        page.setParam("&cid="+c.getId());

        model.addAttribute("ps", propertyList);
        model.addAttribute("c", c);
        model.addAttribute("page", page);

        return "admin/listProperty";
    }

}
