package com.killstan.takeout.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.killstan.takeout.entity.po.Dish;
import com.killstan.takeout.entity.vo.DishVo;
import com.killstan.takeout.entity.vo.ResultVo;

import java.util.List;

/**
 * <p>
 * 菜品 服务类
 * </p>
 *
 * @author killStan
 * @since 2022-10-12
 */
public interface DishService extends IService<Dish> {

    /**
     * 添加菜品
     *
     * @param dishVo
     * @return
     */
    public ResultVo addDish(DishVo dishVo);

    /**
     * 更新菜品
     *
     * @param dishVo
     * @return
     */
    ResultVo updateDish(DishVo dishVo);

    /**
     * 取得菜品列表
     *
     * @param page     当前页
     * @param pageSize 页面size
     * @param dishName 菜品名，可为 null
     * @return
     */
    ResultVo listDish(Integer page, Integer pageSize, String dishName);

    /**
     * 获取菜品信息，包含口味
     *
     * @param dishId 菜品id
     * @return
     */
    ResultVo getDishWithFlavorById(Long dishId);

    ResultVo<List<DishVo>> getDishByCategoryId(Long categoryId, String dishName, Integer status);
}
