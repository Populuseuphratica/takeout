package com.killstan.takeout.mapper.po;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.killstan.takeout.entity.po.Combo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.killstan.takeout.entity.vo.ComboVo;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 套餐表 Mapper 接口
 * </p>
 *
 * @author killStan
 * @since 2022-10-12
 */
@Repository
public interface ComboMapper extends BaseMapper<Combo> {

    IPage<ComboVo> listCombo(Page<ComboVo> comboVoPage, String comboName);
}
