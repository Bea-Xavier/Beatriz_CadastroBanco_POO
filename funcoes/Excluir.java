package funcoes;

import javax.swing.*;

import objetos.usuarios.Cliente;

import java.util.List;

public class Excluir {

    // supondo que você tem a mesma lista estática em Gravar.java
    public static List<Cliente> clientes = Gravar.listaClientes;

    public static void excluirCadastro(JComboBox<String> jcbNomes, JFrame janela) {
        String selecionado = (String) jcbNomes.getSelectedItem();

        if (selecionado == null || selecionado.isEmpty()) {
            JOptionPane.showMessageDialog(janela,
                    "Selecione um cliente para excluir!",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int opcao = JOptionPane.showConfirmDialog(
                janela,
                "Deseja realmente excluir o cliente \"" + selecionado + "\"?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION);

        if (opcao == JOptionPane.YES_OPTION) {
            clientes.remove(selecionado);
            jcbNomes.removeItem(selecionado);

            JOptionPane.showMessageDialog(janela,
                    "Cliente excluído com sucesso!",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}