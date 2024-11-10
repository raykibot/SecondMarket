package com.luo.test.raffle;


import com.luo.domain.service.IRaffleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Slf4j
public class WeightRaffle {

    @Autowired
    private IRaffleService raffleService;

    @Test
    public void weightRaffle(){

        for (int i = 0; i < 100; i++) {
            Integer awardId = raffleService.raffleWithWeight(100001, 4500);

            log.info("抽奖结果：{}", awardId);
        }
    }


}
