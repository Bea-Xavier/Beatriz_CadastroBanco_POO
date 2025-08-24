package telas;

import funcoes.Editar;
import funcoes.Excluir;
import funcoes.Gravar;
import javax.swing.*;
import javax.swing.text.MaskFormatter;

import java.awt.event.ActionEvent;

import objetos.usuarios.Cliente;
import objetos.contas.ContaCorrente;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.KeyEvent;

public class Visualizacao extends JPanel {
    private Janela janela;

    JPanel painelPrincipal = new JPanel();
    JPanel painelSuperior = new JPanel();
    JPanel painelInfo = new JPanel();

    // Mascáras para os campos númericos
    MaskFormatter mascaraAgencia, mascaraConta, mascaraTelefone, mascaraCpf, mascaraSaldo;

    {
        try {
            mascaraAgencia = new MaskFormatter("####");
            mascaraAgencia.setPlaceholderCharacter('_');
            mascaraConta = new MaskFormatter("#######-#");
            mascaraConta.setPlaceholderCharacter('_');
            mascaraTelefone = new MaskFormatter("(##) #####-####");
            mascaraTelefone.setPlaceholderCharacter('_');
            mascaraCpf = new MaskFormatter("###.###.###-##");
            mascaraCpf.setPlaceholderCharacter('_');
            mascaraSaldo = new MaskFormatter("R$ ######.##");
            mascaraSaldo.setPlaceholderCharacter('_');
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    KeyStroke voltar = KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.ALT_DOWN_MASK);

    // Atributos
    JLabel jlTitulo = new JLabel("Consulta de Cadastro");
    JLabel jlInfo = new JLabel("Escolha o nome do cliente:");
    JComboBox<String> jcbNomes = new JComboBox<String>();

    // Botões de Funcionalidades
    JButton jbVoltar = new JButton("Voltar");
    JButton jbEditar = new JButton("Editar");
    JButton jbConfirmar = new JButton("Confirmar");
    JButton jbExcluir = new JButton("Excluir");

    // Labels de exibição dos dados
    JLabel agencia = new JLabel();
    JLabel conta = new JLabel();
    JLabel nome = new JLabel();
    JLabel endereco = new JLabel();
    JLabel telefone = new JLabel();
    JLabel cpf = new JLabel();
    JLabel saldo = new JLabel();
    JLabel tipoConta = new JLabel();

    // Labels de instrução (edição)
    JLabel lblAgencia = new JLabel("Agência:");
    JLabel lblConta = new JLabel("Conta:");
    JLabel lblNome = new JLabel("Nome:");
    JLabel lblEndereco = new JLabel("Endereço:");
    JLabel lblTelefone = new JLabel("Telefone:");
    JLabel lblCpf = new JLabel("CPF:");
    JLabel lblSaldo = new JLabel("Saldo:");

    // Inputs para editar os dados
    JFormattedTextField jtfAgencia = new JFormattedTextField(mascaraAgencia);
    JFormattedTextField jtfConta = new JFormattedTextField(mascaraConta);
    JSeparator jSeparator01 = new JSeparator();
    JTextField jtfNome = new JTextField();
    JTextField jtfEndereco = new JTextField();
    JFormattedTextField jtfTelefone = new JFormattedTextField(mascaraTelefone);
    JFormattedTextField jtfCpf = new JFormattedTextField(mascaraCpf);
    JFormattedTextField jtfSaldo = new JFormattedTextField(mascaraSaldo);
    JRadioButton jrbCorrente = new JRadioButton("Conta Corrente");
    JRadioButton jrbPoupanca = new JRadioButton("Conta Poupança");
    ButtonGroup bgContas = new ButtonGroup();

    // Construtor
    public Visualizacao(Janela janela) {
        this.janela = janela;

        // Layout da próprio tela
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Painel principal com layout vertical
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(8, 20, 10, 20));

        // Painel superior com FlowLayout
        painelSuperior.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Painel de informações também em coluna
        painelInfo.setLayout(new BoxLayout(painelInfo, BoxLayout.Y_AXIS));

        jlTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        painelPrincipal.add(jlTitulo);
        painelPrincipal.add(Box.createVerticalStrut(15));

        jcbNomes.setPreferredSize(new Dimension(200, 25));

        jbConfirmar.setEnabled(false);

        // agencia.setAlignmentX(CENTER_ALIGNMENT);
        // conta.setAlignmentX(CENTER_ALIGNMENT);
        // nome.setAlignmentX(CENTER_ALIGNMENT);
        // endereco.setAlignmentX(CENTER_ALIGNMENT);
        // telefone.setAlignmentX(CENTER_ALIGNMENT);
        // cpf.setAlignmentX(CENTER_ALIGNMENT);
        // saldo.setAlignmentX(CENTER_ALIGNMENT);
        // tipoConta.setAlignmentX(CENTER_ALIGNMENT);

        // jtfAgencia.setAlignmentX(CENTER_ALIGNMENT);
        // jtfConta.setAlignmentX(CENTER_ALIGNMENT);
        // jtfNome.setAlignmentX(CENTER_ALIGNMENT);
        // jtfEndereco.setAlignmentX(CENTER_ALIGNMENT);
        // jtfTelefone.setAlignmentX(CENTER_ALIGNMENT);
        // jtfCpf.setAlignmentX(CENTER_ALIGNMENT);
        // jtfSaldo.setAlignmentX(CENTER_ALIGNMENT);

        // Ação do botão Voltar
        jbVoltar.addActionListener(_ -> {
            janela.mostrarFormulario();
        });
        jbVoltar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(voltar, "voltar");
        jbVoltar.getActionMap().put("voltar", voltar_Action);

        // Ações do Botão de Editar, Confirmar e Excluir
        jbEditar.addActionListener(_ -> Editar.habilitarEdicao(this));
        jbConfirmar.addActionListener(_ -> Editar.confirmarEdicao(this));
        jbExcluir.addActionListener(_ -> Excluir.excluirCadastro(jcbNomes, janela));

        // Preencher combo com clientes já cadastrados
        atualizarListaClientes();

        // Atualizar infos ao trocar de cliente
        jcbNomes.addActionListener(_ -> mostrarDadosSelecionado());

        // Montagem do painel superior
        painelSuperior.add(jlInfo);
        painelSuperior.add(jcbNomes);
        painelSuperior.add(jbVoltar);
        painelSuperior.add(jbEditar);
        painelSuperior.add(jbConfirmar);
        painelSuperior.add(jbExcluir);

        painelPrincipal.add(painelSuperior);
        painelPrincipal.add(Box.createVerticalStrut(20));

        // Adicionando os labels de visualização ao painel de informações
        painelInfo.add(agencia);
        painelInfo.add(conta);
        painelInfo.add(nome);
        painelInfo.add(endereco);
        painelInfo.add(telefone);
        painelInfo.add(cpf);
        painelInfo.add(saldo);
        painelInfo.add(tipoConta);

        // Inputs e labels de edição (escondidos por padrão)
        addCampoEdicao(lblAgencia, jtfAgencia);
        addCampoEdicao(lblConta, jtfConta);
        addCampoEdicao(lblNome, jtfNome);
        addCampoEdicao(lblEndereco, jtfEndereco);
        addCampoEdicao(lblTelefone, jtfTelefone);
        addCampoEdicao(lblCpf, jtfCpf);
        addCampoEdicao(lblSaldo, jtfSaldo);

        painelInfo.add(jrbCorrente);
        painelInfo.add(jrbPoupanca);
        jrbCorrente.setVisible(false);
        jrbPoupanca.setVisible(false);
        bgContas.add(jrbCorrente);
        bgContas.add(jrbPoupanca);

        painelPrincipal.add(painelInfo);

        // ScrollPane envolvendo o painel principal
        JScrollPane scrollPane = new JScrollPane(
                painelPrincipal,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        add(scrollPane, BorderLayout.CENTER);
    }

    private void addCampoEdicao(JLabel label, JComponent input) {
        label.setVisible(false);
        input.setVisible(false);
        painelInfo.add(label);
        painelInfo.add(input);
    }

    // Método para atualizar os nomes do ComboBox a cada novo cadastro de cliente
    public void atualizarListaClientes() {
        jcbNomes.removeAllItems();
        for (int i = 0; i < Gravar.listaClientes.size(); i++) {
            jcbNomes.addItem(Gravar.listaClientes.get(i).getNome());
        }
    }

    // Método para exibir os dados do cliente selecionado
    public void mostrarDadosSelecionado() {
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
            tipoConta.setText("Tipo de Conta: " +
                    (cliente.getConta() instanceof ContaCorrente ? "Conta Corrente" : "Conta Poupança"));
        }
    }

    // Configuração dos Atalhos
    Action voltar_Action = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            jbVoltar.doClick();
        }
    };

    // Getters para o Editar.java acessar
    public JComboBox<String> getJcbNomes() {
        return jcbNomes;
    }

    public JButton getJbConfirmar() {
        return jbConfirmar;
    }

    public JLabel getAgencia() {
        return agencia;
    }

    public JLabel getConta() {
        return conta;
    }

    public JLabel getNome() {
        return nome;
    }

    public JLabel getEndereco() {
        return endereco;
    }

    public JLabel getTelefone() {
        return telefone;
    }

    public JLabel getCpf() {
        return cpf;
    }

    public JLabel getSaldo() {
        return saldo;
    }

    public JLabel getTipoConta() {
        return tipoConta;
    }

    public JLabel getLblAgencia() {
        return lblAgencia;
    }

    public JLabel getLblConta() {
        return lblConta;
    }

    public JLabel getLblNome() {
        return lblNome;
    }

    public JLabel getLblEndereco() {
        return lblEndereco;
    }

    public JLabel getLblTelefone() {
        return lblTelefone;
    }

    public JLabel getLblCpf() {
        return lblCpf;
    }

    public JLabel getLblSaldo() {
        return lblSaldo;
    }

    public JFormattedTextField getJtfAgencia() {
        return jtfAgencia;
    }

    public JFormattedTextField getJtfConta() {
        return jtfConta;
    }

    public JTextField getJtfNome() {
        return jtfNome;
    }

    public JTextField getJtfEndereco() {
        return jtfEndereco;
    }

    public JFormattedTextField getJtfTelefone() {
        return jtfTelefone;
    }

    public JFormattedTextField getJtfCpf() {
        return jtfCpf;
    }

    public JFormattedTextField getJtfSaldo() {
        return jtfSaldo;
    }

    public JRadioButton getJrbCorrente() {
        return jrbCorrente;
    }

    public JRadioButton getJrbPoupanca() {
        return jrbPoupanca;
    }
}
