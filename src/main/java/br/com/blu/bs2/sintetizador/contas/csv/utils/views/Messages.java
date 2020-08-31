package br.com.blu.bs2.sintetizador.contas.csv.utils.views;

import javax.swing.*;

public final class Messages {

    private Messages() {
    }

    public static String perguntarComEntrada(String mensagem) {
        return JOptionPane.showInputDialog(mensagem);
    }

    public static void exibirMensagem(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem);
    }

    public static boolean perguntar(String pergunta) {
        return JOptionPane.showConfirmDialog(null, pergunta, "Jogo Gourmet V.1.0", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION;
    }
}
