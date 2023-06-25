package com.alibaba.seatasagatest;

import com.alibaba.fastjson.JSON;
import io.seata.saga.engine.StateMachineEngine;
import io.seata.saga.statelang.domain.StateMachineInstance;
import io.seata.spring.boot.autoconfigure.SeataAutoConfiguration;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

@SpringBootApplication(exclude = {SeataAutoConfiguration.class})
@ImportResource(locations = "classpath:spring/saga-mock-tc-test.xml")
public class SeataSagaTestApplication implements BeanFactoryAware {

    private static BeanFactory beanFactoryRef;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        beanFactoryRef = beanFactory;
    }

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(SeataSagaTestApplication.class, args);

        StateMachineEngine stateMachineEngine = beanFactoryRef.getBean("stateMachineEngine", StateMachineEngine.class);

        HashMap<String, Object> startParams = new HashMap<>();
        startParams.put("paramA","AAA");
        startParams.put("paramB","BBB");
        StateMachineInstance machineInstance = stateMachineEngine.start("QuickStart", null, startParams);
        System.out.println(JSON.toJSONString(machineInstance));

        new CountDownLatch(1).await();
    }

}
