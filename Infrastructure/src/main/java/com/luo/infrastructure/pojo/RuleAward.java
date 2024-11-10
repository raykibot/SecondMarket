package com.luo.infrastructure.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class RuleAward {


    private Integer id;

    private Integer strategyId;

    private Integer awardId;

    private String ruleModel;

    private String ruleValue;

    private String ruleDesc;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
