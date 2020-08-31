package br.com.blu.bs2.sintetizador.contas.csv.service.processador;

import java.io.File;

public interface IProcessarDepositoService {

    void efetuarProcessamentos(String caminhoArquivos) throws Exception;

    void efetuarProcessamentosPor(File fileCaminhoArquivos) throws Exception;
}
