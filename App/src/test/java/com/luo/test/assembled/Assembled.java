package com.luo.test.assembled;

import com.luo.domain.service.assembled.IStrategyAssembled;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Slf4j
public class Assembled {


    @Autowired
    private IStrategyAssembled strategyAssembled;


    @Test
    public void assembleTest(){

        strategyAssembled.assembled(100001);

    }


}
