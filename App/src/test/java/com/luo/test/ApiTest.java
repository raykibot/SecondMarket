package com.luo.test;

import com.luo.domain.entity.StrategyEntity;
import com.luo.domain.service.IStrategyService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Slf4j
public class ApiTest {



    @Autowired
    private IStrategyService service;

    @Test
    public void repositoryTest() {

        List<StrategyEntity> strategyEntities = service.queryStrategies();

        log.info("service:{}", strategyEntities);

    }

}
