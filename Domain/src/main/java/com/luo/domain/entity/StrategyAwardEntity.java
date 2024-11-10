package com.luo.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StrategyAwardEntity  {

    private Integer strategyId;

    private Integer awardId;

    private Integer sort;

    private Integer awardCount;

    private Integer awardSurplus;

    private BigDecimal rate;

    private String awardTitle;

    private String awardSubtitle;

    private String ruleModel;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
