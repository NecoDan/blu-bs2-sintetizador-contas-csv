package br.com.blu.bs2.sintetizador.contas.csv.model.enums

enum class TipoExtensaoArquivo(codigo: Int, codigoLiteral: String, descricao: String) {
    CSV(1, "CSV", "TIPO EXTENSAO ARQUIVO CSV"), TXT(2, "TXT", "TIPO EXTENSAO ARQUIVO TXT"), DAT(3, "DAT", "TIPO EXTENSAO ARQUIVO DAT");

    private var codigo = 0
    private var codigoLiteral: String? = null
    private var descricao: String? = null

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
        @Throws(IllegalArgumentException::class)
        fun of(codigo: Int): TipoExtensaoArquivo {
            for (tipoExtensaoArquivo in values()) {
                if (tipoExtensaoArquivo.codigo == codigo)
                    return tipoExtensaoArquivo
            }
            throw IllegalArgumentException(String.format("Código do Tipo Extensão Arquivo inválido: %d", codigo))
        }
    }

    init {
        inicialize(codigo, codigoLiteral, descricao)
    }
}
