package com.luo.test.raffle;

import com.luo.domain.service.AbstractRaffleService;
import com.luo.domain.service.tree.impl.LockNode;
import com.luo.domain.service.tree.impl.StockNode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Slf4j
public class RaffleTest {

    @Autowired
    private AbstractRaffleService abstractRaffleService;
    @Autowired
    private LockNode lockNode;

    @Test
    public void raffleTest(){


        for (int i = 0; i < 100;i++) {
            ReflectionTestUtils.setField(lockNode,"raffleCount",6);
            Integer awardId = abstractRaffleService.abstractRaffle("luo", 100001);
            log.info("抽奖结果:{}",awardId);
        }


    }

}
