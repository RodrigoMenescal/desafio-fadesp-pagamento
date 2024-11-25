package com.fadesp.pagamento.service;

import com.fadesp.pagamento.domain.dto.FiltroPagamentoDTO;
import com.fadesp.pagamento.domain.dto.PagamentoDTO;
import com.fadesp.pagamento.domain.entity.Pagamento;
import com.fadesp.pagamento.domain.enums.OperacaoEnum;
import com.fadesp.pagamento.domain.enums.StatusPagamentoEnum;
import com.fadesp.pagamento.domain.mapper.PagamentoMapper;
import com.fadesp.pagamento.exception.DocumentoInvalidoException;
import com.fadesp.pagamento.exception.OperacaoInvalidaException;
import com.fadesp.pagamento.repository.PagamentoRepository;
import com.fadesp.pagamento.service.impl.PagamentoBoletoImpl;
import com.fadesp.pagamento.service.impl.PagamentoCartaoCreditoImpl;
import com.fadesp.pagamento.service.impl.PagamentoCartaoDebitoImpl;
import com.fadesp.pagamento.service.impl.PagamentoPixImp;
import com.fadesp.pagamento.shared.Mensagens;
import com.fadesp.pagamento.utils.ValidadorDocumento;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    private final Logger logger = LoggerFactory.getLogger(PagamentoService.class);

    private final Map<OperacaoEnum, PagamentoStrategy> mapPagamentoStrategy;

    public PagamentoService(List<PagamentoStrategy> pagamentoStrategies) {
        this.mapPagamentoStrategy = pagamentoStrategies.stream()
                .collect(Collectors.toMap(PagamentoStrategy::getOperacao, Function.identity()));
    }

    public void pagar(PagamentoDTO pagamentoDTO){
        try {
            OperacaoEnum meioPagamento = getMeioPagamento(String.valueOf(pagamentoDTO.operacao()));

            logger.error("Iniciando pagamento por " + meioPagamento);

            if (!ValidadorDocumento.validar(pagamentoDTO.cpfCnpj())) {
                throw new DocumentoInvalidoException("CPF ou CNPJ inválido: " + pagamentoDTO.cpfCnpj());
            }


            PagamentoStrategy pagamentoStrategy = mapPagamentoStrategy.get(meioPagamento);
            if (Objects.isNull(pagamentoStrategy)) {
                throw new OperacaoInvalidaException(Mensagens.PAYMENT_INVALIDATED);
            }

            Pagamento pagamento = PagamentoMapper.build().dtoToEntity(pagamentoDTO);
            pagamento.setStatus(StatusPagamentoEnum.PENDENTE);

            pagamentoStrategy.pagar(pagamento);

        } catch (DocumentoInvalidoException | OperacaoInvalidaException e) {
            logger.error("Erro ao processar pagamento: {}", e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public PagamentoDTO atualizarStatus(Long id, StatusPagamentoEnum status){
        try {
            Pagamento pagamento = pagamentoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException(Mensagens.PAYMENT_NOT_FOUND));

            validarStatus(pagamento.getStatus(), status);

            pagamento.setStatus(status);
            pagamento.setModifiedDate(LocalDateTime.now());
            pagamentoRepository.save(pagamento);

            return PagamentoMapper.build().entityToDTO(pagamento);
        }catch (OperacaoInvalidaException e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void excluirPagamento(Long id){
        try {
            Pagamento pagamento = pagamentoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException(Mensagens.PAYMENT_NOT_FOUND));

            if (!pagamento.getStatus().equals(StatusPagamentoEnum.PENDENTE)) {
                throw new RuntimeException("Somente pagamentos pendentes podem ser excluídos!");
            }

            pagamento.setAtivo(false);
            pagamentoRepository.save(pagamento);

        }catch (OperacaoInvalidaException e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public List<Pagamento> filtroPagamento(FiltroPagamentoDTO filtro){

        return pagamentoRepository.getFiltroPagamento(filtro.codigo(), filtro.cpfCnpj(), String.valueOf(filtro.status()))
                .orElseThrow(() -> new RuntimeException(Mensagens.PAYMENT_NOT_FOUND));
    }

    public List<Pagamento> listarPagamento(){
        return pagamentoRepository.findAll();
    }

    private void validarStatus(StatusPagamentoEnum statusAtual, StatusPagamentoEnum novoStatus) {
        if (statusAtual == StatusPagamentoEnum.PROCESSADO_SUCESSO) {
            throw new RuntimeException("Este pagamento não pode ter seu status alterado!");
        }

        if (statusAtual == StatusPagamentoEnum.PROCESSADO_FALHA && novoStatus != StatusPagamentoEnum.PENDENTE) {
            throw new RuntimeException("Este pagamento não pode ter seu status alterado para " + novoStatus);
        }
    }

    private OperacaoEnum getMeioPagamento(String operacaoString) {
        try {
            return OperacaoEnum.valueOf(operacaoString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new OperacaoInvalidaException(Mensagens.PAYMENT_INVALIDATED);
        }
    }
}
