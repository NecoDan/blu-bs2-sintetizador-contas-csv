package br.com.blu.bs2.sintetizador.contas.csv.service.validation;

import br.com.blu.bs2.sintetizador.contas.csv.utils.exceptions.ServiceException;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class ValidadorService implements IValidadorService {

    @Override
    public void validarPathEntradaArquivosCsv(String path) throws ServiceException {
        if (Objects.isNull(path) || path.isEmpty())
            throw new ServiceException("Parametro com o caminho do diretório de entrada dos arquivos, encontra-se inválido (conteúdo vazio) ou inexistente {NULL}.");
    }

    @Override
    public void validarContemArquivosObtidosFileDiretorioPadraoEntrada(List<File> fileInputDatList) throws ServiceException {
        if (fileInputDatList.isEmpty())
            throw new ServiceException("Não foram encontrados arquivos de leitura para entrada de dados. Pasta vazia.");
    }
}
