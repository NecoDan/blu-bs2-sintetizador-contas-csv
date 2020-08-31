package br.com.blu.bs2.sintetizador.contas.csv.service.arquivo;

import br.com.blu.bs2.sintetizador.contas.csv.model.arquivos.Arquivo;
import br.com.blu.bs2.sintetizador.contas.csv.utils.exceptions.ServiceException;

import java.io.File;
import java.util.List;

public interface IArquivoService {

    List<Arquivo> recuperarArquivosCsvFromPath(String path) throws ServiceException;

    List<Arquivo> recuperarArquivosCsvFromFilePath(File path) throws ServiceException;

    List<File> findAllFiles(File filePath) throws ServiceException;

    List<File> findAllFilesPor(String filePath) throws ServiceException;
}
