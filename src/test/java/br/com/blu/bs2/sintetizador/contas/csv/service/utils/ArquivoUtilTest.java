package br.com.blu.bs2.sintetizador.contas.csv.service.utils;

import br.com.blu.bs2.sintetizador.contas.csv.utils.ArquivoUtil;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Objects;

import static org.junit.Assert.assertFalse;

public class ArquivoUtilTest {

    @Before
    public void setUp() {

    }

    @Test
    public void isNotContainsArquivosFileDiretorio() {
        System.out.println("#TEST: isNotContainsArquivosFileDiretorio: ");

        // -- 01_Cenário
        File file = ParametrosTestesUtil.getFileDiretorioPadraoSistema();
        System.out.println("Diretorio File: ".concat(file.getAbsolutePath()));

        // -- 02_Ação && 03_Verificacao-Validacao
        assertFalse(ArquivoUtil.isNotContainsArquivosFileDiretorio(file));
        System.out.println("Qtde Arquivos Diretorio: ".concat(String.valueOf(Objects.requireNonNull(file.listFiles()).length)));
        System.out.println("-------------------------------------------------------------");
    }
}
