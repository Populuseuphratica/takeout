package com.killstan.takeout.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.killstan.takeout.entity.po.Combo;
import com.killstan.takeout.entity.vo.ComboVo;
import com.killstan.takeout.entity.vo.ResultVo;

/**
 * <p>
 * 套餐表 服务类
 * </p>
 *
 * @author killStan
 * @since 2022-10-12
 */
public interface ComboService extends IService<Combo> {

    /**
     * 获取套餐列表
     * @param page 当前页
     * @param pageSize 页面大小
     * @param comboName 套餐名，可为空
     * @return
     */
    ResultVo<IPage<ComboVo>> listCombo(Integer page, Integer pageSize, String comboName);

    /**
     * 添加套餐
     * @param comboVo
     * @return
     */
    ResultVo addCombo(ComboVo comboVo);
}
