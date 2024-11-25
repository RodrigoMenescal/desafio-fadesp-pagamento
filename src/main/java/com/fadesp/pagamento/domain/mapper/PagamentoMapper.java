package com.fadesp.pagamento.domain.mapper;

import com.fadesp.pagamento.domain.dto.PagamentoDTO;
import com.fadesp.pagamento.domain.entity.Pagamento;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PagamentoMapper {

    static PagamentoMapper build() {
        return Mappers.getMapper(PagamentoMapper.class);
    }

    Pagamento dtoToEntity(PagamentoDTO dto);
    PagamentoDTO entityToDTO(Pagamento entity);
}
