package br.com.blu.bs2.sintetizador.contas.csv.model.arquivos;

import br.com.blu.bs2.sintetizador.contas.csv.model.enums.TipoExtensaoArquivo;
import lombok.Builder;

@Builder
public class PropriedadeArquivo {

    private static final String EXTENSAO_PADRAO_ARQUIVOS_LEITURA = TipoExtensaoArquivo.CSV.getCodigoLiteral();

    public String getExtensaoPadraoArquivosLeitura() {
        return EXTENSAO_PADRAO_ARQUIVOS_LEITURA;
    }
}
