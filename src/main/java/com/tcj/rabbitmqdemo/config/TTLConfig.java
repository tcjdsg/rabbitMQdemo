package com.tcj.rabbitmqdemo.config;

import org.springframework.amqp.core.*;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TTLConfig {
    /**
     * 普通交换机名称
     */
    public static final String EXCHANGE_X = "normal.exchange";
    /**
     * 死信交换机名称
     */
    public static final String EXCHANGE_DEAD_Y = "dead.exchange";
    /**
     * 普通队列名称
     */
    public static final String QUEUE_A = "QA";
    public static final String QUEUE_B = "QB";
    /**
     * 死信队列名称
     */
    public static final String QUEUE_DEAD_D = "QD";

    // 创建普通交换机
    @Bean
    public DirectExchange directExchange(){
            return new DirectExchange(EXCHANGE_X);
    }

    // 创建普通队列，设置ttl为5秒，绑定死信交换机
    @Bean
    public Queue normalQueueA() {
        //声名参数
        Map<String, Object> arguments = new HashMap<>(3);
        //设置死信交换机
        arguments.put("x-dead-letter-exchange",EXCHANGE_DEAD_Y);
        //设置死信RoutingKey
        arguments.put("x-dead-letter-routing-key","YD");
        //设置 TTL 10s
        arguments.put("x-message-ttl",10000);

        return QueueBuilder.durable(QUEUE_A).withArguments(arguments).build();

    }
    @Bean
    public Queue normalQueueB() {
        Map<String, Object> arguments = new HashMap<>(3);
        //设置死信交换机
        arguments.put("x-dead-letter-exchange",EXCHANGE_DEAD_Y);
        //设置死信RoutingKey
        arguments.put("x-dead-letter-routing-key","YD");
        //设置 TTL 40s
        arguments.put("x-message-ttl",4000);

        return QueueBuilder.durable(QUEUE_B).withArguments(arguments).build();

    }

    // 创建死信交换机
    @Bean
    public DirectExchange deadExchange(){
        return new DirectExchange(EXCHANGE_DEAD_Y);
    }

    // 创建死信队列
    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(QUEUE_DEAD_D).build();
    }

    /**
     * 普通队列QB 通过 routingKey XA 绑定 普通交换机X
     * @param normalQueueA 普通队列QA
     * @param directExchange 普通交换机X
     * @return 绑定
     */
    @Bean
    public Binding queueABindingX(Queue normalQueueA,DirectExchange directExchange){
        return BindingBuilder.bind(normalQueueA).to(directExchange).with("XA");
    }
    /**
     * 普通队列QB 通过 routingKey XB 绑定 普通交换机X
     * @param normalQueueB 普通队列QB
     * @param directExchange 普通交换机X
     * @return 绑定
     */
    @Bean
    public Binding queueBBindingX(Queue normalQueueB,DirectExchange directExchange){
        return BindingBuilder.bind(normalQueueB).to(directExchange).with("XB");
    }
    /**
     * 死信队列QD 通过 routingKey YD 绑定 死信交换机Y
     * @param deadLetterQueue 死信队列QD
     * @param deadExchange 死信交换机Y
     * @return 绑定
     */
    @Bean
    public Binding queueDBindingX(Queue deadLetterQueue,DirectExchange deadExchange){
        return BindingBuilder.bind(deadLetterQueue).to(deadExchange).with("YD");
    }


}
