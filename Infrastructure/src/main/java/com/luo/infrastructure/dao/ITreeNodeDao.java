package com.luo.infrastructure.dao;

import com.luo.infrastructure.pojo.Node;
import com.luo.infrastructure.pojo.TreeNodeLine;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ITreeNodeDao {


    String queryTreeId(String ruleModel);

    Node queryNodeEntity(Node node);

    List<TreeNodeLine> queryNodeLine(TreeNodeLine treeNodeLine);
}
