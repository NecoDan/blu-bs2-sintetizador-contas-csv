package br.com.blu.bs2.sintetizador.contas.csv.utils

import br.com.blu.bs2.sintetizador.contas.csv.utils.RandomicoUtil.gerarValorRandomico
import br.com.blu.bs2.sintetizador.contas.csv.utils.RandomicoUtil.gerarValorRandomicoLong
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal

class RandomicoUtilTest {
    @Before
    fun setUp() {
    }

    @Test
    fun deveGerarValorRandomicoInteiro() {
        println("#TEST: deveGerarValorRandomicoInteiro: ")

        // -- 01_Cenário && -- 02_Ação
        val valor = gerarValorRandomico()

        // -- 03_Verificacao_Validacao
        Assert.assertTrue(valor > 0)
        println("Valor resultado: $valor")
        println("-------------------------------------------------------------")
    }

    @Test
    fun deveGerarValorRandomicoLong() {
        println("#TEST: deveGerarValorRandomicoLong: ")

        // -- 01_Cenário && -- 02_Ação
        val valor = gerarValorRandomicoLong()

        // -- 03_Verificacao_Validacao
        Assert.assertTrue(valor > 0)
        println("Valor resultado: $valor")
        println("-------------------------------------------------------------")
    }

    @Test
    fun deveGerarValorBigDecimalRandomicoAte() {
        println("#TEST: deveGerarValorBigDecimalRandomicoAte: ")

        // -- 01_Cenário
        val valorMaximo = RandomicoUtil.gerarValorRandomicoDecimal()

        // -- 02_Ação
        if (valorMaximo != null) {
            Assert.assertTrue(valorMaximo.toDouble() > 0)
            println("Valor resultado: $valorMaximo")
        } else {
            Assert.assertNull(valorMaximo)
        }

        println("-------------------------------------------------------------")
    }

}
