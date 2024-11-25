package com.fadesp.pagamento.domain.dto;

import com.fadesp.pagamento.domain.enums.StatusPagamentoEnum;

public record FiltroPagamentoDTO(
        Integer codigo,
        StatusPagamentoEnum status,
        String cpfCnpj
) {
}
