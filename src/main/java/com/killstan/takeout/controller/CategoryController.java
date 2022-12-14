package com.killstan.takeout.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.killstan.takeout.entity.po.Category;
import com.killstan.takeout.entity.po.Combo;
import com.killstan.takeout.entity.po.Dish;
import com.killstan.takeout.entity.vo.ResultVo;
import com.killstan.takeout.service.CategoryService;
import com.killstan.takeout.service.ComboService;
import com.killstan.takeout.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    private final CategoryService categoryService;

    private final ComboService comboService;

    private final DishService dishService;

    @Autowired
    public CategoryController(CategoryService categoryService, ComboService comboService, DishService dishService) {
        this.categoryService = categoryService;
        this.comboService = comboService;
        this.dishService = dishService;
    }

    /**
     * 获取分类列表
     *
     * @param page     当前页
     * @param pageSize 页面大小
     * @return
     */
    @GetMapping("/page")
    public ResultVo<IPage<Category>> listCategory(Integer page, Integer pageSize) {
        Page<Category> page1 = new Page<>();
        page1.addOrder(OrderItem.asc("sort"));
        IPage<Category> categoryPage = categoryService.page(page1);

        return ResultVo.success(categoryPage);
    }

    /**
     * 添加分类
     *
     * @param category
     * @return
     */
    @PostMapping
    public ResultVo addCategory(@RequestBody Category category) {
        categoryService.save(category);
        return ResultVo.success(null);
    }

    /**
     * 修改分类
     *
     * @param category
     * @return
     */
    @PutMapping
    public ResultVo updateCategory(@RequestBody Category category) {
        categoryService.updateById(category);
        return ResultVo.success(null);
    }

    /**
     * 删除分类
     * 如果该分类下有菜品或套餐，则不删除
     *
     * @param categoryId
     * @return
     */
    @DeleteMapping
    public ResultVo deleteCategory(@RequestParam("id") Long categoryId) {
        LambdaQueryWrapper<Combo> comboQueryWrapper = new LambdaQueryWrapper();
        comboQueryWrapper.eq(Combo::getCategoryId, categoryId);
        int comboCount = comboService.count(comboQueryWrapper);

        if (comboCount > 0) {
            return ResultVo.fail("该分类有绑定的套餐，无法删除");
        }

        LambdaQueryWrapper<Dish> dishQueryWrapper = new LambdaQueryWrapper();
        dishQueryWrapper.eq(Dish::getCategoryId, categoryId);
        int dishCount = dishService.count(dishQueryWrapper);

        if (dishCount > 0) {
            return ResultVo.fail("该分类有绑定的菜品，无法删除");
        }

        boolean b = categoryService.removeById(categoryId);

        if (b) {
            return ResultVo.success(null);
        }
        return ResultVo.fail("删除失败，请刷新后重试");
    }

    /**
     * 根据分类类型获取分类
     *
     * @param cgType
     * @return 分类list
     */
    @GetMapping("/list")
    public ResultVo<List<Category>> getCategoryByType(@RequestParam(value = "cgType", required = false) Integer cgType) {
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(cgType != null, Category::getCgType, cgType);
        lambdaQueryWrapper.orderByAsc(Category::getSort);
        List<Category> list = categoryService.list(lambdaQueryWrapper);
        return ResultVo.success(list);
    }


}

