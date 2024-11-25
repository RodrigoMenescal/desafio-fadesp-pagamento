package com.fadesp.pagamento.service.impl;

import com.fadesp.pagamento.domain.entity.Pagamento;
import com.fadesp.pagamento.domain.enums.OperacaoEnum;
import com.fadesp.pagamento.repository.PagamentoRepository;
import com.fadesp.pagamento.service.PagamentoStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PagamentoBoletoImpl implements PagamentoStrategy {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    private final Logger logger =  LoggerFactory.getLogger(PagamentoStrategy.class);

    @Override
    public void pagar(Pagamento pagamento) {
        try {
            pagamentoRepository.save(pagamento);
            logger.error("Pagamento por Boleto salvo com sucesso :)");
        }catch (Exception e){
            logger.error("Erro ao processar pagamento por Boleto. Erro: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public OperacaoEnum getOperacao() {
        return OperacaoEnum.BOLETO;
    }
}