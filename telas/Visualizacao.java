package telas;

import funcoes.Gravar;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import objetos.usuarios.Cliente;
import objetos.contas.ContaCorrente;
import java.awt.Dimension;

import java.awt.FlowLayout;

public class Visualizacao extends JPanel {
    private Janela janela;
    JPanel painelPrincipal = new JPanel();
    JPanel painelSuperior = new JPanel();
    JPanel painelInfo = new JPanel();

    // Atributos
    JLabel jlTitulo = new JLabel("Vizualização de Cadastro");
    JLabel jlInfo = new JLabel("Escolha o nome do cliente:");
    JComboBox<String> jcbNomes = new JComboBox<String>();
    JButton jbVoltar = new JButton("Voltar");
    JLabel agencia = new JLabel();
    JLabel conta = new JLabel();
    JLabel nome = new JLabel();
    JLabel endereco = new JLabel();
    JLabel telefone = new JLabel();
    JLabel cpf = new JLabel();
    JLabel saldo = new JLabel();
    JLabel tipoConta = new JLabel();

    public Visualizacao(Janela janela) {
        this.janela = janela;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelSuperior.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelInfo.setLayout(new BoxLayout(painelInfo, BoxLayout.Y_AXIS));

        jlTitulo.setAlignmentX(CENTER_ALIGNMENT);
        jcbNomes.setPreferredSize(new Dimension(200, 25));

        agencia.setAlignmentX(CENTER_ALIGNMENT);
        conta.setAlignmentX(CENTER_ALIGNMENT);
        nome.setAlignmentX(CENTER_ALIGNMENT);
        endereco.setAlignmentX(CENTER_ALIGNMENT);
        telefone.setAlignmentX(CENTER_ALIGNMENT);
        cpf.setAlignmentX(CENTER_ALIGNMENT);
        saldo.setAlignmentX(CENTER_ALIGNMENT);
        tipoConta.setAlignmentX(CENTER_ALIGNMENT);

        // Nomes dos clientes do ComboBox
        for (int i = 0; i < Gravar.listaClientes.size(); i++) {
            jcbNomes.addItem(Gravar.listaClientes.get(i).getNome());
        }

        // Ação do ComboBox
        jcbNomes.addActionListener(_ -> {
            int indice = jcbNomes.getSelectedIndex();
            if (indice >= 0 && indice < Gravar.listaClientes.size()) {
                Cliente cliente = Gravar.listaClientes.get(indice);
                agencia.setText("Agência: " + cliente.getConta().getAgencia());
                conta.setText("Conta: " + cliente.getConta().getNumeroConta());
                nome.setText("Nome: " + cliente.getNome());
                endereco.setText("Endereço: " + cliente.getEndereco());
                telefone.setText("Telefone: " + cliente.getTelefone());
                cpf.setText("CPF: " + cliente.getCpf());
                saldo.setText(String.format("Saldo: R$ %.2f", cliente.getConta().getSaldo()));
                tipoConta.setText("Tipo de Conta: "
                        + (cliente.getConta() instanceof ContaCorrente ? "Conta Corrente" : "Conta Poupança"));
            }
        });

        // Ação do botão Voltar
        jbVoltar.addActionListener(_ -> {
            janela.mostrarFormulario();
        });

        painelSuperior.add(jlInfo);
        painelSuperior.add(jcbNomes);
        painelSuperior.add(jbVoltar);
        painelPrincipal.add(jlTitulo);
        painelPrincipal.add(painelSuperior);
        painelInfo.add(agencia);
        painelInfo.add(conta);
        painelInfo.add(nome);
        painelInfo.add(endereco);
        painelInfo.add(telefone);
        painelInfo.add(cpf);
        painelInfo.add(saldo);
        painelInfo.add(tipoConta);
        painelPrincipal.add(painelInfo);
        add(painelPrincipal);
    }
}
