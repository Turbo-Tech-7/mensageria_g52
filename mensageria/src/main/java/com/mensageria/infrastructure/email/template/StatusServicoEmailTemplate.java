package com.mensageria.infrastructure.email.template;

import com.mensageria.domain.model.NotificacaoStatusServico;
import com.mensageria.domain.model.StatusServico;

public final class StatusServicoEmailTemplate {

    private static final String BRAND       = "#1E50A0";
    private static final String DARK        = "#1A1A2E";
    private static final String MUTED       = "#6B7280";
    private static final String BG          = "#F0F4FA";
    private static final String WHITE       = "#FFFFFF";
    private static final String BORDER      = "#C8D2E6";
    private static final String FASE_FUTURA = "#D6DEEC";
    private static final String TEXTO_FUTURO = "#94A3B8";

    private StatusServicoEmailTemplate() {
    }

    public static String montar(NotificacaoStatusServico notificacao) {
        String body = """
            <span class="badge">🔧 Acompanhamento de Serviço</span>
            <h2>%s</h2>
            <p>Olá, <strong>%s</strong>!</p>
            <p>%s</p>
            %s
            <table class="info-table">
              <tr><td>Veículo</td><td>%s</td></tr>
              <tr><td>Status atual</td><td>%s</td></tr>
            </table>
            <hr/>
            <p class="note">
              Você receberá um novo e-mail a cada etapa concluída, até a retirada do veículo.
              Dúvidas? Entre em contato com a nossa equipe.
            </p>
            """.formatted(
                tituloPorStatus(notificacao.status()),
                notificacao.nomeCliente() != null ? notificacao.nomeCliente() : "Cliente",
                mensagemPorStatus(notificacao.status()),
                renderStepper(notificacao.status()),
                notificacao.veiculo() != null && !notificacao.veiculo().isBlank() ? notificacao.veiculo() : "—",
                notificacao.status().getDescricao()
        );

        return wrap("Garagem52 — Acompanhamento do seu serviço", body);
    }

    private static String tituloPorStatus(StatusServico status) {
        return switch (status) {
            case NAO_INICIADO -> "Seu serviço foi registrado";
            case EM_ANDAMENTO -> "Seu veículo está em atendimento";
            case SERVICO_CONCLUIDO -> "Serviço concluído — pronto para retirada!";
            case ENTREGUE -> "Veículo entregue";
        };
    }

    private static String mensagemPorStatus(StatusServico status) {
        return switch (status) {
            case NAO_INICIADO -> "Recebemos seu veículo e o serviço entrará na fila de atendimento em breve.";
            case EM_ANDAMENTO -> "Nosso mecânico já está trabalhando no seu veículo. Te avisamos assim que for concluído.";
            case SERVICO_CONCLUIDO -> "O serviço foi concluído e o seu carro já está pronto para retirada na oficina!";
            case ENTREGUE -> "Seu veículo foi retirado. Obrigado pela confiança na Garagem52!";
        };
    }

    private static String renderStepper(StatusServico atual) {
        StringBuilder barra = new StringBuilder();
        StringBuilder circulos = new StringBuilder();
        StringBuilder rotulos = new StringBuilder();

        for (StatusServico fase : StatusServico.values()) {
            boolean concluida = fase.ordinal() < atual.ordinal();
            boolean corrente = fase.ordinal() == atual.ordinal();
            boolean alcancada = concluida || corrente;

            String corBarra = alcancada ? BRAND : FASE_FUTURA;
            barra.append("<td style=\"width:25%;padding:0;\"><div style=\"height:6px;background:")
                    .append(corBarra).append(";border-radius:3px;margin:0 3px;\"></div></td>");

            String corCirculo = alcancada ? BRAND : FASE_FUTURA;
            String corConteudo = alcancada ? WHITE : TEXTO_FUTURO;
            String conteudo = concluida ? "&#10003;" : String.valueOf(fase.ordinal() + 1);
            circulos.append("""
                <td style="width:25%%; text-align:center; padding:0;">
                  <div style="width:32px;height:32px;border-radius:50%%;background:%s;color:%s;
                              font-size:13px;font-weight:700;line-height:32px;margin:0 auto;">%s</div>
                </td>
                """.formatted(corCirculo, corConteudo, conteudo));

            String corRotulo = corrente ? BRAND : (concluida ? DARK : TEXTO_FUTURO);
            String pesoRotulo = corrente ? "700" : "500";
            rotulos.append("""
                <td style="width:25%%; text-align:center; padding:8px 2px 0; font-size:10.5px;
                           color:%s; font-weight:%s;">%s</td>
                """.formatted(corRotulo, pesoRotulo, fase.getDescricao()));
        }

        return """
            <table style="width:100%%; border-collapse:collapse; margin:22px 0 4px;">
              <tr>%s</tr>
            </table>
            <table style="width:100%%; border-collapse:collapse; margin:0 0 20px;">
              <tr>%s</tr>
              <tr>%s</tr>
            </table>
            """.formatted(barra, circulos, rotulos);
    }

    private static String wrap(String title, String bodyHtml) {
        return """
            <!DOCTYPE html>
            <html lang="pt-BR">
            <head>
              <meta charset="UTF-8"/>
              <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
              <title>%s</title>
              <style>
                body  { margin:0; padding:0; background:%s;
                        font-family:'Segoe UI',Arial,sans-serif; }
                .wrap { max-width:580px; margin:36px auto; background:%s;
                        border-radius:14px; border:1px solid %s;
                        overflow:hidden; box-shadow:0 4px 24px rgba(30,80,160,.08); }
                .hdr  { background:%s; padding:28px 36px 22px; text-align:center; }
                .hdr-logo { margin:0; font-size:28px; font-weight:900;
                             color:#fff; letter-spacing:3px; }
                .hdr-sub  { margin:5px 0 0; font-size:10px;
                             color:rgba(255,255,255,.70);
                             letter-spacing:4px; text-transform:uppercase; }
                .body { padding:32px 36px; }
                .ftr  { background:%s; border-top:1px solid %s;
                        padding:18px 32px; text-align:center; }
                .ftr p { margin:0; font-size:11px; color:%s; line-height:1.6; }
                h2 { margin:0 0 10px; color:%s; font-size:20px; }
                p  { margin:0 0 14px; color:%s; font-size:14px; line-height:1.75; }
                hr { border:none; border-top:1px solid %s; margin:22px 0; }
                .badge { display:inline-block; background:%s; color:#fff;
                          font-size:10px; font-weight:700; letter-spacing:1.5px;
                          text-transform:uppercase; padding:4px 12px;
                          border-radius:20px; margin-bottom:14px; }
                .info-table { width:100%%; border-collapse:collapse; margin:6px 0 20px; }
                .info-table td { padding:9px 12px; font-size:13px;
                                  border-bottom:1px solid %s; }
                .info-table td:first-child { color:%s; font-weight:600;
                                              width:45%%; }
                .info-table td:last-child  { color:%s; text-align:right; }
                .info-table tr:last-child td { border-bottom:none; }
                .note { font-size:12px; color:%s; }
              </style>
            </head>
            <body>
              <div class="wrap">
                <div class="hdr">
                  <p class="hdr-logo">GARAGEM 52</p>
                  <p class="hdr-sub">Serviços Automotivos</p>
                </div>
                <div class="body">
                  %s
                </div>
                <div class="ftr">
                  <p>Este e-mail foi gerado automaticamente pelo sistema <strong>Garagem52</strong>.</p>
                  <p>Por favor, não responda este e-mail.</p>
                </div>
              </div>
            </body>
            </html>
            """.formatted(
                title,
                BG, WHITE, BORDER,
                BRAND,
                BG, BORDER, MUTED,
                DARK, MUTED,
                BORDER,
                BRAND,
                BORDER, DARK, MUTED,
                MUTED,
                bodyHtml
        );
    }
}
