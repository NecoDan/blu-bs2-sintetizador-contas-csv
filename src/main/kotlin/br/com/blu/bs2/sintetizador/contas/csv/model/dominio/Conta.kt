package br.com.blu.bs2.sintetizador.contas.csv.model.dominio

import java.math.BigDecimal
import java.util.*

class Conta {
    var numero: Long? = null
    var digito: Int? = null
    var tipoConta: TipoConta? = null
    private var saldo: BigDecimal? = null
    fun getSaldo(): BigDecimal? {
        return saldo
    }

    fun setSaldo(saldo: BigDecimal?) {
        addSaldo(saldo)
    }

    fun addSaldo(saldo: BigDecimal?) {
        if (Objects.isNull(this.saldo)) this.saldo = BigDecimal.ZERO
        this.saldo = this.saldo!!.add(saldo)
    }

    fun adicionarSaldo(saldo: BigDecimal?): Conta {
        addSaldo(saldo)
        return this
    }

    fun gerarTipoConta() {
        if (Objects.isNull(digito) || digito!! < 0) return
        tipoConta = TipoConta.of(digito!!)
    }

    fun geraTipoConta(): Conta {
        gerarTipoConta()
        return this
    }

    val isTipoContaValido: Boolean
        get() = Objects.nonNull(tipoConta)
    val isTipoContaIsComum: Boolean
        get() = isTipoContaValido && tipoConta!!.isContaComum(digito!!)
    val isTipoContaIsPremium: Boolean
        get() = isTipoContaValido && tipoConta!!.isContaPremium(digito!!)
    val isTipoContaIsExecutiva: Boolean
        get() = isTipoContaValido && tipoConta!!.isContaExecutiva(digito!!)

    fun toStringNumeroContaCompleta(): String {
        return if (isParamGenerateNumeroContaCompleta) numero.toString() + "-" + digito.toString() else ""
    }

    private val isParamGenerateNumeroContaCompleta: Boolean
        get() = isNumeroValido && isDigitoContaValido
    private val isNumeroValido: Boolean
        get() = Objects.nonNull(numero)
    private val isDigitoContaValido: Boolean
        get() = Objects.nonNull(digito)

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is Conta) return false
        return numero == o.numero && digito == o.digito
    }

    override fun hashCode(): Int {
        return Objects.hash(numero, digito)
    }

    override fun toString(): String {
        return "Conta{" +
                "numero=" + numero +
                ", digito=" + digito +
                ", tipoConta=" + tipoConta +
                ", saldo=" + saldo +
                '}'
    }
}
