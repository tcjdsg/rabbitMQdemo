package com.tcj.rabbitmqdemo;

import com.tcj.rabbitmqdemo.pojos.User;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;

@SpringBootTest
class RabbitMQdemoApplicationTests {
	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Bean
	public MessageConverter messageConverter(){
		return new Jackson2JsonMessageConverter();
	}

	@Test
	public void testSendMessage() {
		for (int i = 0; i < 5; i++) {
			CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
			rabbitTemplate.convertAndSend("normal.exchange", "normal",
					"hello");
		}
	}

}
