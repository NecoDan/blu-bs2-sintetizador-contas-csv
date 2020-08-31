package br.com.blu.bs2.sintetizador.contas.csv.model.dominio;

import java.util.*;
import java.util.stream.Stream;

public enum TipoConta {

    COMUM(0, Collections.singletonList(0), "COMUM", "CONTA TIPO COMUM"),

    PREMIUM(1, Arrays.asList(1, 2, 3), "PREMIUM", "CONTA TIPO PREMIUM"),

    EXECUTIVA(3, Arrays.asList(4, 5, 6, 7, 8), "EXECUTIVA", "CONTA TIPO EXECUTIVA"),

    SEM_ESPECIFICACAO(4, Collections.singletonList(9), "SEM_ESPECIFICACAO", "CONTA SEM ESPECIFICACAO");

    private static final Map<Integer, TipoConta> lookup;

    static {
        lookup = new HashMap<>();
        EnumSet<TipoConta> enumSet = EnumSet.allOf(TipoConta.class);

        for (TipoConta type : enumSet)
            lookup.put(type.codigo, type);
    }

    private int codigo;
    private List<Integer> digitosVerificador;
    private String codigoLiteral;
    private String descricao;

    TipoConta(int codigo, List<Integer> digitosVerificador, String codigoLiteral, String descricao) {
        inicialize(codigo, digitosVerificador, codigoLiteral, descricao);
    }

    private void inicialize(int codigo, List<Integer> digitosVerificador, String codigoLiteral, String descricao) {
        this.codigo = codigo;
        this.digitosVerificador = digitosVerificador;
        this.codigoLiteral = codigoLiteral;
        this.descricao = descricao;
    }

    public static TipoConta fromCodigo(int codigo) {
        if (lookup.containsKey(codigo))
            return lookup.get(codigo);
        throw new IllegalArgumentException(String.format("Código do Tipo Conta inválido: %d", codigo));
    }

    public static TipoConta of(int digitoVerificador) {
        return Stream.of(TipoConta.values())
                .filter(tipoConta -> tipoConta.getDigitosVerificador().contains(digitoVerificador))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public int getCodigo() {
        return this.codigo;
    }

    public List<Integer> getDigitosVerificador() {
        return digitosVerificador;
    }

    public String getCodigoLiteral() {
        return this.codigoLiteral.toLowerCase();
    }

    public String getDescricao() {
        return this.descricao;
    }

    public boolean isComum() {
        return Objects.equals(this, COMUM);
    }

    public boolean isPremium() {
        return Objects.equals(this, PREMIUM);
    }

    public boolean isExecutiva() {
        return Objects.equals(this, EXECUTIVA);
    }

    public boolean isContaComum(int digitoVerificador) {
        return (isDigitoVerificadorParamValido(digitoVerificador) && isComum() && isContemDigitoVerifadorInTipo(digitoVerificador));
    }

    public boolean isContaPremium(int digitoVerificador) {
        return (isDigitoVerificadorParamValido(digitoVerificador) && isPremium() && isContemDigitoVerifadorInTipo(digitoVerificador));
    }

    public boolean isContaExecutiva(int digitoVerificador) {
        return (isDigitoVerificadorParamValido(digitoVerificador) && isExecutiva() && isContemDigitoVerifadorInTipo(digitoVerificador));
    }

    private boolean isDigitoVerificadorParamValido(int digitoVerificador) {
        return (digitoVerificador >= 0);
    }

    private boolean isContemDigitoVerifadorInTipo(int digitoVerificador) {
        return this.getDigitosVerificador().stream().anyMatch(i -> i.equals(digitoVerificador));
    }
}
