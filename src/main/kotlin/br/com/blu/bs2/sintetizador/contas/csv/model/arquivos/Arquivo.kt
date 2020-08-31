package br.com.blu.bs2.sintetizador.contas.csv.model.arquivos

import java.io.File

class Arquivo {
    var pathCompleto: String? = null
    var path: String? = null
    var nome: String? = null
    var fileEntrada: File? = null
    var fileSaida: File? = null
    var conteudo: String? = null

    constructor() {}

    constructor(pathCompleto: String?, path: String?, nome: String?, fileEntrada: File?, fileSaida: File?, conteudo: String?) {
        this.pathCompleto = pathCompleto
        this.path = path
        this.nome = nome
        this.fileEntrada = fileEntrada
        this.fileSaida = fileSaida
        this.conteudo = conteudo
    }

    override fun toString(): String {
        return "Arquivo{" +
                "pathCompleto='" + pathCompleto + '\'' +
                ", path='" + path + '\'' +
                ", nome='" + nome + '\'' +
                ", fileEntrada=" + fileEntrada +
                ", fileSaida=" + fileSaida +
                ", conteudo='" + conteudo + '\'' +
                '}'
    }
}
