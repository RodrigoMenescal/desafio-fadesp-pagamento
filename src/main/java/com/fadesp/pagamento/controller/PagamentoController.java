package com.fadesp.pagamento.controller;

import com.fadesp.pagamento.domain.dto.FiltroPagamentoDTO;
import com.fadesp.pagamento.domain.dto.PagamentoDTO;
import com.fadesp.pagamento.domain.enums.StatusPagamentoEnum;
import com.fadesp.pagamento.service.PagamentoService;
import com.fadesp.pagamento.shared.Mensagens;
import com.fadesp.pagamento.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping
    public ResponseEntity criarPagamento(@RequestBody PagamentoDTO pagamento) {
        try{
            pagamentoService.pagar(pagamento);
            return ResponseEntity.ok().body(ResponseUtil.montaResponse(Mensagens.PROCESSED_PENDING));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseUtil.montaResponse(e.getMessage()));
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity atualizarStatus(@PathVariable Long id, @RequestBody StatusPagamentoEnum status) {
        try{
            return ResponseEntity.ok().body(ResponseUtil.montaResponse(pagamentoService.atualizarStatus(id, status), Mensagens.UPDATE_REGITRY));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseUtil.montaResponse(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluirPagamento(@PathVariable Long id) {
        try{
            pagamentoService.excluirPagamento(id);
            return ResponseEntity.ok().body(ResponseUtil.montaResponse(Mensagens.DELETED_REGITRY));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseUtil.montaResponse(e.getMessage()));
        }
    }

    @GetMapping("/listar")
    public ResponseEntity listarPagamento() {
        try{
            return ResponseEntity.ok().body(ResponseUtil.montaResponse(pagamentoService.listarPagamento()));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseUtil.montaResponse(e.getMessage()));
        }
    }
    @GetMapping("/filtro")
    public ResponseEntity filtrarPagamento(@ModelAttribute FiltroPagamentoDTO filtro) {
        try{
            return ResponseEntity.ok().body(ResponseUtil.montaResponse(pagamentoService.filtroPagamento(filtro)));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseUtil.montaResponse(e.getMessage()));
        }
    }
}
