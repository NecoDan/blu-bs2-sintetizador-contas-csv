package br.com.blu.bs2.sintetizador.contas.csv.model.enums

import java.util.stream.Stream

enum class TipoExtensaoArquivo(codigo: Int, codigoLiteral: String, descricao: String) {
    CSV(1, "CSV", "TIPO EXTENSAO ARQUIVO CSV"), TXT(2, "TXT", "TIPO EXTENSAO ARQUIVO TXT"), DAT(3, "DAT", "TIPO EXTENSAO ARQUIVO DAT");

    var codigo = 0
        private set
    private var codigoLiteral: String? = null
    var descricao: String? = null
        private set

    private fun inicialize(codigo: Int, codigoLiteral: String, descricao: String) {
        this.codigo = codigo
        this.codigoLiteral = codigoLiteral
        this.descricao = descricao
    }

    fun getCodigoLiteral(): String {
        return codigoLiteral!!.toLowerCase()
    }

    val isXML: Boolean
        get() = this == CSV
    val isTXT: Boolean
        get() = this == TXT
    val isDAT: Boolean
        get() = this == DAT

    companion object {
//        fun of(codigo: Int): TipoExtensaoArquivo {
//            return Stream.of(*values())
//                    .filter { tipoExtensaoArquivo: TipoExtensaoArquivo -> tipoExtensaoArquivo.codigo == codigo }
//                    .findFirst()
//                    .orElseThrow { IllegalArgumentException() }
//        }
    }

    init {
        inicialize(codigo, codigoLiteral, descricao)
    }
}
