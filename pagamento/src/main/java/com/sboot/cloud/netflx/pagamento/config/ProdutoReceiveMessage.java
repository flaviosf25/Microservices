package com.sboot.cloud.netflx.pagamento.config;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.sboot.cloud.netflx.pagamento.data.vo.ProdutoVO;
import com.sboot.cloud.netflx.pagamento.entity.Produto;
import com.sboot.cloud.netflx.pagamento.repository.ProdutoRespository;

@Component
public class ProdutoReceiveMessage {
	
	private final ProdutoRespository produtoRespository;

	@Autowired
	public ProdutoReceiveMessage(ProdutoRespository produtoRespository) {
		this.produtoRespository = produtoRespository;
	}
	
	@RabbitListener(queues = {"${crud.rabbitmq.queue}"})
	public void receive(@Payload ProdutoVO produtoVO) {
		Produto produto = Produto.create(produtoVO);
		produtoRespository.save(produto);
	}
}
