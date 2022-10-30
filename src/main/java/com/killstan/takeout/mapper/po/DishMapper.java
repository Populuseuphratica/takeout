package com.killstan.takeout.mapper.po;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.killstan.takeout.entity.po.Dish;
import com.killstan.takeout.entity.vo.DishVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 菜品 Mapper 接口
 * </p>
 *
 * @author killStan
 * @since 2022-10-12
 */
@Repository
public interface DishMapper extends BaseMapper<Dish> {

    /**
     * 取得菜品 list
     * @param page
     * @param dishName 菜品名，可为 null
     * @return
     */
    IPage<DishVo> listDish(@Param("page") Page<DishVo> page, @Param("dishName") String dishName);

}
