package com.killstan.takeout.service.impl;

import com.killstan.takeout.entity.po.Category;
import com.killstan.takeout.mapper.po.CategoryMapper;
import com.killstan.takeout.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 分类表 服务实现类
 * </p>
 *
 * @author killStan
 * @since 2022-10-12
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}
