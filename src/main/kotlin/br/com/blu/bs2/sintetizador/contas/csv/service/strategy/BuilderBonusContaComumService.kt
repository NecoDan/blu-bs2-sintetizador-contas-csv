package br.com.blu.bs2.sintetizador.contas.csv.service.strategy

import br.com.blu.bs2.sintetizador.contas.csv.model.dominio.Conta
import br.com.blu.bs2.sintetizador.contas.csv.service.strategy.PercentualBonus
import java.math.BigDecimal
import java.util.*

class BuilderBonusContaComumService : IFactoryObterPercentualBonusService {

    override fun isAppliable(conta: Conta?): Boolean {
        return Objects.nonNull(conta) && conta!!.isTipoContaIsComum
    }

    override fun obterValorPercentualBonusAoDeposito(conta: Conta?): BigDecimal? {
        return if (isAppliable(conta)) BigDecimal.valueOf(VALOR_FIXO_DEFAUL_PERCENTUAL_UM_PORCENTO) else BigDecimal.ZERO
    }

    override fun getPercentualBonus(conta: Conta?): PercentualBonus? {
        return if (isAppliable(conta)) PercentualBonus.BONUS_CONTA_COMUM else PercentualBonus.BONUS_CONTA_NAO_ESPECIFICADA
    }

    companion object {
        private const val VALOR_FIXO_DEFAUL_PERCENTUAL_UM_PORCENTO = 1.00
    }
}
