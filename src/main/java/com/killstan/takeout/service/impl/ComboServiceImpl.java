package com.killstan.takeout.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.killstan.takeout.entity.po.Combo;
import com.killstan.takeout.entity.po.ComboDish;
import com.killstan.takeout.entity.vo.ComboDishVo;
import com.killstan.takeout.entity.vo.ComboVo;
import com.killstan.takeout.entity.vo.ResultVo;
import com.killstan.takeout.mapper.po.ComboMapper;
import com.killstan.takeout.service.ComboDishService;
import com.killstan.takeout.service.ComboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 套餐表 服务实现类
 * </p>
 *
 * @author killStan
 * @since 2022-10-12
 */
@Service
public class ComboServiceImpl extends ServiceImpl<ComboMapper, Combo> implements ComboService {

    @Autowired
    private ComboMapper comboMapper;

    @Autowired
    private ComboDishService comboDishService;

    @Override
    public ResultVo<IPage<ComboVo>> listCombo(Integer page, Integer pageSize, String comboName) {
        Page<ComboVo> comboVoPage = new Page<>(page, pageSize);
        IPage<ComboVo> comboVoIPage = comboMapper.listCombo(comboVoPage, comboName);

        return ResultVo.success(comboVoIPage);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVo addCombo(ComboVo comboVo) {
        Combo combo = new Combo();
        combo.setIsDeleted(0);
        BeanUtils.copyProperties(comboVo, combo);
        comboMapper.insert(combo);

        Long comboId = combo.getComboId();

        List<ComboDish> comboDishes = comboVo.getComboDishes();
        comboDishes.forEach(comboDish -> comboDish.setComboId(comboId));
        comboDishService.saveBatch(comboDishes);

        return ResultVo.success(null);
    }

    /**
     * @Description: 获取套餐内菜品
     * @Param: [comboId]
     * @Return: com.killstan.takeout.entity.vo.ResultVo
     * @Author Kill_Stan
     * @Date 2022/12/25 15:50
     */
    @Override
    public ResultVo getDishByComboId(Long comboId) {
        ComboDishVo dishByComboId = comboMapper.getDishByComboId(comboId);
        System.out.println(dishByComboId);
        return ResultVo.success(dishByComboId);
    }

}
