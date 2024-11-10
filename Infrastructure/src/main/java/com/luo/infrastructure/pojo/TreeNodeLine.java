package com.luo.infrastructure.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class TreeNodeLine {


    private Integer id;

    private String nodeNameFrom;

    private String nodeNameTo;

    private String nodeLimitResult;

    private String treeId;

    private String nodeLineDesc;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
