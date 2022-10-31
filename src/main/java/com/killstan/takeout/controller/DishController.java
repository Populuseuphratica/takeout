package com.killstan.takeout.controller;


import com.killstan.takeout.entity.vo.DishVo;
import com.killstan.takeout.entity.vo.ResultVo;
import com.killstan.takeout.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 菜品 前端控制器
 * </p>
 *
 * @author killStan
 * @since 2022-10-12
 */
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    /**
     * 添加菜品
     *
     * @param dishVo
     * @return 成功返回 date为 null 的 ResultVo
     */
    @PostMapping
    public ResultVo addDish(@RequestBody DishVo dishVo) {

        ResultVo resultVo = dishService.addDish(dishVo);

        return resultVo;
    }

    /**
     * 获取菜品 list
     * @param page 当前页
     * @param pageSize 页面大小
     * @param dishName 菜品名，可为空
     * @return
     */
    @GetMapping("/page")
    public ResultVo listDish(Integer page, Integer pageSize, @RequestParam(required = false) String dishName) {

        ResultVo resultVo = dishService.listDish(page, pageSize, dishName);

        return resultVo;
    }

    /**
     * 获取菜品信息
     * @param dishId 菜品id
     * @return
     */
    @GetMapping("{dishId}")
    public ResultVo getDishById(@PathVariable("dishId") Long dishId){

        ResultVo resultVo = dishService.getDishWithFlavorById(dishId);
        return resultVo;
    }

    /**
     * 更新菜品
     * @param dishVo
     * @return
     */
    @PutMapping()
    public ResultVo updateDish(@RequestBody DishVo dishVo){

        ResultVo resultVo = dishService.updateDish(dishVo);
        return resultVo;
    }
}

