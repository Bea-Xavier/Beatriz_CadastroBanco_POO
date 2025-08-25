package funcoes;

import javax.swing.*;
import objetos.usuarios.Cliente;
import objetos.contas.ContaCorrente;
import objetos.contas.ContaPoupanca;
import telas.Visualizacao;

public class Editar {

    public static void habilitarEdicao(Visualizacao tela) {
        int indice = tela.getJcbNomes().getSelectedIndex();
        if (indice < 0) {
            JOptionPane.showMessageDialog(null, "Selecione um cliente antes de editar.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // prepara a tela e mostra os inputs
        tela.enterEditMode();
    }

    public static void confirmarEdicao(Visualizacao tela) {
        int indice = tela.getJcbNomes().getSelectedIndex();
        if (indice < 0) return;
        Cliente cliente = Gravar.listaClientes.get(indice);

        // valida e aplica
        try {
            cliente.getConta().setAgencia(tela.getJtfAgencia().getText());
            cliente.getConta().setNumeroConta(tela.getJtfConta().getText());
            cliente.setNome(tela.getJtfNome().getText());
            cliente.setEndereco(tela.getJtfEndereco().getText());
            cliente.setTelefone(tela.getJtfTelefone().getText());
            cliente.setCpf(tela.getJtfCpf().getText());
            String saldoText = tela.getJtfSaldo().getText().replace("R$", "").trim();
            if (saldoText.isEmpty()) saldoText = "0";
            double saldo = Double.parseDouble(saldoText);
            cliente.getConta().setSaldo(saldo);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Saldo inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // ajustar tipo de conta se necessário (ex.: trocar entre Corrente/Poupança)
        if (tela.getJrbCorrente().isSelected() && !(cliente.getConta() instanceof ContaCorrente)) {
            cliente.setConta(new ContaCorrente(cliente.getConta().getAgencia(), cliente.getConta().getNumeroConta(), cliente.getConta().getSaldo()));
        } else if (tela.getJrbPoupanca().isSelected() && (cliente.getConta() instanceof ContaCorrente)) {
            cliente.setConta(new ContaPoupanca(cliente.getConta().getAgencia(), cliente.getConta().getNumeroConta(), cliente.getConta().getSaldo()));
        }

        // Atualiza combo (nome pode ter mudado)
        tela.atualizarListaClientes();
        tela.getJcbNomes().setSelectedIndex(indice); // manter seleção

        // volta para modo display e atualiza labels
        tela.exitEditMode(cliente);

        JOptionPane.showMessageDialog(null, "Cadastro atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }
}