package com.luo.test.raffle;

import com.luo.domain.service.IRaffleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Slf4j
public class NormalRaffle {


    @Autowired
    private IRaffleService raffleService;

    @Test
    public void raffle() {

        for (int i = 0; i < 100; i++) {
            Integer awardId = raffleService.raffle(100001);

            log.info("抽奖结果：{}", awardId);
        }

    }



}
