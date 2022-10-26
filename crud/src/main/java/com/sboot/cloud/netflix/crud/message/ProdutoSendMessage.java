package com.sboot.cloud.netflix.crud.message;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sboot.cloud.netflix.crud.data.vo.ProdutoVO;

@Component
public class ProdutoSendMessage {

	@Value("${crud.rabbitmq.exchange}")
	String exchange;
	
	@Value("${crud.rabbitmq.routingkey}")
	String routingkey;
	
	private final RabbitTemplate rabbitTemplate;

	@Autowired
	public ProdutoSendMessage(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}
	
	public void sendMessage(ProdutoVO produtoVO) {
		rabbitTemplate.convertAndSend(exchange, routingkey, produtoVO);
	}
	
	
}
