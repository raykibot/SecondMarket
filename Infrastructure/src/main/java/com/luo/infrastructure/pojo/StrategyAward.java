package com.luo.infrastructure.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class StrategyAward {


    private Integer id;

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
