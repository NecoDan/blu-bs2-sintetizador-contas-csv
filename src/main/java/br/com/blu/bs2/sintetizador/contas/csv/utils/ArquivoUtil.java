package br.com.blu.bs2.sintetizador.contas.csv.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author Daniel Santos
 */
public final class ArquivoUtil {

    public static final String PATH_DEFAULT_SAIDA = "data/out";

    private ArquivoUtil() {
    }

    public static boolean isNotContainsArquivosFileDiretorio(File file) {
        return (isFileInValido(file) || Objects.isNull(file.listFiles()) || (Objects.nonNull(file.listFiles()) && Objects.requireNonNull(file.listFiles()).length == 0));
    }

    public static boolean isFileInValido(File file) {
        return (Objects.equals(false, isFileValido(file)));
    }

    public static boolean isFileValido(File file) {
        return (Objects.nonNull(file) && file.exists() && file.canRead());
    }

    public static List<File> buscarListaFiles(File filePath, String extensaoArquivoFiltro) {
        return (List<File>) ((isNotContainsExtensaoComoFiltroBusca(extensaoArquivoFiltro))
                ? FileUtils.listFiles(filePath, FileFilterUtils.fileFileFilter(), null)
                : FileUtils.listFiles(filePath, FileFilterUtils.suffixFileFilter(extensaoArquivoFiltro), null));
    }

    private static boolean isNotContainsExtensaoComoFiltroBusca(String extensaoArquivoFiltro) {
        return (Objects.isNull(extensaoArquivoFiltro) || extensaoArquivoFiltro.isEmpty());
    }

    public static void gravarArquivo(String conteudoArquivo, File arquivoNovo) throws IOException {
        try (FileWriter fileWriter = new FileWriter(arquivoNovo.getAbsolutePath())) {
            fileWriter.write(conteudoArquivo);
            fileWriter.flush();
        }
    }

    public static File gerarDiretorioPadraoArquivosSaidaAPartirSistema() {
        File filePathDiretorioSaidaSistema = new File(System.getProperty("user.home").concat("/").concat(PATH_DEFAULT_SAIDA));
        return (filePathDiretorioSaidaSistema.exists()) ? filePathDiretorioSaidaSistema : criarPathDiretorioInexistente(filePathDiretorioSaidaSistema);
    }

    public static File criarPathDiretorioInexistente(File filePathDiretorio) {
        if (!filePathDiretorio.exists()) {
            filePathDiretorio.mkdir();
        }
        return filePathDiretorio;
    }
}
