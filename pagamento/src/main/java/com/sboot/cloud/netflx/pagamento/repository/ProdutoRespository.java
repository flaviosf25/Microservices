package com.sboot.cloud.netflx.pagamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sboot.cloud.netflx.pagamento.entity.Produto;
import com.sboot.cloud.netflx.pagamento.entity.Venda;

@Repository
public interface ProdutoRespository extends JpaRepository<Produto, Long> {

}
