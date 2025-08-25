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
import java.awt.GridLayout;
import java.awt.BorderLayout;
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
        setSize(400, 305);

        // Painel principal com layout vertical
        painelPrincipal.setLayout(null);
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(8, 20, 10, 20));

        // Painel superior com FlowLayout
        painelSuperior.setLayout(null);
        painelSuperior.setBounds(10, 40, 450, 60);

        // Painel de informações também em coluna
        painelInfo.setBounds(10, 100, 370, 350); // Ajuste o tamanho e posição conforme desejar
        painelInfo.setLayout(new GridLayout(0, 1));

        jlTitulo.setBounds(0, 10, 400, 20);
        jlTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        painelPrincipal.add(jlTitulo);

        jcbNomes.setPreferredSize(new Dimension(200, 25));

        jbConfirmar.setEnabled(false);

        // Ação do botão Voltar
        jbVoltar.addActionListener(e -> {
            janela.mostrarFormulario();
        });
        jbVoltar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(voltar, "voltar");
        jbVoltar.getActionMap().put("voltar", voltar_Action);

        // Ações do Botão de Editar, Confirmar e Excluir
        jbEditar.addActionListener(e -> Editar.habilitarEdicao(this));
        jbConfirmar.addActionListener(e -> Editar.confirmarEdicao(this));
        jbExcluir.addActionListener(e -> Excluir.excluirCadastro(jcbNomes, janela));

        // Preencher combo com clientes já cadastrados
        atualizarListaClientes();

        // Atualizar infos ao trocar de cliente
        jcbNomes.addActionListener(e -> mostrarDadosSelecionado());

        GridLayout layout = new GridLayout(1, 0);
        layout.setHgap(8);
        JPanel inputPanel = new JPanel();
        inputPanel.setBounds(0, 0, 370, 25);
        inputPanel.setLayout(layout);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(layout);
        buttonsPanel.setBounds(0, 30, 370, 25);

        // Montagem do painel superior
        inputPanel.add(jlInfo);
        inputPanel.add(jcbNomes);
        painelSuperior.add(inputPanel);
        buttonsPanel.add(jbVoltar);
        buttonsPanel.add(jbEditar);
        buttonsPanel.add(jbConfirmar);
        buttonsPanel.add(jbExcluir);
        painelSuperior.add(buttonsPanel);

        painelPrincipal.add(painelSuperior);
        painelPrincipal.add(Box.createVerticalStrut(20));

        // Adicionando os labels de visualização ao painel de informações
        // painelInfo.add(agencia);
        // painelInfo.add(conta);
        // painelInfo.add(nome);
        // painelInfo.add(endereco);
        // painelInfo.add(telefone);
        // painelInfo.add(cpf);
        // painelInfo.add(saldo);
        // painelInfo.add(tipoConta);

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
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);

        new Timer(100, e -> {
            painelInfo.revalidate();
            painelInfo.repaint();
        }).start();


        // add(painelPrincipal);
    }

    private void addCampoEdicao(JLabel label, JComponent input) {
        label.setVisible(true);
        input.setVisible(true);
        painelInfo.add(label);
        painelInfo.add(input);
        Editar.habilitarEdicao(this);
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
            System.out.print(cliente.getNome());
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
        revalidate();
        repaint();
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
