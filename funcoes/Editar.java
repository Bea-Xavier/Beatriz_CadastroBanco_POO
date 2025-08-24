package funcoes;

import javax.swing.*;
import objetos.usuarios.Cliente;
import objetos.contas.ContaCorrente;
import objetos.contas.ContaPoupanca;
import telas.Visualizacao;

public class Editar {

    // Habilitar modo de edição
    public static void habilitarEdicao(Visualizacao tela) {
        int indice = tela.getJcbNomes().getSelectedIndex();
        if (indice >= 0) {
            Cliente cliente = Gravar.listaClientes.get(indice);

            // Preenche inputs
            tela.getJtfAgencia().setText(cliente.getConta().getAgencia());
            tela.getJtfConta().setText(cliente.getConta().getNumeroConta());
            tela.getJtfNome().setText(cliente.getNome());
            tela.getJtfEndereco().setText(cliente.getEndereco());
            tela.getJtfTelefone().setText(cliente.getTelefone());
            tela.getJtfCpf().setText(cliente.getCpf());
            tela.getJtfSaldo().setText(String.valueOf(cliente.getConta().getSaldo()));
            tela.getJrbCorrente().setSelected(cliente.getConta() instanceof ContaCorrente);
            tela.getJrbPoupanca().setSelected(cliente.getConta() instanceof ContaPoupanca);

            // Esconde labels
            tela.getAgencia().setVisible(false);
            tela.getConta().setVisible(false);
            tela.getNome().setVisible(false);
            tela.getEndereco().setVisible(false);
            tela.getTelefone().setVisible(false);
            tela.getCpf().setVisible(false);
            tela.getSaldo().setVisible(false);
            tela.getTipoConta().setVisible(false);

            // Mostra inputs + labels de instrução
            tela.getLblAgencia().setVisible(true);
            tela.getLblConta().setVisible(true);
            tela.getLblNome().setVisible(true);
            tela.getLblEndereco().setVisible(true);
            tela.getLblTelefone().setVisible(true);
            tela.getLblCpf().setVisible(true);
            tela.getLblSaldo().setVisible(true);
            tela.getJtfAgencia().setVisible(true);
            tela.getJtfConta().setVisible(true);
            tela.getJtfNome().setVisible(true);
            tela.getJtfEndereco().setVisible(true);
            tela.getJtfTelefone().setVisible(true);
            tela.getJtfCpf().setVisible(true);
            tela.getJtfSaldo().setVisible(true);
            tela.getJrbCorrente().setVisible(true);
            tela.getJrbPoupanca().setVisible(true);

            tela.getJbConfirmar().setEnabled(true);
        }
    }

    // Confirmar edição
    public static void confirmarEdicao(Visualizacao tela) {
        int indice = tela.getJcbNomes().getSelectedIndex();
        if (indice >= 0) {
            Cliente cliente = Gravar.listaClientes.get(indice);

            // Atualiza dados
            cliente.getConta().setAgencia(tela.getJtfAgencia().getText());
            cliente.getConta().setNumeroConta(tela.getJtfConta().getText());
            cliente.setNome(tela.getJtfNome().getText());
            cliente.setEndereco(tela.getJtfEndereco().getText());
            cliente.setTelefone(tela.getJtfTelefone().getText());
            cliente.setCpf(tela.getJtfCpf().getText());
            try {
                cliente.getConta().setSaldo(
                    Double.parseDouble(tela.getJtfSaldo().getText().replace("R$", "").trim())
                );
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Saldo inválido!");
                return;
            }

            if (tela.getJrbCorrente().isSelected()) {
                cliente.setConta(new ContaCorrente(
                    cliente.getConta().getAgencia(),
                    cliente.getConta().getNumeroConta(),
                    cliente.getConta().getSaldo()
                ));
            } else if (tela.getJrbPoupanca().isSelected()) {
                cliente.setConta(new ContaPoupanca(
                    cliente.getConta().getAgencia(),
                    cliente.getConta().getNumeroConta(),
                    cliente.getConta().getSaldo()
                ));
            }

            // Volta para exibição
            tela.getLblAgencia().setVisible(false);
            tela.getLblConta().setVisible(false);
            tela.getLblNome().setVisible(false);
            tela.getLblEndereco().setVisible(false);
            tela.getLblTelefone().setVisible(false);
            tela.getLblCpf().setVisible(false);
            tela.getLblSaldo().setVisible(false);
            tela.getJtfAgencia().setVisible(false);
            tela.getJtfConta().setVisible(false);
            tela.getJtfNome().setVisible(false);
            tela.getJtfEndereco().setVisible(false);
            tela.getJtfTelefone().setVisible(false);
            tela.getJtfCpf().setVisible(false);
            tela.getJtfSaldo().setVisible(false);
            tela.getJrbCorrente().setVisible(false);
            tela.getJrbPoupanca().setVisible(false);

            tela.mostrarDadosSelecionado();
            tela.getJbConfirmar().setEnabled(false);

            JOptionPane.showMessageDialog(null, "Cadastro atualizado com sucesso!");
        }
    }
}