package com.fadesp.pagamento.domain.dto;

public record ResponseDTO<T>(T dados, String mensagem) {
    public ResponseDTO(T dados) {
        this(dados, null);
    }

    public ResponseDTO(String mensagem) {
        this(null, mensagem);
    }
}
