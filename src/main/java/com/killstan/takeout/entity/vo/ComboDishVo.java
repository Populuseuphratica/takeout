package com.killstan.takeout.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 菜品
 * </p>
 *
 * @author killStan
 * @since 2022-10-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ComboDishVo {

    private List<DishVo> dishList;

    private Long comboId;

    private String comboName;

    private BigDecimal price;
}
