package br.com.blu.bs2.sintetizador.contas.csv.service.strategy

import br.com.blu.bs2.sintetizador.contas.csv.model.dominio.Conta
import java.math.BigDecimal

interface IFactoryObterPercentualBonusService {

    fun isAppliable(conta: Conta?): Boolean

    fun obterValorPercentualBonusAoDeposito(conta: Conta?): BigDecimal?

    fun getPercentualBonus(conta: Conta?): PercentualBonus?
}
