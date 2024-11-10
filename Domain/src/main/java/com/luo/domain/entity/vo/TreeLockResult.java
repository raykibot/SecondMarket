package com.luo.domain.entity.vo;

import com.luo.domain.entity.NodeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TreeLockResult {


    private Integer awardId;

    private String nodeLimitResult;

    private String nodeNameTo;

    private String treeId;

}
