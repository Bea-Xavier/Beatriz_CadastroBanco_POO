package telas;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.CardLayout;

public class Janela extends JFrame {
    private CardLayout cardLayout;
    private Formulario formulario;
    private Visualizacao visualizacao;

    // Construtor da class Janela
    public Janela() {
        setTitle("Java Swing - Desenvolvimento de Sistemas");
        setSize(400, 305);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        centralizar();
        setResizable(false);

        cardLayout = new CardLayout();
        getContentPane().setLayout(cardLayout);

        formulario = new Formulario(this);
        visualizacao = new Visualizacao(this);

        getContentPane().add(formulario, "formulario");
        getContentPane().add(visualizacao, "visualizacao");

        cardLayout.show(getContentPane(), "formulario");

    }

    public void mostrarFormulario() {
        cardLayout.show(getContentPane(), "formulario");
    }

    public void mostrarVisualizacao() {
        cardLayout.show(getContentPane(), "visualizacao");
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
}
