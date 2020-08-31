package br.com.blu.bs2.sintetizador.contas.csv.service.validation;

import br.com.blu.bs2.sintetizador.contas.csv.utils.exceptions.ServiceException;

import java.io.File;
import java.util.List;

public interface IValidadorService {

    void validarPathEntradaArquivosCsv(String path) throws ServiceException;

    void validarContemArquivosObtidosFileDiretorioPadraoEntrada(List<File> fileInputDatList) throws ServiceException;
}
