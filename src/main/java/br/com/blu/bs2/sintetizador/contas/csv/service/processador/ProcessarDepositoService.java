package br.com.blu.bs2.sintetizador.contas.csv.service.processador;

import br.com.blu.bs2.sintetizador.contas.csv.model.arquivos.Arquivo;
import br.com.blu.bs2.sintetizador.contas.csv.model.dominio.Conta;
import br.com.blu.bs2.sintetizador.contas.csv.service.arquivo.ArquivoService;
import br.com.blu.bs2.sintetizador.contas.csv.service.arquivo.IArquivoService;
import br.com.blu.bs2.sintetizador.contas.csv.service.negocio.FactoryContas;
import br.com.blu.bs2.sintetizador.contas.csv.service.negocio.GeraTransacoesDepositoService;
import br.com.blu.bs2.sintetizador.contas.csv.service.negocio.IFactoryContas;
import br.com.blu.bs2.sintetizador.contas.csv.service.negocio.IGeraTransacoesDepositoService;
import br.com.blu.bs2.sintetizador.contas.csv.service.validation.IValidadorService;
import br.com.blu.bs2.sintetizador.contas.csv.service.validation.ValidadorService;
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
    final IValidadorService validadorService;

    public ProcessarDepositoService() {
        this.arquivoService = new ArquivoService();
        this.geraTransacoesDepositoService = new GeraTransacoesDepositoService();
        this.factoryContas = new FactoryContas();
        this.validadorService = new ValidadorService();
    }

    @Override
    public void efetuarProcessamentos(String caminhoArquivos) throws Exception {
        this.validadorService.validarPathEntradaArquivosCsv(caminhoArquivos);
        efetuarProcessamentosPor(FileUtils.getFile(caminhoArquivos));
    }

    @Override
    public void efetuarProcessamentosPor(File fileCaminhoArquivos) throws Exception {
        List<Arquivo> arquivoList = arquivoService.recuperarArquivosCsvFromFilePath(fileCaminhoArquivos);

        if (isNaoContemArquivosAProcessar(arquivoList))
            return;

        arquivoList.parallelStream().filter(Objects::nonNull).forEach(arquivo -> {
            try {
                Arquivo arquivoResult = processarTransacoesPorArquivo(arquivo);
                imprimirResultados(arquivoResult);
            } catch (IOException | ServiceException e) {
                throw new RuntimeException(e.getLocalizedMessage());
            }
        });
    }

    private Arquivo processarTransacoesPorArquivo(Arquivo arquivo) throws IOException, ServiceException {
        List<Conta> contaList = this.factoryContas.getContasPorArquivoEFileCSVReader(arquivo);
        return this.geraTransacoesDepositoService.gerarDepositosFromTransacoes(arquivo, contaList);
    }

    private boolean isNaoContemArquivosAProcessar(List<Arquivo> arquivoList) {
        return (Objects.isNull(arquivoList) || arquivoList.isEmpty());
    }

    private void imprimirResultados(Arquivo arquivo) {
        if (Objects.isNull(arquivo))
            return;

        System.out.println("\n # Arquivo do {RELATÓRIO} de {SAIDA} gerado com sucesso: " + arquivo.getFileSaida().getAbsolutePath());
        System.out.println("# REPORT: \n");
        System.out.println(arquivo.getConteudo());
    }
}
