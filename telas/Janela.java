package telas;

import javax.swing.*;
import java.awt.*;

public class Janela extends JFrame {

    //Atributos
    JLabel jlAgencia = new JLabel();
    JTextField jtfAgencia = new JTextField();
    JLabel jlConta = new JLabel();
    JTextField jtfConta = new JTextField();
    JSeparator jSeparator01 = new JSeparator();
    JLabel jlNome = new JLabel();
    JTextField jtfNome = new JTextField();
    JLabel jlEndereco = new JLabel();
    JTextField jtfEndereco = new JTextField();
    JLabel jlTelefone = new JLabel();
    JTextField jtfTelefone = new JTextField();
    JLabel jlCpf = new JLabel();
    JTextField jtfCpf = new JTextField();
    JRadioButton jrbCorrente = new JRadioButton("Conta Corrente");
    JRadioButton jrbPopupanca = new JRadioButton("Conta Poupança");
    ButtonGroup bgContas = new ButtonGroup(); 
    JSeparator jSeparator02 = new JSeparator();
    JButton jbConsultar = new JButton("Consultar");
    JButton jbAtualizar = new JButton("Atualizar");
    JButton jbFechar = new JButton("Fechar");


    //Construtor
    public Janela() {
        setTitle("Java Swing - Desenvolvimento de Sistemas");
        setSize(400, 275);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        centralizar();

        setResizable(false);
        getContentPane().setLayout(null);

        //Configuração dos componentes da Janela
        jlAgencia.setText("Código da Agência");
        jlAgencia.setBounds(10,10,110, 18 );
        getContentPane().add(jlAgencia);

        jtfAgencia.setBounds(125, 10, 50, 20);
        getContentPane().add(jtfAgencia);

        jlConta.setText("Número da Conta");
        jlConta.setBounds(205, 10, 105, 18);
        getContentPane().add(jlConta);

        jtfConta.setBounds(315, 10, 60, 20);
        getContentPane().add(jtfConta);

        jSeparator01.setBounds(10, 40, 365, 10);
        getContentPane().add(jSeparator01);

        jlNome.setText("Nome:");
        jlNome.setBounds(10, 50, 60, 18);
        jlNome.setHorizontalAlignment(SwingConstants.RIGHT);
        getContentPane().add(jlNome);
        
        jtfNome.setBounds(75, 50, 300, 20);
        getContentPane().add(jtfNome);

        jlEndereco.setText("Endereço:");
        jlEndereco.setBounds(10, 75, 60, 18);
        jlEndereco.setHorizontalAlignment(SwingConstants.RIGHT);
        getContentPane().add(jlEndereco);

        jtfEndereco.setBounds(75, 75, 300, 20);
        getContentPane().add(jtfEndereco);

        jlTelefone.setText("Telefone:");
        jlTelefone.setBounds(10, 100, 60, 18);
        jlTelefone.setHorizontalAlignment(SwingConstants.RIGHT);
        getContentPane().add(jlTelefone);

        jtfTelefone.setBounds(75, 100, 300, 20);
        getContentPane().add(jtfTelefone);

        jlCpf.setText("Telefone:");
        jlCpf.setBounds(10, 125, 60, 18);
        jlCpf.setHorizontalAlignment(SwingConstants.RIGHT);
        getContentPane().add(jlCpf);

        jtfCpf.setBounds(75, 125, 300, 20);
        getContentPane().add(jtfCpf);

        jrbCorrente.setBounds(100, 150, 111, 20);
        jrbCorrente.setSelected(true);
        getContentPane().add(jrbCorrente);

        //Fazer os atalhos ("Ex: Alt + C")

        jrbPopupanca.setBounds(225, 150, 118, 20);
        getContentPane().add(jrbPopupanca);

        bgContas.add(jrbCorrente);
        bgContas.add(jrbPopupanca);

        jSeparator02.setBounds(10, 180, 365, 10);
        getContentPane().add(jSeparator02);

        jbConsultar.setBounds(35, 190, 100, 23);
        getContentPane().add(jbConsultar);

        jbAtualizar.setBounds(145, 190, 100, 23);
        jbAtualizar.setEnabled(false);
        getContentPane().add(jbAtualizar);

        jbFechar.setBounds(225, 190, 100, 23);
        getContentPane().add(jbFechar);
        

    }

    // Método da class Janela
    private void centralizar() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension janela = getSize();
        if (janela.height > screen.height)
            setSize(janela.width, screen.height);
        if (janela.height > screen.width)
            setSize(screen.width, janela.height);
        setLocation((screen.width - janela.width) / 2,
                (screen.height - janela.height) / 2);
    }

    public static void main(String args[]) {
        Janela janela = new Janela();
        janela.setVisible(true);
    }
}
