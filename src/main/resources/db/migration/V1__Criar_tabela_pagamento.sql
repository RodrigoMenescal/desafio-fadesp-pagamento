CREATE SEQUENCE IF NOT EXISTS sq_pagamento
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS tb_pagamento
(
    co_seq_pagamento    bigint     DEFAULT nextval('sq_pagamento') NOT NULL,
    co_debido           int         NOT NULL,
    cpf_cnpj            VARCHAR(20) NOT NULL,
    tp_operacao         VARCHAR(20) NOT NULL,
    nm_cartao           VARCHAR(20),
    st_ativo            bool        NOT NULL,
    status              VARCHAR(30)  NOT NULL,
    dh_criacao          timestamp without time zone,
    dh_alteracao        timestamp without time zone,
    CONSTRAINT pk_pagamento PRIMARY KEY (co_seq_pagamento)
);