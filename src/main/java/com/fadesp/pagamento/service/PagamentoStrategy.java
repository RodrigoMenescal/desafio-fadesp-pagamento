package com.fadesp.pagamento.service;

import com.fadesp.pagamento.domain.dto.PagamentoDTO;
import com.fadesp.pagamento.domain.entity.Pagamento;
import com.fadesp.pagamento.domain.enums.OperacaoEnum;
import org.springframework.stereotype.Component;

@Component
public interface PagamentoStrategy {

    void pagar(Pagamento pagamento);

    OperacaoEnum getOperacao();
}
