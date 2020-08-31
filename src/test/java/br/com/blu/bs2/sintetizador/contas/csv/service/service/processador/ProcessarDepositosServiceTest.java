package br.com.blu.bs2.sintetizador.contas.csv.service.service.processador;

import br.com.blu.bs2.sintetizador.contas.csv.model.enums.TipoExtensaoArquivo;
import br.com.blu.bs2.sintetizador.contas.csv.service.model.Transacao;
import br.com.blu.bs2.sintetizador.contas.csv.service.processador.ProcessarDepositoService;
import br.com.blu.bs2.sintetizador.contas.csv.service.utils.ParametrosTestesUtil;
import br.com.blu.bs2.sintetizador.contas.csv.utils.RandomicoUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ProcessarDepositosServiceTest {

    private ProcessarDepositoService processarDepositoService;
    private String pathTempPadraoSistema;

    @Before
    public void setUp() throws Exception {
        this.pathTempPadraoSistema = ParametrosTestesUtil.getDiretorioTempSistema();
        this.processarDepositoService = new ProcessarDepositoService();
    }

    @Test
    public void efetuarProcessamentosPorSringCaminhoPathValido() throws Exception {
        System.out.println("#TEST: efetuarProcessamentosPorSringCaminhoPathValido: ");

        // -- 01_Cenário
        List<Transacao> transacaoList = ParametrosTestesUtil.getListaArquivosTransacao();
        String nomeArquivo = RandomicoUtil.gerarValorRandomicoLong() + "." + TipoExtensaoArquivo.CSV.getCodigoLiteral();
        ParametrosTestesUtil.salvarGravarCSV(pathTempPadraoSistema, nomeArquivo, transacaoList);
        String path = pathTempPadraoSistema.concat("/").concat(nomeArquivo);

        // -- 02_Ação
        this.processarDepositoService.efetuarProcessamentos(path);

        // -- 03_Verificação_Validação
        //  System.out.println("Contains arquivos a serem lidos no diretorio: " + fileList.size());
        System.out.println("-------------------------------------------------------------");
    }

    @Test
    public void efetuarProcessamentosPorFileCaminhoPathValido() {

    }
}
