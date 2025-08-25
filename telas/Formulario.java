package telas;

import java.awt.event.KeyEvent;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;

import funcoes.Gravar;

public class Formulario extends JPanel {
    private Janela janela;

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

    // Definições dos atalhos
    KeyStroke contaCorrente = KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.ALT_DOWN_MASK);
    KeyStroke contaPoupanca = KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.ALT_DOWN_MASK);
    KeyStroke cadastrar = KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK);
    KeyStroke consultar = KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK);

    // Atributos do Painel
    JLabel jlAgencia = new JLabel();
    JFormattedTextField jtfAgencia = new JFormattedTextField(mascaraAgencia);

    JLabel jlConta = new JLabel();
    JFormattedTextField jtfConta = new JFormattedTextField(mascaraConta);

    JSeparator jSeparator01 = new JSeparator();

    JLabel jlNome = new JLabel();
    JTextField jtfNome = new JTextField();

    JLabel jlEndereco = new JLabel();
    JTextField jtfEndereco = new JTextField();

    JLabel jlTelefone = new JLabel();
    JFormattedTextField jtfTelefone = new JFormattedTextField(mascaraTelefone);

    JLabel jlCpf = new JLabel();
    JFormattedTextField jtfCpf = new JFormattedTextField(mascaraCpf);

    JLabel jlSaldo = new JLabel();
    JFormattedTextField jtfSaldo = new JFormattedTextField(mascaraSaldo);

    JRadioButton jrbCorrente = new JRadioButton("Conta Corrente");
    JRadioButton jrbPopupanca = new JRadioButton("Conta Poupança");
    ButtonGroup bgContas = new ButtonGroup();

    JSeparator jSeparator02 = new JSeparator();

    JButton jbConsultar = new JButton("Consultar");
    JButton jbCadastrar = new JButton("Cadastrar");

    // Construtor do Painel
    public Formulario(Janela janela) {
        this.janela = janela;
        setLayout(null);

        // Configuração dos componentes da Janela
        jlAgencia.setText("Código da Agência");
        jlAgencia.setBounds(10, 10, 110, 18);

        add(jlAgencia);

        jtfAgencia.setBounds(125, 10, 34, 20);
        add(jtfAgencia);

        jlConta.setText("Número da Conta");
        jlConta.setBounds(195, 10, 105, 18);
        add(jlConta);

        jtfConta.setBounds(300, 10, 66, 20);
        add(jtfConta);

        jSeparator01.setBounds(10, 40, 365, 10);
        add(jSeparator01);

        jlNome.setText("Nome:");
        jlNome.setBounds(10, 50, 60, 18);
        jlNome.setHorizontalAlignment(SwingConstants.RIGHT);
        add(jlNome);

        jtfNome.setBounds(75, 50, 300, 20);
        add(jtfNome);

        jlEndereco.setText("Endereço:");
        jlEndereco.setBounds(10, 75, 60, 18);
        jlEndereco.setHorizontalAlignment(SwingConstants.RIGHT);
        add(jlEndereco);

        jtfEndereco.setBounds(75, 75, 300, 20);
        add(jtfEndereco);

        jlTelefone.setText("Telefone:");
        jlTelefone.setBounds(10, 100, 60, 18);
        jlTelefone.setHorizontalAlignment(SwingConstants.RIGHT);
        add(jlTelefone);

        jtfTelefone.setBounds(75, 100, 98, 20);
        add(jtfTelefone);

        jlCpf.setText("CPF:");
        jlCpf.setBounds(10, 125, 60, 18);
        jlCpf.setHorizontalAlignment(SwingConstants.RIGHT);
        add(jlCpf);

        jtfCpf.setBounds(75, 125, 94, 20);
        add(jtfCpf);

        jlSaldo.setText("Saldo:");
        jlSaldo.setBounds(10, 150, 60, 18);
        jlSaldo.setHorizontalAlignment(SwingConstants.RIGHT);
        add(jlSaldo);

        jtfSaldo.setBounds(75, 150, 84, 20);
        add(jtfSaldo);

        jrbCorrente.setBounds(75, 180, 111, 20);
        jrbCorrente.setSelected(true);
        // Colocando nome no atalho
        jrbCorrente.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(contaCorrente, "contaC");
        jrbCorrente.getActionMap().put("contaC", contaC_Action);
        add(jrbCorrente);

        jrbPopupanca.setBounds(205, 180, 118, 20);
        // Colocando nome no atalho
        jrbPopupanca.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(contaPoupanca, "contaP");
        jrbPopupanca.getActionMap().put("contaP", contaP_Action);
        add(jrbPopupanca);

        bgContas.add(jrbCorrente);
        bgContas.add(jrbPopupanca);

        jSeparator02.setBounds(10, 210, 365, 10);
        add(jSeparator02);

        jbConsultar.setBounds(80, 225, 100, 23);
        // Ação do botão Consultar
        jbConsultar.addActionListener(_ -> {
            janela.mostrarVisualizacao();
        });
        jbConsultar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(consultar, "vizualizar");
        jbConsultar.getActionMap().put("vizualizar", consultar_Action);
        add(jbConsultar);

        jbCadastrar.setBounds(210, 225, 100, 23);
        // Ação do botão Cadastrar
        jbCadastrar.addActionListener(_ -> {
            String nome = jtfNome.getText();
            String endereco = jtfEndereco.getText();
            String telefone = jtfTelefone.getText();
            String cpf = jtfCpf.getText();
            String agencia = jtfAgencia.getText();
            String numero = jtfConta.getText();
            String saldo = jtfSaldo.getText();
            String tipoConta = jrbCorrente.isSelected() ? "Conta Corrente" : "Conta Poupança";

            Gravar gravar = new Gravar();
            boolean sucesso = gravar.cadastrarCliente(nome, endereco, telefone, cpf, agencia, numero, saldo, tipoConta);

            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");

                // Limpar os campos
                jtfNome.setText("");
                jtfEndereco.setText("");
                jtfTelefone.setValue(null);
                jtfCpf.setValue(null);
                jtfAgencia.setValue(null);
                jtfConta.setValue(null);
                jtfSaldo.setValue(null);
                jrbCorrente.setSelected(true);
            }
        });
        jbCadastrar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(cadastrar, "gravar");
        jbCadastrar.getActionMap().put("gravar", cadastrar_Action);
        add(jbCadastrar);

    }

    // Ação do atalho
    Action contaC_Action = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            jrbCorrente.setSelected(true);
        }
    };

    Action contaP_Action = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            jrbPopupanca.setSelected(true);
        }
    };

    Action cadastrar_Action = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            jbCadastrar.doClick();
        }
    };

    Action consultar_Action = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            jbConsultar.doClick();
        }
    };

}