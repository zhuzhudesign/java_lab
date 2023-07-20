package com.atguigu.springcloud.service.impl;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class PaymentHystrixService {
    public String paymentInfoOk(Integer id) {
        return "Thread container: " + Thread.currentThread().getName() + " paymentInfoOk, id: "
                + id + "\t" + "😂!";
    }

    @HystrixCommand(fallbackMethod = "paymentInfoTimeoutHandler", commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="3000")
    })
    public String paymentInfoTimeOut(Integer id) {
        int timeNumber = 2;
        try {
            TimeUnit.SECONDS.sleep(timeNumber);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return "Thread container: " + Thread.currentThread().getName() + "paymentInfoTimeout, id: "
                + id + "\t" + "😂" + " waste 2 seconds.";
    }

    public String paymentInfoTimeoutHandler(Integer id) {
        return "Thread container: " + Thread.currentThread().getName() + "paymentInfoTimeoutHandler, id: "
                + id + "\t" + "😭";
    }

    @HystrixCommand(fallbackMethod = "paymentCircuitBeakerFallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        if(id < 0) {
            throw new RuntimeException("***id can't be minus");
        }
        String serialNumber = IdUtil.simpleUUID();
        log.info("dfsfsdfsdsd");
        return Thread.currentThread().getName() + "\t" + "call success, serial number: " + serialNumber;
    }
    public String paymentCircuitBeakerFallback(@PathVariable("id") Integer id) {
        return "id can not be minus!";
    }
}
