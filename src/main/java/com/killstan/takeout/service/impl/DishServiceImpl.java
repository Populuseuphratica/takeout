package com.killstan.takeout.service.impl;

import com.killstan.takeout.entity.po.Dish;
import com.killstan.takeout.mapper.po.DishMapper;
import com.killstan.takeout.service.DishService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜品 服务实现类
 * </p>
 *
 * @author killStan
 * @since 2022-10-12
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

}
