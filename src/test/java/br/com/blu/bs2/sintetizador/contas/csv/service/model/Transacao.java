package br.com.blu.bs2.sintetizador.contas.csv.service.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Transacao {
    private String numeroConta;
    private BigDecimal valorTransacao;
}
