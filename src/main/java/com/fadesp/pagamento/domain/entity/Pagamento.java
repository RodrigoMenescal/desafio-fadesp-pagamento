package com.fadesp.pagamento.domain.entity;

import com.fadesp.pagamento.domain.enums.OperacaoEnum;
import com.fadesp.pagamento.domain.enums.StatusPagamentoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_pagamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pagamento {

    @Id
    @Column(name = "co_seq_pagamento", unique=true, nullable = false)
    @SequenceGenerator(name = "sq_pagamento", sequenceName = "sq_pagamento", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "sq_pagamento")
    private Long id;

    @Column(name = "co_debido")
    private Integer codigo;

    @Column(name = "cpf_cnpj")
    private String cpfCnpj;

    @Enumerated(EnumType.STRING)
    @Column(name = "tp_operacao", updatable = false)
    private OperacaoEnum operacao;

    @Column(name = "nm_cartao")
    private String numeroCartao;

    @Column(name = "st_ativo")
    private Boolean ativo = true;

    @Enumerated(EnumType.STRING)
    private StatusPagamentoEnum status;

    @Column(name = "dh_criacao", updatable = false, insertable = true, columnDefinition = "TIMESTAMP")
    private LocalDateTime createdDate;

    @Column(name = "dh_alteracao", insertable = true, columnDefinition = "TIMESTAMP")
    private LocalDateTime modifiedDate;

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }
}
