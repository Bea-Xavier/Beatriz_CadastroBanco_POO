package funcoes;

import javax.swing.*;
import objetos.usuarios.Cliente;
import telas.Visualizacao;

public class Excluir {
    public static void excluirCadastro(JComboBox<String> jcb, Visualizacao tela) {
        int idx = jcb.getSelectedIndex();
        if (idx < 0) {
            JOptionPane.showMessageDialog(null, "Selecione um cliente para excluir.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Cliente c = Gravar.listaClientes.get(idx);
        int opt = JOptionPane.showConfirmDialog(null, "Confirma exclusão de \"" + c.getNome() + "\"?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opt == JOptionPane.YES_OPTION) {
            Gravar.listaClientes.remove(idx);
            tela.atualizarListaClientes();
            if (tela.getJcbNomes().getItemCount() > 0) {
                tela.getJcbNomes().setSelectedIndex(0);
                tela.mostrarDadosSelecionado();
            } else {
                tela.mostrarDadosSelecionado(); // limpa
            }
            JOptionPane.showMessageDialog(null, "Cliente excluído com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}