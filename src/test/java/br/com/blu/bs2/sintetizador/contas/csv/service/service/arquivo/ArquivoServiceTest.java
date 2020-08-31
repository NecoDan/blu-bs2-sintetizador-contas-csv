package br.com.blu.bs2.sintetizador.contas.csv.service.service.arquivo;

import br.com.blu.bs2.sintetizador.contas.csv.model.arquivos.Arquivo;
import br.com.blu.bs2.sintetizador.contas.csv.model.enums.TipoExtensaoArquivo;
import br.com.blu.bs2.sintetizador.contas.csv.service.arquivo.ArquivoService;
import br.com.blu.bs2.sintetizador.contas.csv.service.model.Transacao;
import br.com.blu.bs2.sintetizador.contas.csv.service.utils.ParametrosTestesUtil;
import br.com.blu.bs2.sintetizador.contas.csv.utils.RandomicoUtil;
import br.com.blu.bs2.sintetizador.contas.csv.utils.exceptions.ServiceException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class ArquivoServiceTest {

    private ArquivoService arquivoService;
    private String diretorioTemporarioSistema;

    @Before
    public void setUp() {
        this.arquivoService = new ArquivoService();
        this.diretorioTemporarioSistema = ParametrosTestesUtil.getDiretorioTempSistema();
    }

    @Test
    public void deveRetornarListaFilesArquivosCsvAPartiCaminhoParametro() throws IOException, ServiceException {
        System.out.println("#TEST: deveRetornarListaFilesArquivosCsvAPartiCaminhoParametro: ");

        // -- 01_Cenário
        List<Transacao> transacaoList = ParametrosTestesUtil.getListaArquivosTransacao();
        String nomeArquivo = RandomicoUtil.gerarValorRandomicoLong() + "." + TipoExtensaoArquivo.CSV.getCodigoLiteral();
        ParametrosTestesUtil.salvarGravarCSV(null, nomeArquivo, transacaoList);

        // -- 02_Ação
        List<File> fileList = this.arquivoService.findAllFilesPor(this.diretorioTemporarioSistema);

        // -- 03_Verificação_Validação
        assertTrue(fileList.size() > 0);
        System.out.println("Contains arquivos a serem lidos no diretorio: " + fileList.size());
        System.out.println("-------------------------------------------------------------");
    }

    @Test
    public void deveRetornarUmaListaObjsArquivosCSVAPartiCaminhoParametro() throws IOException, ServiceException {
        System.out.println("#TEST: deveRetornarUmaListaObjsArquivosCSVAPartiCaminhoParametro: ");

        // -- 01_Cenário
        List<Transacao> transacaoList = ParametrosTestesUtil.getListaArquivosTransacao();
        String nomeArquivo = RandomicoUtil.gerarValorRandomicoLong() + "." + TipoExtensaoArquivo.CSV.getCodigoLiteral();
        ParametrosTestesUtil.salvarGravarCSV(null, nomeArquivo, transacaoList);

        // -- 02_Ação
        List<Arquivo> arquivoList = this.arquivoService.recuperarArquivosCsvFromPath(this.diretorioTemporarioSistema);

        // -- 03_Verificação_Validação
        assertTrue(arquivoList.size() > 0);
        System.out.println("Contains arquivos a serem lidos no diretorio: " + arquivoList.size());
        System.out.println("-------------------------------------------------------------");
    }
}
