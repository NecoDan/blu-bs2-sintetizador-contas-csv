package br.com.blu.bs2.sintetizador.contas.csv.service.processador;

import br.com.blu.bs2.sintetizador.contas.csv.model.arquivos.Arquivo;
import br.com.blu.bs2.sintetizador.contas.csv.model.dominio.Conta;
import br.com.blu.bs2.sintetizador.contas.csv.service.arquivo.ArquivoService;
import br.com.blu.bs2.sintetizador.contas.csv.service.arquivo.IArquivoService;
import br.com.blu.bs2.sintetizador.contas.csv.service.negocio.FactoryContas;
import br.com.blu.bs2.sintetizador.contas.csv.service.negocio.GeraTransacoesDepositoService;
import br.com.blu.bs2.sintetizador.contas.csv.service.negocio.IFactoryContas;
import br.com.blu.bs2.sintetizador.contas.csv.service.negocio.IGeraTransacoesDepositoService;
import br.com.blu.bs2.sintetizador.contas.csv.utils.exceptions.ServiceException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ProcessarDepositoService implements IProcessarDepositoService {

    final IArquivoService arquivoService;
    final IGeraTransacoesDepositoService geraTransacoesDepositoService;
    final IFactoryContas factoryContas;

    public ProcessarDepositoService() {
        this.arquivoService = new ArquivoService();
        this.geraTransacoesDepositoService = new GeraTransacoesDepositoService();
        this.factoryContas = new FactoryContas();
    }

    @Override
    public void efetuarProcessamentos(String caminhoArquivos) throws Exception {
        efetuarProcessamentosPor(FileUtils.getFile(caminhoArquivos));
    }

    @Override
    public void efetuarProcessamentosPor(File fileCaminhoArquivos) throws Exception {
        List<Arquivo> arquivoList = arquivoService.recuperarArquivosCsvFromFilePath(fileCaminhoArquivos);

        if (isNaoContemArquivosAProcessar(arquivoList))
            return;

        arquivoList.parallelStream().filter(Objects::nonNull).forEach(arquivo -> {
            try {
                processarTransacoesPorArquivo(arquivo);
            } catch (IOException | ServiceException e) {
                throw new RuntimeException(e.getLocalizedMessage());
            }
        });
    }

    private void processarTransacoesPorArquivo(Arquivo arquivo) throws IOException, ServiceException {
        List<Conta> contaList = this.factoryContas.getContasPorArquivoEFileCSVReader(arquivo);
        Arquivo arquivoResult = this.geraTransacoesDepositoService.gerarDepositosFromTransacoes(arquivo, contaList);
    }

    private boolean isNaoContemArquivosAProcessar(List<Arquivo> arquivoList) {
        return (Objects.isNull(arquivoList) || arquivoList.isEmpty());
    }
}
