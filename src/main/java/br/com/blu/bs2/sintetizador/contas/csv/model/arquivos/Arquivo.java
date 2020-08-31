package br.com.blu.bs2.sintetizador.contas.csv.model.arquivos;

import lombok.Builder;
import lombok.Data;

import java.io.File;

@Data
@Builder
public class Arquivo {
    private String pathCompleto;
    private String path;
    private String nome;
    private File fileEntrada;
    private File fileSaida;
}
