package br.com.blu.bs2.sintetizador.contas.csv.service.strategy

import br.com.blu.bs2.sintetizador.contas.csv.model.dominio.Conta
import br.com.blu.bs2.sintetizador.contas.csv.service.strategy.PercentualBonus
import java.math.BigDecimal
import java.util.*

class BuilderBonusContaPremiumService : IFactoryObterPercentualBonusService {

    override fun isAppliable(conta: Conta?): Boolean {
        return Objects.nonNull(conta) && conta!!.isTipoContaIsPremium
    }

    override fun obterValorPercentualBonusAoDeposito(conta: Conta?): BigDecimal? {
        return if (isAppliable(conta)) BigDecimal.valueOf(VALOR_FIXO_DEFAUL_PERCENTUAL_DEZ_PORCENTO) else BigDecimal.ZERO
    }

    override fun getPercentualBonus(conta: Conta?): PercentualBonus? {
        return if (isAppliable(conta)) PercentualBonus.BONUS_CONTA_PREMIUM else PercentualBonus.BONUS_CONTA_NAO_ESPECIFICADA
    }

    companion object {
        private const val VALOR_FIXO_DEFAUL_PERCENTUAL_DEZ_PORCENTO = 10.00
    }
}
