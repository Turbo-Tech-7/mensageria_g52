package com.mensageria.domain.model;

/**
 * Espelha com.garagem52.domain.utils.enums.ServicoStatus pelo nome (contrato
 * implícito entre os dois serviços via JSON). A ordem dos valores define a
 * linha do tempo exibida no e-mail de acompanhamento.
 */
public enum StatusServico {

    NAO_INICIADO("Não iniciado"),
    EM_ANDAMENTO("Em andamento"),
    SERVICO_CONCLUIDO("Serviço concluído"),
    ENTREGUE("Entregue");

    private final String descricao;

    StatusServico(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
