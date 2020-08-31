package br.com.blu.bs2.sintetizador.contas.csv.model.dominio;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@Builder
public class Conta {
    private Long numero;
    private Integer digito;
    private TipoConta tipoConta;
    @Setter(value = AccessLevel.PRIVATE)
    private BigDecimal saldo;

    public void addSaldo(BigDecimal saldo) {
        if (Objects.isNull(this.saldo))
            this.saldo = BigDecimal.ZERO;
        this.saldo = this.saldo.add(saldo);
    }

    public Conta adicionarSaldo(BigDecimal saldo) {
        addSaldo(saldo);
        return this;
    }

    public void gerarTipoConta() {
        if (Objects.isNull(this.digito) || this.digito < 0)
            return;
        this.tipoConta = TipoConta.of(this.digito);
    }

    public Conta geraTipoConta() {
        gerarTipoConta();
        return this;
    }

    public boolean isTipoContaValido() {
        return (Objects.nonNull(this.tipoConta));
    }

    public boolean isTipoContaIsComum() {
        return (isTipoContaValido() && this.tipoConta.isContaComum(this.digito));
    }

    public boolean isTipoContaIsPremium() {
        return (isTipoContaValido() && this.tipoConta.isContaPremium(this.digito));
    }

    public boolean isTipoContaIsExecutiva() {
        return (isTipoContaValido() && this.tipoConta.isContaExecutiva(this.digito));
    }

    public String toStringNumeroContaCompleta() {
        return (isParamGenerateNumeroContaCompleta())
                ? String.valueOf(this.numero).concat("-").concat(String.valueOf(this.digito))
                : "";
    }

    private boolean isParamGenerateNumeroContaCompleta() {
        return (isNumeroValido() && isDigitoContaValido());
    }

    private boolean isNumeroValido() {
        return Objects.nonNull(this.numero);
    }

    private boolean isDigitoContaValido() {
        return Objects.nonNull(this.digito);
    }
}
