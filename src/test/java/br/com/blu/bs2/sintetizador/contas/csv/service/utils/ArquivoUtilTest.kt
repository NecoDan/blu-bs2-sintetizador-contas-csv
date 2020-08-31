package br.com.blu.bs2.sintetizador.contas.csv.service.utils

import br.com.blu.bs2.sintetizador.contas.csv.utils.ArquivoUtil.isNotContainsArquivosFileDiretorio
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.*

class ArquivoUtilTest {
    @Before
    fun setUp() {
    }

    // -- 01_Cenário
    @get:Test
    val isNotContainsArquivosFileDiretorio: Unit
        // -- 02_Ação && 03_Verificacao-Validacao
        get() {
            println("#TEST: isNotContainsArquivosFileDiretorio: ")

            // -- 01_Cenário
            val file = ParametrosTestesUtil.fileDiretorioPadraoSistema
            println("Diretorio File: " + file.absolutePath)

            // -- 02_Ação && 03_Verificacao-Validacao
            Assert.assertFalse(isNotContainsArquivosFileDiretorio(file))
            println("Qtde Arquivos Diretorio: " + Objects.requireNonNull(file.listFiles()).size.toString())
            println("-------------------------------------------------------------")
        }
}
