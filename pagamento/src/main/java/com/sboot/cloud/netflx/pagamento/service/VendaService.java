package com.sboot.cloud.netflx.pagamento.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sboot.cloud.netflx.pagamento.data.vo.VendaVO;
import com.sboot.cloud.netflx.pagamento.entity.ProdutoVenda;
import com.sboot.cloud.netflx.pagamento.entity.Venda;
import com.sboot.cloud.netflx.pagamento.exception.ResourceNotFundException;
import com.sboot.cloud.netflx.pagamento.repository.ProdutoVendaRespository;
import com.sboot.cloud.netflx.pagamento.repository.VendaRepository;

@Service
public class VendaService {

	private final VendaRepository vendaRepository;
	private final ProdutoVendaRespository produtoVendaRespository;

	@Autowired
	public VendaService(VendaRepository vendaRepository, ProdutoVendaRespository produtoVendaRespository) {
		this.vendaRepository = vendaRepository;
		this.produtoVendaRespository = produtoVendaRespository;
	}
	
	public VendaVO create(VendaVO vendaVO) {
		Venda venda = vendaRepository.save(Venda.create(vendaVO));
		
		List<ProdutoVenda> produtosSalvos = new ArrayList<>();
		vendaVO.getProdutos().forEach(p -> {
			ProdutoVenda pv = ProdutoVenda.create(p);
			pv.setVenda(venda);
			produtosSalvos.add(produtoVendaRespository.save(pv));
		});
		
		venda.setProdutos(produtosSalvos);
		return  VendaVO.create(venda);
	}
	
	public Page<VendaVO> findAll(Pageable pageable){
		var page = vendaRepository.findAll(pageable);
		return page.map(this::convertToVendaVO);
	}
	
	private VendaVO convertToVendaVO(Venda venda) {
		return VendaVO.create(venda);
	}
	
	public VendaVO findById(Long id) {
		var entity = vendaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFundException("No records found for this ID"));
		return VendaVO.create(entity);
	}
	
}
