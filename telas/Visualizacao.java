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

    JPanel painelPrincipal = new JPanel();
    JPanel painelSuperior = new JPanel();
    JPanel painelInfo = new JPanel();

    // Máscaras
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

    // Cabeçalho
    JLabel jlTitulo = new JLabel("Consulta de Cadastro");
    JLabel jlInfo = new JLabel("Escolha o nome do cliente:");
    JComboBox<String> jcbNomes = new JComboBox<>();

    // Botões
    JButton jbVoltar = new JButton("Voltar");
    JButton jbEditar = new JButton("Editar");
    JButton jbConfirmar = new JButton("Confirmar");
    JButton jbExcluir = new JButton("Excluir");

    // Labels (display)
    JLabel agencia = new JLabel();
    JLabel conta = new JLabel();
    JLabel nome = new JLabel();
    JLabel endereco = new JLabel();
    JLabel telefone = new JLabel();
    JLabel cpf = new JLabel();
    JLabel saldo = new JLabel();
    JLabel tipoConta = new JLabel();

    // Labels instrução (edição)
    JLabel lblAgencia = new JLabel("Agência:");
    JLabel lblConta = new JLabel("Conta:");
    JLabel lblNome = new JLabel("Nome:");
    JLabel lblEndereco = new JLabel("Endereço:");
    JLabel lblTelefone = new JLabel("Telefone:");
    JLabel lblCpf = new JLabel("CPF:");
    JLabel lblSaldo = new JLabel("Saldo:");

    // Inputs (edição)
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

    // Card panels (cada linha é um CardLayout para trocar display/edit)
    private JPanel cardAgencia, cardConta, cardNome, cardEndereco, cardTelefone, cardCpf, cardSaldo;

    public Visualizacao(Janela janela) {
        this.janela = janela;

        // Layout da tela
        setLayout(new BorderLayout());

        // painel principal vertical
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(8, 12, 12, 12));

        // título
        jlTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        jlTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        painelPrincipal.add(jlTitulo);
        painelPrincipal.add(Box.createVerticalStrut(10));

        // painel superior (combo + botões)
        painelSuperior.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 6));
        jcbNomes.setPreferredSize(new Dimension(220, 24));
        jbConfirmar.setEnabled(false);
        painelSuperior.add(jlInfo);
        painelSuperior.add(jcbNomes);
        painelSuperior.add(jbVoltar);
        painelSuperior.add(jbEditar);
        painelSuperior.add(jbConfirmar);
        painelSuperior.add(jbExcluir);
        painelPrincipal.add(painelSuperior);
        painelPrincipal.add(Box.createVerticalStrut(10));

        // painel info (linhas)
        painelInfo.setLayout(new BoxLayout(painelInfo, BoxLayout.Y_AXIS));
        painelInfo.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelInfo.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

        // criar linhas (cada card substitui display/edit)
        cardAgencia = criarLinhaInfo(agencia, lblAgencia, jtfAgencia);
        cardConta = criarLinhaInfo(conta, lblConta, jtfConta);
        cardNome = criarLinhaInfo(nome, lblNome, jtfNome);
        cardEndereco = criarLinhaInfo(endereco, lblEndereco, jtfEndereco);
        cardTelefone = criarLinhaInfo(telefone, lblTelefone, jtfTelefone);
        cardCpf = criarLinhaInfo(cpf, lblCpf, jtfCpf);
        cardSaldo = criarLinhaInfo(saldo, lblSaldo, jtfSaldo);

        // rótulo tipoConta (apenas display)
        tipoConta.setAlignmentX(Component.LEFT_ALIGNMENT);

        painelInfo.add(cardAgencia);
        painelInfo.add(cardConta);
        painelInfo.add(cardNome);
        painelInfo.add(cardEndereco);
        painelInfo.add(cardTelefone);
        painelInfo.add(cardCpf);
        painelInfo.add(cardSaldo);
        painelInfo.add(Box.createVerticalStrut(6));
        painelInfo.add(tipoConta);

        // radio buttons (aparecem apenas no modo edição)
        JPanel radios = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        bgContas.add(jrbCorrente);
        bgContas.add(jrbPoupanca);
        jrbCorrente.setVisible(false);
        jrbPoupanca.setVisible(false);
        radios.add(jrbCorrente);
        radios.add(jrbPoupanca);
        painelInfo.add(radios);

        painelPrincipal.add(painelInfo);
        painelPrincipal.add(Box.createVerticalGlue()); // empurra conteúdo para cima

        // colocar painelPrincipal dentro do scroll
        JScrollPane scrollPane = new JScrollPane(painelPrincipal,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        // define preferências de tamanho do scroll/viewport para habilitar barra
        scrollPane.setPreferredSize(new Dimension(400, 305));
        add(scrollPane, BorderLayout.CENTER);

        // ações
        jbVoltar.addActionListener(_ -> janela.mostrarFormulario());
        jbVoltar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(voltar, "voltar");
        jbVoltar.getActionMap().put("voltar", voltar_Action);

        jbEditar.addActionListener(_ -> Editar.habilitarEdicao(this));
        jbConfirmar.addActionListener(_ -> Editar.confirmarEdicao(this));
        jbExcluir.addActionListener(_ -> Excluir.excluirCadastro(jcbNomes, this));

        jcbNomes.addActionListener(_ -> mostrarDadosSelecionado());

        // preencher lista
        atualizarListaClientes();
        // exibir primeiro (se houver)
        if (jcbNomes.getItemCount() > 0) {
            jcbNomes.setSelectedIndex(0);
            mostrarDadosSelecionado();
        }
    }

    // cria linha com CardLayout: card "display" e card "edit"
    private JPanel criarLinhaInfo(JLabel labelExib, JLabel labelInstr, JComponent input) {
        JPanel card = new JPanel(new CardLayout());
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.setMaximumSize(new Dimension(1000, 36)); // evita esticar horizontalmente no BoxLayout

        // display panel (apenas labelExib)
        JPanel display = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 2));
        display.add(labelExib);

        // edit panel (instrucao + input)
        JPanel edit = new JPanel(new BorderLayout(6, 2));
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        left.add(labelInstr);
        labelInstr.setVisible(true); // por padrão, instrução visível no edit card
        input.setPreferredSize(new Dimension(220, 24));
        edit.add(left, BorderLayout.WEST);
        edit.add(input, BorderLayout.CENTER);

        card.add(display, "display");
        card.add(edit, "edit");

        // comece no display
        ((CardLayout) card.getLayout()).show(card, "display");
        return card;
    }

    // trocar para modo edição (mostra cards "edit", preenche inputs)
    public void enterEditModeForSelected() {
        int idx = jcbNomes.getSelectedIndex();
        if (idx < 0)
            return;
        Cliente c = Gravar.listaClientes.get(idx);
        // preencher inputs
        jtfAgencia.setText(c.getConta().getAgencia());
        jtfConta.setText(c.getConta().getNumeroConta());
        jtfNome.setText(c.getNome());
        jtfEndereco.setText(c.getEndereco());
        jtfTelefone.setText(c.getTelefone());
        jtfCpf.setText(c.getCpf());
        jtfSaldo.setText(String.valueOf(c.getConta().getSaldo()));
        jrbCorrente.setSelected(c.getConta() instanceof ContaCorrente);
        jrbPoupanca.setSelected(!jrbCorrente.isSelected());

        // mostrar edit cards
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

    // voltar para exibição (mostrar display cards e atualizar labels)
    public void exitEditModeAndRefresh(Cliente c) {
        // atualiza labels com cliente (pode receber cliente já atualizado)
        mostrarDadosSelecionado(); // atualiza labels a partir do combo index atual

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

    // Atualiza a lista do combo
    public void atualizarListaClientes() {
        jcbNomes.removeAllItems();
        for (Cliente c : Gravar.listaClientes)
            jcbNomes.addItem(c.getNome());
    }

    // Preenche labels a partir do cliente selecionado
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
            // limpa
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

    // getters usados por Editar / Excluir
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

    // métodos para Editar chamar
    public void enterEditMode() {
        enterEditModeForSelected();
    }

    public void exitEditMode(Cliente c) {
        exitEditModeAndRefresh(c);
    }
}