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

    @GetMapping("/page")
    public ResultVo listDish(Integer page, Integer pageSize, @RequestParam(required = false) String dishName) {

        ResultVo resultVo = dishService.listDish(page, pageSize, dishName);

        return resultVo;
    }
}

