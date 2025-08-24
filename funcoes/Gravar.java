package funcoes;

import javax.swing.*;
import java.util.ArrayList;
import objetos.contas.Conta;
import objetos.contas.ContaCorrente;
import objetos.contas.ContaPoupanca;
import objetos.usuarios.Cliente;

public class Gravar {
    public static ArrayList<Cliente> listaClientes = new ArrayList<>();

    public boolean cadastrarCliente(
            String nome, String endereco, String telefone, String cpf,
            String agencia, String numero, String saldo, String tipoConta) {
        if (nome.isEmpty() || endereco.isEmpty() || telefone.isEmpty() || cpf.isEmpty() ||
                agencia.isEmpty() || numero.isEmpty() || saldo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos.", "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        Conta conta;
        try {
            double saldoDouble = Double.parseDouble(saldo.replace("R$", "").replace(",", ".").trim());
            if (tipoConta.equals("Conta Corrente")) {
                conta = new ContaCorrente(agencia, numero, saldoDouble);
            } else {
                conta = new ContaPoupanca(agencia, numero, saldoDouble);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Saldo inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        Cliente cliente = new Cliente(nome, endereco, telefone, cpf, conta);
        listaClientes.add(cliente);

        JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!", "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);
        return true;
    }

}
