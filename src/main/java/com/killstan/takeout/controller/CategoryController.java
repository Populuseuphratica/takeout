package com.killstan.takeout.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.killstan.takeout.entity.po.Category;
import com.killstan.takeout.entity.vo.ResultVo;
import com.killstan.takeout.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 分类表 前端控制器
 * </p>
 *
 * @author killStan
 * @since 2022-10-12
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/page")
    public ResultVo<IPage<Category>> listCategory(Integer page,Integer pageSize){
        Page<Category> page1 = new Page<>();
        page1.addOrder(OrderItem.asc("sort"));
        IPage<Category> categoryPage = categoryService.page(page1);

        return ResultVo.success(categoryPage);
    }

    @PostMapping
    public ResultVo addCategory(@RequestBody Category category){
        categoryService.save(category);
        return ResultVo.success(null);
    }

}

