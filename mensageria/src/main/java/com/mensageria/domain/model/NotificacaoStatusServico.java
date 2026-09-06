package com.mensageria.domain.model;

/**
 * Representa, em termos de domínio do mensageria, o conteúdo já traduzido do
 * evento "SERVICO_STATUS_ATUALIZADO" recebido do TurboTech-BackEnd.
 */
public record NotificacaoStatusServico(
        String servicoId,
        String orcamentoId,
        String nomeCliente,
        String emailCliente,
        String veiculo,
        StatusServico status
) {
}
