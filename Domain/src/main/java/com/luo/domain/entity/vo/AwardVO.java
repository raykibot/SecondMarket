package com.luo.domain.entity.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AwardVO {

    private Integer awardId;

    private String awardSubtitle;

}
