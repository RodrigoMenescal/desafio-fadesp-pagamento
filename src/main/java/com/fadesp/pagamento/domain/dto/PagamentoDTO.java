package com.fadesp.pagamento.domain.dto;

import com.fadesp.pagamento.domain.enums.OperacaoEnum;
import com.fadesp.pagamento.domain.enums.StatusPagamentoEnum;

public record PagamentoDTO(
        Integer codigo,
        StatusPagamentoEnum status,
        String operacao,
        String cpfCnpj,
        String numeroCartao
) {
}
