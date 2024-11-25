package com.fadesp.pagamento.controller;

import com.fadesp.pagamento.domain.dto.FiltroPagamentoDTO;
import com.fadesp.pagamento.domain.dto.PagamentoDTO;
import com.fadesp.pagamento.domain.enums.StatusPagamentoEnum;
import com.fadesp.pagamento.service.PagamentoService;
import com.fadesp.pagamento.shared.Mensagens;
import com.fadesp.pagamento.utils.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Pagamento", description = "API para gerenciamento de pagamento")
@RestController
@RequestMapping("v1/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping
    @Operation(summary = "Realizar Pagamento", description = "Esta operação realiza o pagamento")
    public ResponseEntity criarPagamento(@RequestBody PagamentoDTO pagamento) {
        try{
            pagamentoService.pagar(pagamento);
            return ResponseEntity.ok().body(ResponseUtil.montaResponse(Mensagens.PROCESSED_PENDING));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseUtil.montaResponse(e.getMessage()));
        }
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Atualiza o status", description = "Esta operação atualiza o status do pagamento")
    public ResponseEntity atualizarStatus(@PathVariable Long id, @RequestBody StatusPagamentoEnum status) {
        try{
            return ResponseEntity.ok().body(ResponseUtil.montaResponse(pagamentoService.atualizarStatus(id, status), Mensagens.UPDATE_REGITRY));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseUtil.montaResponse(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta o Pagamento", description = "Esta operação deleta o pagamento")
    public ResponseEntity excluirPagamento(@PathVariable Long id) {
        try{
            pagamentoService.excluirPagamento(id);
            return ResponseEntity.ok().body(ResponseUtil.montaResponse(Mensagens.DELETED_REGITRY));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseUtil.montaResponse(e.getMessage()));
        }
    }

    @GetMapping("/listar")
    @Operation(summary = "Liata todos os Pagamento", description = "Esta operação busca todos os pagamentos")
    public ResponseEntity listarPagamento() {
        try{
            return ResponseEntity.ok().body(ResponseUtil.montaResponse(pagamentoService.listarPagamento()));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseUtil.montaResponse(e.getMessage()));
        }
    }
    @GetMapping("/filtro")
    @Operation(summary = "Filtar Pagamento", description = "Esta operação realiza um filtro")
    public ResponseEntity filtrarPagamento(@ModelAttribute FiltroPagamentoDTO filtro) {
        try{
            return ResponseEntity.ok().body(ResponseUtil.montaResponse(pagamentoService.filtroPagamento(filtro)));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseUtil.montaResponse(e.getMessage()));
        }
    }
}
