package br.com.blu.bs2.sintetizador.contas.csv;

import br.com.blu.bs2.sintetizador.contas.csv.service.processador.IProcessarDepositoService;
import br.com.blu.bs2.sintetizador.contas.csv.service.processador.ProcessarDepositoService;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        IProcessarDepositoService processarDepositoService = new ProcessarDepositoService();

        try {
            System.out.println("Inicializado com sucesso!");
            Scanner inPathDiretorio = new Scanner(System.in);
            System.out.println("\n Defina a pasta e/ou diretorio com o(s) arquivo(s) CSV a serem processados: ");

            String path = inPathDiretorio.nextLine();
            processarDepositoService.efetuarProcessamentos(path);
        } catch (Exception e) {
            System.out.println("Houve erro ao procesar arquivo e gerar depósitos: " + e.getLocalizedMessage());
        }

        System.exit(0);
    }
}
