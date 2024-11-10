package com.luo.test;

import com.luo.domain.entity.StrategyEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Slf4j
public class ApiTest {




    @Test
    public void repositoryTest() {


        Map<Integer,Integer> map = new HashMap<>();

        map.put(1,1);
        map.put(2,2);
        map.put(3,3);
        map.put(4,4);

        Integer number = map.get(0);
        System.out.println(number);


    }

}
