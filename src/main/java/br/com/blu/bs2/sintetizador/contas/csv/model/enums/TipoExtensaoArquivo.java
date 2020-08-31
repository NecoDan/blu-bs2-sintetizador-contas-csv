package br.com.blu.bs2.sintetizador.contas.csv.model.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public enum TipoExtensaoArquivo {

    CSV(1, "CSV", "TIPO EXTENSAO ARQUIVO CSV"),

    TXT(2, "TXT", "TIPO EXTENSAO ARQUIVO TXT"),

    DAT(3, "DAT", "TIPO EXTENSAO ARQUIVO DAT");

    private static final Map<Integer, TipoExtensaoArquivo> lookup;

    static {
        lookup = new HashMap<>();
        EnumSet<TipoExtensaoArquivo> enumSet = EnumSet.allOf(TipoExtensaoArquivo.class);

        for (TipoExtensaoArquivo type : enumSet)
            lookup.put(type.codigo, type);
    }

    private int codigo;
    private String codigoLiteral;
    private String descricao;

    TipoExtensaoArquivo(int codigo, String codigoLiteral, String descricao) {
        inicialize(codigo, codigoLiteral, descricao);
    }

    private void inicialize(int codigo, String codigoLiteral, String descricao) {
        this.codigo = codigo;
        this.codigoLiteral = codigoLiteral;
        this.descricao = descricao;
    }

    public static TipoExtensaoArquivo fromCodigo(int codigo) {
        if (lookup.containsKey(codigo))
            return lookup.get(codigo);
        throw new IllegalArgumentException(String.format("Código do Layout Arquivo inválido: %d", codigo));
    }

    public int getCodigo() {
        return this.codigo;
    }

    public String getCodigoLiteral() {
        return this.codigoLiteral.toLowerCase();
    }

    public String getDescricao() {
        return this.descricao;
    }

    public boolean isXML() {
        return Objects.equals(this, CSV);
    }

    public boolean isTXT() {
        return Objects.equals(this, TXT);
    }

    public boolean isDAT() {
        return Objects.equals(this, DAT);
    }
}
