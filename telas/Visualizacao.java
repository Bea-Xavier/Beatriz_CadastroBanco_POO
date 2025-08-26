package telas;

import funcoes.Editar;
import funcoes.Excluir;
import funcoes.Gravar;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import objetos.usuarios.Cliente;
import objetos.contas.ContaCorrente;

public class Visualizacao extends JPanel {
    private Janela janela;

    // painéis
    JPanel painelPrincipal = new JPanel();
    JPanel painelHeader = new JPanel(); // contém título
    JPanel painelSelecao = new JPanel(); // linha: label + combo
    JPanel painelBotoes = new JPanel(); // linha: botões
    JPanel painelInfo = new JPanel(); // cards (display/edit)

    // máscaras
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

    // criação dos atalhps

    KeyStroke voltar = KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.ALT_DOWN_MASK);
    KeyStroke editar = KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.ALT_DOWN_MASK);
    KeyStroke confirmar = KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.ALT_DOWN_MASK);
    KeyStroke excluir = KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.ALT_DOWN_MASK);

    // cabeçalho
    JLabel jlTitulo = new JLabel("Consulta de Cadastro");
    JLabel jlInfo = new JLabel("Escolha o nome do cliente:");
    JComboBox<String> jcbNomes = new JComboBox<>();

    // botões
    JButton jbVoltar = new JButton("Voltar");
    JButton jbEditar = new JButton("Editar");
    JButton jbConfirmar = new JButton("Confirmar");
    JButton jbExcluir = new JButton("Excluir");

    // display labels
    JLabel agencia = new JLabel();
    JLabel conta = new JLabel();
    JLabel nome = new JLabel();
    JLabel endereco = new JLabel();
    JLabel telefone = new JLabel();
    JLabel cpf = new JLabel();
    JLabel saldo = new JLabel();
    JLabel tipoConta = new JLabel();

    // labels de instrução (edição)
    JLabel lblAgencia = new JLabel("Agência:");
    JLabel lblConta = new JLabel("Conta:");
    JLabel lblNome = new JLabel("Nome:");
    JLabel lblEndereco = new JLabel("Endereço:");
    JLabel lblTelefone = new JLabel("Telefone:");
    JLabel lblCpf = new JLabel("CPF:");
    JLabel lblSaldo = new JLabel("Saldo:");

    // inputs de edição
    JFormattedTextField jtfAgencia = new JFormattedTextField(mascaraAgencia);
    JFormattedTextField jtfConta = new JFormattedTextField(mascaraConta);
    JTextField jtfNome = new JTextField();
    JTextField jtfEndereco = new JTextField();
    JFormattedTextField jtfTelefone = new JFormattedTextField(mascaraTelefone);
    JFormattedTextField jtfCpf = new JFormattedTextField(mascaraCpf);
    JFormattedTextField jtfSaldo = new JFormattedTextField(mascaraSaldo);
    JRadioButton jrbCorrente = new JRadioButton("Conta Corrente");
    JRadioButton jrbPoupanca = new JRadioButton("Conta Poupança");
    ButtonGroup bgContas = new ButtonGroup();

    // cards por linha (cada linha é um CardLayout para trocar display/edit)
    private JPanel cardAgencia, cardConta, cardNome, cardEndereco, cardTelefone, cardCpf, cardSaldo;

    public Visualizacao(Janela janela) {
        this.janela = janela;

        // layout principal do panel (coloca o scroll no center)
        setLayout(new BorderLayout());

        // painelPrincipal organiza conteúdo verticalmente e centraliza internamente
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));

        // --- header (título) ---
        painelHeader.setLayout(new FlowLayout(FlowLayout.CENTER));
        jlTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        painelHeader.add(jlTitulo);
        painelPrincipal.add(painelHeader);
        painelPrincipal.add(Box.createVerticalStrut(10));

        // --- seleção (rótulo + combobox) ---
        painelSelecao.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        jlInfo.setHorizontalAlignment(SwingConstants.CENTER);
        jcbNomes.setPreferredSize(new Dimension(190, 22));
        painelSelecao.add(jlInfo);
        painelSelecao.add(jcbNomes);
        painelPrincipal.add(painelSelecao);
        painelPrincipal.add(Box.createVerticalStrut(8));

        // --- botões (linha separada, centrada) ---
        painelBotoes.setLayout(new FlowLayout(FlowLayout.CENTER, 12, 6));
        jbConfirmar.setEnabled(false);
        painelBotoes.add(jbVoltar);
        painelBotoes.add(jbEditar);
        painelBotoes.add(jbConfirmar);
        painelBotoes.add(jbExcluir);
        painelPrincipal.add(painelBotoes);
        painelPrincipal.add(Box.createVerticalStrut(12));

        // --- painelInfo: que irá conter os cards
        painelInfo.setLayout(new BoxLayout(painelInfo, BoxLayout.Y_AXIS));
        painelInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelInfo.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

        // linhas (cada card substitui display/edit)
        cardAgencia = criarLinhaInfo(agencia, lblAgencia, jtfAgencia);
        cardConta = criarLinhaInfo(conta, lblConta, jtfConta);
        cardNome = criarLinhaInfo(nome, lblNome, jtfNome);
        cardEndereco = criarLinhaInfo(endereco, lblEndereco, jtfEndereco);
        cardTelefone = criarLinhaInfo(telefone, lblTelefone, jtfTelefone);
        cardCpf = criarLinhaInfo(cpf, lblCpf, jtfCpf);
        cardSaldo = criarLinhaInfo(saldo, lblSaldo, jtfSaldo);

        // adiciona as linhas (com espaçamento)
        painelInfo.add(cardAgencia);
        painelInfo.add(Box.createVerticalStrut(6));
        painelInfo.add(cardConta);
        painelInfo.add(Box.createVerticalStrut(6));
        painelInfo.add(cardNome);
        painelInfo.add(Box.createVerticalStrut(6));
        painelInfo.add(cardEndereco);
        painelInfo.add(Box.createVerticalStrut(6));
        painelInfo.add(cardTelefone);
        painelInfo.add(Box.createVerticalStrut(6));
        painelInfo.add(cardCpf);
        painelInfo.add(Box.createVerticalStrut(6));
        painelInfo.add(cardSaldo);
        painelInfo.add(Box.createVerticalStrut(8));

        // tipo de conta (apenas display)
        tipoConta.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelInfo.add(tipoConta);

        // radios (aparecem só no modo edição)
        JPanel radios = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 2));
        bgContas.add(jrbCorrente);
        bgContas.add(jrbPoupanca);
        jrbCorrente.setVisible(false);
        jrbPoupanca.setVisible(false);
        radios.add(jrbCorrente);
        radios.add(jrbPoupanca);
        painelInfo.add(Box.createVerticalStrut(8));
        painelInfo.add(radios);

        painelPrincipal.add(painelInfo);
        painelPrincipal.add(Box.createVerticalGlue()); // empurra conteúdo para cima

        // --- scroll ---
        JScrollPane scrollPane = new JScrollPane(painelPrincipal,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(420, 340)); // mantém janela do tamanho desejado
        add(scrollPane, BorderLayout.CENTER);

        // --- ações dos botões ---
        jbVoltar.addActionListener(_ -> janela.mostrarFormulario());
        jbVoltar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(voltar, "voltar");
        jbVoltar.getActionMap().put("voltar", voltar_Action);

        jbEditar.addActionListener(_ -> Editar.habilitarEdicao(this));
        jbEditar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(editar, "editar");
        jbEditar.getActionMap().put("editar", editar_Action);

        jbConfirmar.addActionListener(_ -> Editar.confirmarEdicao(this));
        jbConfirmar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(confirmar, "confirmar");
        jbConfirmar.getActionMap().put("confirmar", confirmar_Action);

        jbExcluir.addActionListener(_ -> Excluir.excluirCadastro(jcbNomes, this));
        jbExcluir.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(excluir, "excluir");
        jbExcluir.getActionMap().put("excluir", excluir_Action);

        jcbNomes.addActionListener(_ -> mostrarDadosSelecionado());

        // preencher lista e mostrar primeiro item se houver
        atualizarListaClientes();
        if (jcbNomes.getItemCount() > 0) {
            jcbNomes.setSelectedIndex(0);
            mostrarDadosSelecionado();
        }
    }

    // cria linha com CardLayout: "display" e "edit"
    private JPanel criarLinhaInfo(JLabel labelExib, JLabel labelInstr, JComponent input) {
        JPanel card = new JPanel(new CardLayout());
        card.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.setMaximumSize(new Dimension(380, 36)); // limita largura para centralizar bem

        // display panel (apenas labelExib)
        JPanel display = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 2));
        display.setOpaque(false);
        labelExib.setFont(labelExib.getFont().deriveFont(Font.PLAIN));
        display.add(labelExib);

        // edit panel (instrucao + input) - centralizado
        JPanel edit = new JPanel(new BorderLayout(6, 0));
        edit.setOpaque(false);
        labelInstr.setVisible(true);
        labelInstr.setHorizontalAlignment(SwingConstants.LEFT);
        input.setPreferredSize(new Dimension(240, 24));
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        left.setOpaque(false);
        left.add(labelInstr);
        edit.add(left, BorderLayout.WEST);
        edit.add(input, BorderLayout.CENTER);

        card.add(display, "display");
        card.add(edit, "edit");

        // começar em display
        ((CardLayout) card.getLayout()).show(card, "display");
        return card;
    }

    // ativar modo edição: mostra os cards "edit" e preenche inputs
    public void enterEditMode() {
        int idx = jcbNomes.getSelectedIndex();
        if (idx < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para editar.", "Atenção",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        Cliente c = Gravar.listaClientes.get(idx);
        jtfAgencia.setText(c.getConta().getAgencia());
        jtfConta.setText(c.getConta().getNumeroConta());
        jtfNome.setText(c.getNome());
        jtfEndereco.setText(c.getEndereco());
        jtfTelefone.setText(c.getTelefone());
        jtfCpf.setText(c.getCpf());
        jtfSaldo.setText(String.valueOf(c.getConta().getSaldo()));
        jrbCorrente.setSelected(c.getConta() instanceof ContaCorrente);
        jrbPoupanca.setSelected(!jrbCorrente.isSelected());

        ((CardLayout) cardAgencia.getLayout()).show(cardAgencia, "edit");
        ((CardLayout) cardConta.getLayout()).show(cardConta, "edit");
        ((CardLayout) cardNome.getLayout()).show(cardNome, "edit");
        ((CardLayout) cardEndereco.getLayout()).show(cardEndereco, "edit");
        ((CardLayout) cardTelefone.getLayout()).show(cardTelefone, "edit");
        ((CardLayout) cardCpf.getLayout()).show(cardCpf, "edit");
        ((CardLayout) cardSaldo.getLayout()).show(cardSaldo, "edit");

        jrbCorrente.setVisible(true);
        jrbPoupanca.setVisible(true);
        jbConfirmar.setEnabled(true);
        revalidate();
        repaint();
    }

    // sair do modo edição atualizando display recebe cliente atualizado
    public void exitEditModeAndRefresh(Cliente c) {
        // atualiza lista/labels
        mostrarDadosSelecionado();
        ((CardLayout) cardAgencia.getLayout()).show(cardAgencia, "display");
        ((CardLayout) cardConta.getLayout()).show(cardConta, "display");
        ((CardLayout) cardNome.getLayout()).show(cardNome, "display");
        ((CardLayout) cardEndereco.getLayout()).show(cardEndereco, "display");
        ((CardLayout) cardTelefone.getLayout()).show(cardTelefone, "display");
        ((CardLayout) cardCpf.getLayout()).show(cardCpf, "display");
        ((CardLayout) cardSaldo.getLayout()).show(cardSaldo, "display");

        jrbCorrente.setVisible(false);
        jrbPoupanca.setVisible(false);
        jbConfirmar.setEnabled(false);
        revalidate();
        repaint();
    }

    // atualiza a lista dos nomes no comboBox

    public void atualizarListaClientes() {
        jcbNomes.removeAllItems();
        for (Cliente c : Gravar.listaClientes)
            jcbNomes.addItem(c.getNome());
    }

    // exibe os dados cadastrados nos labels de display

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
            tipoConta.setText("Tipo de Conta: "
                    + (cliente.getConta() instanceof ContaCorrente ? "Conta Corrente" : "Conta Poupança"));
        } else {
            agencia.setText("");
            conta.setText("");
            nome.setText("");
            endereco.setText("");
            telefone.setText("");
            cpf.setText("");
            saldo.setText("");
            tipoConta.setText("");
        }
        revalidate();
        repaint();
    }

    // atalhos
    Action voltar_Action = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            jbVoltar.doClick();
        }
    };

    Action editar_Action = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            jbEditar.doClick();
        }
    };

    Action confirmar_Action = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            jbConfirmar.doClick();
        }
    };

    Action excluir_Action = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            jbExcluir.doClick();
        }
    };

    // getters para Editar e Excluir
    public JComboBox<String> getJcbNomes() {
        return jcbNomes;
    }

    public JButton getJbConfirmar() {
        return jbConfirmar;
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

    // métodos auxiliares chamados por Editar
    public void enterEditModePublic() {
        enterEditMode();
    }

    public void exitEditModePublic(Cliente c) {
        exitEditModeAndRefresh(c);
    }
}