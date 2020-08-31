package br.com.blu.bs2.sintetizador.contas.csv.utils.exceptions

class ServiceException : Exception {
    constructor(s: String) : super(msgValidacaoDefault + s) {}
    constructor(s: String?, throwable: Throwable?) : super(s, throwable) {}
    constructor(throwable: Throwable?) : super(throwable) {}
    constructor(s: String?, throwable: Throwable?, b: Boolean, b1: Boolean) : super(s, throwable, b, b1) {}

    companion object {
        const val msgValidacaoDefault = "Validação serviço - "
    }
}
