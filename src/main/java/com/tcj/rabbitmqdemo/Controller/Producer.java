package com.tcj.rabbitmqdemo.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/ttl")
public class Producer {
    @Autowired
    RabbitTemplate rabbitTemplate;
    @GetMapping("/sendMsg/{message}")
    public void sendMsg(@PathVariable String message){
        log.info("当前时间：{}，发送给两个TTL队列：{}",new Date().toString(),message);

        //生产者使用普通交换机X 通过 信道XA 向普通队列QA发送消息
        rabbitTemplate.convertAndSend("normal.exchange","XA","消息来自ttl为10s的队列："+message);
        //生产者使用普通交换机X 通过 信道XB 向普通队列QB发送消息
        rabbitTemplate.convertAndSend("normal.exchange","XB","消息来自ttl为4s的队列："+message);

    }
}
