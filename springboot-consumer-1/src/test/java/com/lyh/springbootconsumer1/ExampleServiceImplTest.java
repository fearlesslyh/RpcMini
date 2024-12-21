package com.lyh.springbootconsumer1;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author 梁懿豪
 * @version 1.3
 */
@SpringBootTest
class ExampleServiceImplTest {

    @Resource
    private ServiceImpl exampleService;

    @Test
    void test1() {
        exampleService.test();
    }
}