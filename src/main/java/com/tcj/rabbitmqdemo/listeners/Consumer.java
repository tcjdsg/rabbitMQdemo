package com.tcj.rabbitmqdemo.listeners;

import com.rabbitmq.client.Channel;
import com.tcj.rabbitmqdemo.pojos.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.io.IOException;

@Component
@Slf4j
public class Consumer {
    /**
     * 消费者QD接收消息
     */
    @RabbitListener(queues = "QD")
    public void receiveD(Message message, Channel channel){
        String msg = new String(message.getBody());
        log.info("当前时间：{}，收到死信队列的消息：{}",new Date().toString(),msg);
    }
}

