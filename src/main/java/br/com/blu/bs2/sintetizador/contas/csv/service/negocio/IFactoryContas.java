package br.com.blu.bs2.sintetizador.contas.csv.service.negocio;

import br.com.blu.bs2.sintetizador.contas.csv.model.arquivos.Arquivo;
import br.com.blu.bs2.sintetizador.contas.csv.model.dominio.Conta;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IFactoryContas {

    List<Conta> getContasPorArquivoEFileCSVReader(Arquivo arquivo) throws IOException;

    Conta getContaExistenteFromList(List<Conta> contaList, Conta conta, BigDecimal valor);

    Optional<Conta> getOptionalContaExistente(List<Conta> contaList, Conta conta);
}
