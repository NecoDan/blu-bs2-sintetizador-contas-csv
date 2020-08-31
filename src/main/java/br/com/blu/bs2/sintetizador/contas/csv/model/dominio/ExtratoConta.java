package br.com.blu.bs2.sintetizador.contas.csv.model.dominio;

import br.com.blu.bs2.sintetizador.contas.csv.service.strategy.PercentualBonus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@Builder
public class ExtratoConta {
    private Conta conta;
    private BigDecimal valorTotalDeposito;
    private BigDecimal valorPercentualBonus;

    public void aplicarValorPercentualBonus(PercentualBonus percentualBonus) {
        if (Objects.isNull(this.conta)) {
            this.valorPercentualBonus = BigDecimal.ZERO;
            return;
        }
        this.valorPercentualBonus = (Objects.isNull(percentualBonus))
                ? BigDecimal.ZERO
                : percentualBonus.getFactoryObterPercentualBonusService().obterValorPercentualBonusAoDeposito(this.conta);
    }
}
