package com.fadesp.pagamento.utils;

import com.fadesp.pagamento.domain.dto.ResponseDTO;

public class ResponseUtil {
    private ResponseUtil() {
    }

    public static<T> ResponseDTO<T> montaResponse(T dados, String mensagem) {
        return new ResponseDTO<>(dados, mensagem);
    }

    public static ResponseDTO<?> montaResponse(String mensagem) {
        return new ResponseDTO<>(mensagem);
    }

    public static<T> ResponseDTO<T> montaResponse(T dados) {
        return new ResponseDTO<>(dados);
    }
}
