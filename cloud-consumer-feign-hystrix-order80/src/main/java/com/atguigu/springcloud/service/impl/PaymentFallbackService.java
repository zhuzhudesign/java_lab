package com.atguigu.springcloud.service.impl;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentFeignHystrixService;
import org.springframework.stereotype.Service;

@Service
public class PaymentFallbackService implements PaymentFeignHystrixService {
    @Override
    public CommonResult<Payment> getPaymentById(Long id) {
        return null;
    }

    @Override
    public CommonResult create(Payment payment) {
        return null;
    }

    @Override
    public String paymentFeignTimeout() {
        return null;
    }

    @Override
    public String paymentInfoOk(Integer id) {
        return "fall back in impl";
    }

    @Override
    public String paymentInfoTimeout(Integer id) {
        return "fallback in impl";
    }
}
