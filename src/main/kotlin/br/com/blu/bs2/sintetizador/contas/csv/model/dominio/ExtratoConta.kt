package br.com.blu.bs2.sintetizador.contas.csv.model.dominio

import br.com.blu.bs2.sintetizador.contas.csv.service.strategy.PercentualBonus
import java.math.BigDecimal
import java.util.*

class ExtratoConta {
    var conta: Conta? = null
    var valorTotalDeposito: BigDecimal? = null
    var valorPercentualBonus: BigDecimal? = null

    constructor() {}
    constructor(conta: Conta?, valorTotalDeposito: BigDecimal?, valorPercentualBonus: BigDecimal?) {
        this.conta = conta
        this.valorTotalDeposito = valorTotalDeposito
        this.valorPercentualBonus = valorPercentualBonus
    }

    fun aplicarValorPercentualBonus(percentualBonus: PercentualBonus) {
        if (Objects.isNull(conta)) {
            valorPercentualBonus = BigDecimal.ZERO
            return
        }
        valorPercentualBonus = if (Objects.isNull(percentualBonus)) BigDecimal.ZERO else percentualBonus.factoryObterPercentualBonusService.obterValorPercentualBonusAoDeposito(conta)
    }

    override fun toString(): String {
        return "ExtratoConta{" +
                "conta=" + conta +
                ", valorTotalDeposito=" + valorTotalDeposito +
                ", valorPercentualBonus=" + valorPercentualBonus +
                '}'
    }
}
