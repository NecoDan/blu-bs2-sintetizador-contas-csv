package br.com.blu.bs2.sintetizador.contas.csv.service.strategy

import br.com.blu.bs2.sintetizador.contas.csv.model.dominio.Conta
import br.com.blu.bs2.sintetizador.contas.csv.service.strategy.PercentualBonus
import java.math.BigDecimal

class BuilderBonusContaNaoEspecificada : IFactoryObterPercentualBonusService {
    override fun isAppliable(conta: Conta?): Boolean {
        return false
    }

    override fun obterValorPercentualBonusAoDeposito(conta: Conta?): BigDecimal? {
        return BigDecimal.valueOf(VALOR_FIXO_DEFAUL_PERCENTUAL_ZERADO)
    }

    override fun getPercentualBonus(conta: Conta?): PercentualBonus? {
        return PercentualBonus.BONUS_CONTA_NAO_ESPECIFICADA
    }

    companion object {
        private const val VALOR_FIXO_DEFAUL_PERCENTUAL_ZERADO = 0.0
    }
}
