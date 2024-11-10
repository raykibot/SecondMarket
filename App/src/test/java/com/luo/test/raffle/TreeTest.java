package com.luo.test.raffle;

import com.luo.domain.entity.vo.TreeLockResult;
import com.luo.domain.service.tree.ILockTree;
import com.luo.domain.service.tree.factory.TreeFactory;
import com.luo.domain.service.tree.factory.engine.TreeEngine;
import com.luo.domain.service.tree.impl.LockNode;
import com.luo.domain.service.tree.impl.StockNode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Slf4j
public class TreeTest {


    @Autowired
    private TreeEngine treeEngine;
    @Autowired
    private LockNode lockNode;


    @Test
    public void treeTest(){

            ReflectionTestUtils.setField(lockNode,"raffleCount",6);
            TreeLockResult treeLockResult = treeEngine.treeEngine(100001, 105, "luo");
            log.info("抽奖结果:{}",treeLockResult);
    }


}
