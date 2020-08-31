package br.com.blu.bs2.sintetizador.contas.csv.service.arquivo;

import br.com.blu.bs2.sintetizador.contas.csv.model.arquivos.Arquivo;
import br.com.blu.bs2.sintetizador.contas.csv.model.arquivos.PropriedadeArquivo;
import br.com.blu.bs2.sintetizador.contas.csv.service.validation.IValidadorService;
import br.com.blu.bs2.sintetizador.contas.csv.service.validation.ValidadorService;
import br.com.blu.bs2.sintetizador.contas.csv.utils.ArquivoUtil;
import br.com.blu.bs2.sintetizador.contas.csv.utils.exceptions.ServiceException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static br.com.blu.bs2.sintetizador.contas.csv.utils.ArquivoUtil.buscarListaFiles;

public class ArquivoService implements IArquivoService {

    final IValidadorService validadorService;

    public ArquivoService() {
        this.validadorService = new ValidadorService();
    }

    @Override
    public List<Arquivo> recuperarArquivosCsvFromPath(String path) throws ServiceException {
        this.validadorService.validarPathEntradaArquivosCsv(path);
        return recuperarArquivosCsvFromFilePath(FileUtils.getFile(path));
    }

    @Override
    public List<Arquivo> recuperarArquivosCsvFromFilePath(File filePath) throws ServiceException {
        List<File> fileList = (filePath.isDirectory()) ? findAllFiles(filePath) : Collections.singletonList(filePath);
        return fileList.stream().filter(Objects::nonNull).map(this::mountArquivoCsvFromFile).collect(Collectors.toList());
    }

    @Override
    public List<File> findAllFilesPor(String filePath) throws ServiceException {
        return findAllFiles(FileUtils.getFile(filePath));
    }

    @Override
    public List<File> findAllFiles(File filePath) throws ServiceException {
        List<File> fileInputCsvList = procurar(filePath, PropriedadeArquivo.builder().build().getExtensaoPadraoArquivosLeitura());
        this.validadorService.validarContemArquivosObtidosFileDiretorioPadraoEntrada(fileInputCsvList);
        return fileInputCsvList;
    }

    public Arquivo mountArquivoCsvFromFile(File file) {
        return Arquivo
                .builder()
                .fileEntrada(file)
                .path(file.getPath())
                .nome(file.getName())
                .pathCompleto(file.getAbsolutePath())
                .build();
    }

    private List<File> procurar(File filePathInputDefault, String extensaoArquivoBusca) {
        return (isNotContainsArquivosDiretorioPadraoEntrada(filePathInputDefault))
                ? Collections.emptyList()
                : buscarListaFiles(filePathInputDefault, extensaoArquivoBusca);
    }

    private boolean isNotContainsArquivosDiretorioPadraoEntrada(File filePathInput) {
        return ArquivoUtil.isNotContainsArquivosFileDiretorio(filePathInput);
    }
}
