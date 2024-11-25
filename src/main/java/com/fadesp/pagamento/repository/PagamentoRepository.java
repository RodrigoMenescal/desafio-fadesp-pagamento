package com.fadesp.pagamento.repository;

import com.fadesp.pagamento.domain.entity.Pagamento;
import com.fadesp.pagamento.domain.enums.StatusPagamentoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    @Query(value = "SELECT * FROM tb_pagamento p WHERE " +
            "(p.co_debido = :codigo OR :codigo IS NULL) " +
            "AND (p.cpf_cnpj = :cpfCnpj OR :cpfCnpj IS NULL) " +
            "AND (p.status = :status OR :status IS NULL)",
            nativeQuery = true)
    Optional<List<Pagamento>> getFiltroPagamento(@Param("codigo") Integer codigo,
                                                 @Param("cpfCnpj") String cpfCnpj,
                                                 @Param("status") String status);
}
