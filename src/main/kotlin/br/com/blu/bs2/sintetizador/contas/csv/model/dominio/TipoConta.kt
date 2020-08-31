package br.com.blu.bs2.sintetizador.contas.csv.model.dominio

enum class TipoConta(codigo: Int, digitosVerificador: List<Int>, codigoLiteral: String, descricao: String) {

    COMUM(0, listOf<Int>(0), "COMUM", "CONTA TIPO COMUM"),

    PREMIUM(1, listOf<Int>(1, 2, 3), "PREMIUM", "CONTA TIPO PREMIUM"),

    EXECUTIVA(3, listOf<Int>(4, 5, 6, 7, 8), "EXECUTIVA", "CONTA TIPO EXECUTIVA"),

    SEM_ESPECIFICACAO(4, listOf<Int>(9), "SEM_ESPECIFICACAO", "CONTA SEM ESPECIFICACAO");

    var codigo = 0
        private set
    var digitosVerificador: List<Int>? = null
        private set
    private var codigoLiteral: String? = null
    var descricao: String? = null
        private set

    private fun inicialize(codigo: Int, digitosVerificador: List<Int>, codigoLiteral: String, descricao: String) {
        this.codigo = codigo
        this.digitosVerificador = digitosVerificador
        this.codigoLiteral = codigoLiteral
        this.descricao = descricao
    }

    fun getCodigoLiteral(): String {
        return codigoLiteral!!.toLowerCase()
    }

    val isComum: Boolean
        get() = this == COMUM
    val isPremium: Boolean
        get() = this == PREMIUM
    val isExecutiva: Boolean
        get() = this == EXECUTIVA

    fun isContaComum(digitoVerificador: Int): Boolean {
        return isDigitoVerificadorParamValido(digitoVerificador) && isComum && isContemDigitoVerifadorInTipo(digitoVerificador)
    }

    fun isContaPremium(digitoVerificador: Int): Boolean {
        return isDigitoVerificadorParamValido(digitoVerificador) && isPremium && isContemDigitoVerifadorInTipo(digitoVerificador)
    }

    fun isContaExecutiva(digitoVerificador: Int): Boolean {
        return isDigitoVerificadorParamValido(digitoVerificador) && isExecutiva && isContemDigitoVerifadorInTipo(digitoVerificador)
    }

    private fun isDigitoVerificadorParamValido(digitoVerificador: Int): Boolean {
        return digitoVerificador >= 0
    }

    private fun isContemDigitoVerifadorInTipo(digitoVerificador: Int): Boolean {
        return digitosVerificador!!.stream().anyMatch { i: Int -> i == digitoVerificador }
    }

    companion object {
        @Throws(IllegalArgumentException::class)
        fun of(digitoVerificador: Int): TipoConta {
            var tipoContaResult = SEM_ESPECIFICACAO
            for (tipoConta in values()) {
                if (tipoConta.isContemDigitoVerifadorInTipo(digitoVerificador))
                    tipoContaResult = tipoConta
            }
            return tipoContaResult
        }
    }

    init {
        inicialize(codigo, digitosVerificador, codigoLiteral, descricao)
    }
}
