package com.luo.test.raffle;

import com.luo.domain.entity.vo.ChainResult;
import com.luo.domain.service.chain.IChainFilter;
import com.luo.domain.service.chain.factory.ChainFactory;
import com.luo.domain.service.chain.impl.WeightFilterImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.ReflectionUtils;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Slf4j
public class ChainTest {


    @Autowired
    private ChainFactory chainFactory;
    @Autowired
    private WeightFilterImpl weightFilter;



    @Test
    public void chainTest() {

        ReflectionTestUtils.setField(weightFilter, "userWeight", 6500);

        for (int i = 0; i < 100; i++) {
            IChainFilter chainFilterComponent = chainFactory.getChainFilterComponent(100001);
            ChainResult luo = chainFilterComponent.logic("user00", 100001);
            log.info("抽奖结果：{}", luo);
        }
    }


}
