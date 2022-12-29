package com.killstan.takeout.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Sequence;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.killstan.takeout.entity.po.Dish;
import com.killstan.takeout.entity.po.Flavor;
import com.killstan.takeout.entity.vo.DishVo;
import com.killstan.takeout.entity.vo.ResultVo;
import com.killstan.takeout.mapper.po.DishMapper;
import com.killstan.takeout.service.DishService;
import com.killstan.takeout.service.FlavorService;
import com.killstan.takeout.util.ConstantUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    private final FlavorService flavorService;

    private final DishMapper dishMapper;

    private final RedisTemplate redisTemplate;

    @Autowired
    public DishServiceImpl(FlavorService flavorService, DishMapper dishMapper, RedisTemplate redisTemplate) {
        this.flavorService = flavorService;
        this.dishMapper = dishMapper;
        this.redisTemplate = redisTemplate;
    }

    /**
     * @Description: 添加菜品
     * @Param: [dishVo]
     * @Return: com.killstan.takeout.entity.vo.ResultVo
     * @Author Kill_Stan
     * @Date 2022/12/29 21:54
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVo addDish(DishVo dishVo) {

        // 根据雪花算法生成菜品 id
        Sequence sequence = new Sequence();
        long dishId = sequence.nextId();

        // 存入口味
        List<Flavor> flavors = dishVo.getFlavors();

        if (flavors != null && flavors.size() != 0) {
            Iterator<Flavor> flavorIterable = flavors.iterator();
            while (flavorIterable.hasNext()) {
                Flavor flavor = flavorIterable.next();
                String flavorName = flavor.getName();
                String flavorValue = flavor.getValue();
                if (StringUtils.hasLength(flavorName) && StringUtils.hasLength(flavorValue)) {
                    flavor.setDishId(dishId);
                    // 默认口味可使用
                    flavor.setIsDeleted(0);

                    // 数据量小，直接分条插入
                    flavorService.save(flavor);
                }
            }
        }

        // 保存菜品
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishVo, dish);
        dish.setDishId(dishId);
        dish.setSort(1);
        dish.setIsDeleted(0);
        save(dish);
        // 更新成功之后删除 redis 中该菜品分类的缓存
        redisTemplate.delete(ConstantUtil.REDIS_DISH_VO + dish.getCategoryId());

        return ResultVo.success(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVo updateDish(DishVo dishVo) {

        Long dishId = dishVo.getDishId();

        // 删除空口味
        List<Flavor> flavors = dishVo.getFlavors();
        if (flavors != null && flavors.size() != 0) {
            Iterator<Flavor> flavorIterable = flavors.iterator();
            while (flavorIterable.hasNext()) {
                Flavor flavor = flavorIterable.next();
                String flavorName = flavor.getName();
                String flavorValue = flavor.getValue();
                if (!(StringUtils.hasLength(flavorName) && StringUtils.hasLength(flavorValue))) {
                    flavorIterable.remove();
                } else {
                    flavor.setDishId(dishId);
                    flavor.setIsDeleted(0);
                }
            }
        }

        // 删除之前的口味
        LambdaQueryWrapper<Flavor> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Flavor::getDishId, dishId);
        flavorService.remove(lambdaQueryWrapper);

        // 保存口味
        flavorService.saveBatch(flavors);

        // 更新菜品
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishVo, dish);
        updateById(dish);
        // 更新成功之后删除 redis 中缓存
        redisTemplate.delete(ConstantUtil.REDIS_DISH_VO + dish.getCategoryId());

        return ResultVo.success(null);
    }

    @Override
    public ResultVo listDish(Integer page, Integer pageSize, String dishName) {

        Page<DishVo> pageDish = new Page<>(page, pageSize);
        IPage<DishVo> resultPage = dishMapper.listDish(pageDish, dishName);

        return ResultVo.success(resultPage);
    }

    @Override
    public ResultVo getDishWithFlavorById(Long dishId) {

        Dish dish = getById(dishId);

        LambdaQueryWrapper<Flavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Flavor::getDishId, dishId);
        List<Flavor> flavors = flavorService.list(lambdaQueryWrapper);

        DishVo dishVo = new DishVo();
        BeanUtils.copyProperties(dish, dishVo);
        dishVo.setFlavors(flavors);

        return ResultVo.success(dishVo);
    }

    /**
     * @Description: 根据分类获得菜品
     * @Param: [categoryId, dishName, status]
     * @Return: com.killstan.takeout.entity.vo.ResultVo<java.util.List < com.killstan.takeout.entity.vo.DishVo>>
     * @Author Kill_Stan
     * @Date 2022/12/4 15:43
     */
    @Override
    public ResultVo<List<DishVo>> getDishByCategoryId(Long categoryId, String dishName, Integer status) {
        // 如果有缓存，直接取
        if (categoryId != null && dishName == null) {
            Object obj = redisTemplate.opsForValue().get(ConstantUtil.REDIS_DISH_VO + categoryId);
            if (obj != null) {
                return ResultVo.success((List<DishVo>) obj);
            }
        }

        // 没有缓存，从数据库中查询
        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(categoryId != null, Dish::getCategoryId, categoryId)
                .eq(StringUtils.hasLength(dishName), Dish::getDishName, dishName)
                .eq(Dish::getIsDeleted, 0)
                .eq(Dish::getStatus, 1)
                .orderByAsc(Dish::getSort)
                .orderByDesc(Dish::getUpdateTime);

        List<Dish> list = this.list(lambdaQueryWrapper);
        List<DishVo> voList = new ArrayList<>();
        for (Dish dish : list) {
            DishVo dishVo = new DishVo();
            BeanUtils.copyProperties(dish, dishVo);
            // 带 status 参数说明是前台菜单页面调用，需要菜品口味
            if (status != null) {
                LambdaQueryWrapper<Flavor> lambdaQueryWrapperFlavor = new LambdaQueryWrapper<>();
                lambdaQueryWrapperFlavor.eq(Flavor::getDishId, dishVo.getDishId());
                List<Flavor> flavors = flavorService.list(lambdaQueryWrapperFlavor);
                dishVo.setFlavors(flavors);
            }
            voList.add(dishVo);
        }

        // 返回之前放入 redis 中缓存
        if (categoryId != null && dishName == null) {
            redisTemplate.opsForValue().set(ConstantUtil.REDIS_DISH_VO + categoryId, voList, Long.parseLong(ConstantUtil.REDIS_DATA_TIME), TimeUnit.DAYS);
        }

        return ResultVo.success(voList);
    }

}
