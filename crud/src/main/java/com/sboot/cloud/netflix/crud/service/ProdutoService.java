package com.sboot.cloud.netflix.crud.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sboot.cloud.netflix.crud.data.vo.ProdutoVO;
import com.sboot.cloud.netflix.crud.entity.Produto;
import com.sboot.cloud.netflix.crud.exception.ResourceNotFundException;
import com.sboot.cloud.netflix.crud.message.ProdutoSendMessage;
import com.sboot.cloud.netflix.crud.repository.ProdutoRepository;

@Service
public class ProdutoService {
	
	private final ProdutoRepository produtoRepository;
	private final ProdutoSendMessage produtoSendMessage;

	@Autowired
	public ProdutoService(ProdutoRepository produtoRepository, ProdutoSendMessage produtoSendMessage) {
		this.produtoRepository = produtoRepository;
		this.produtoSendMessage = produtoSendMessage;
	}
	
	public ProdutoVO create(ProdutoVO produtoVO) {
		Produto prodRetorno = produtoRepository.save(Produto.create(produtoVO));
		ProdutoVO prodVORetorno = ProdutoVO.create(prodRetorno);
		produtoSendMessage.sendMessage(prodVORetorno);
		return prodVORetorno;
	}
	
	public Page<ProdutoVO> findAll(Pageable pageable){
		var page = produtoRepository.findAll(pageable);
		return page.map(this::convertToProdutoVO);
	}
	
	private ProdutoVO convertToProdutoVO(Produto produto) {
		return ProdutoVO.create(produto);
	}
	
	public ProdutoVO findById(Long id) {
		var entity = produtoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFundException("No records found for this ID"));
		return ProdutoVO.create(entity);
	}
	
	public ProdutoVO update(ProdutoVO produtoVO) {
		final Optional<Produto> optnionalProduto = produtoRepository.findById(produtoVO.getId());
		if(!optnionalProduto.isPresent()) {
			new ResourceNotFundException("No records found for this ID");
		}
		
		return ProdutoVO.create(produtoRepository.save(Produto.create(produtoVO)));
				
	}
	
	public void delete(Long id) {
		var entity = produtoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFundException("No records found for this ID"));
		
		produtoRepository.delete(entity);
	}
	
}
