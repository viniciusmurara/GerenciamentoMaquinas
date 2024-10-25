package painel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import factory.Caldeira;
import factory.Refrigerador;
import observer.Maquina;

public class PainelControle extends JFrame {
    private DefaultListModel<String> listModel;
    private JPanel maquinasPanel;

    public PainelControle() {
        setTitle("Painel de Controle de Máquinas");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        listModel = new DefaultListModel<>();
        maquinasPanel = new JPanel();
        maquinasPanel.setLayout(new BoxLayout(maquinasPanel, BoxLayout.Y_AXIS));

        JButton btnAddMaquina = new JButton("Adicionar Máquina");
        btnAddMaquina.addActionListener(e -> adicionarMaquina());

        JScrollPane scrollPane = new JScrollPane(maquinasPanel);
        add(btnAddMaquina, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void adicionarMaquina() {
        String[] opcoes = {"Caldeira", "Refrigerador"};
        String tipo = (String) JOptionPane.showInputDialog(this, "Escolha o tipo de máquina:",
                "Adicionar Máquina", JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

        if (tipo != null) {
            Maquina maquina = null;

            if (tipo.equals("Caldeira")) {
                maquina = new Caldeira();
            } else if (tipo.equals("Refrigerador")) {
                maquina = new Refrigerador();
            }

            if (maquina != null) {
                adicionarMaquinaPanel(maquina);
            }
        }
    }

    private void adicionarMaquinaPanel(Maquina maquina) {
        JPanel panelMaquina = new JPanel();
        panelMaquina.setLayout(new BoxLayout(panelMaquina, BoxLayout.Y_AXIS));
        JLabel lblMaquina = new JLabel("Máquina: " + maquina.getClass().getSimpleName());
        JLabel lblTemperatura = new JLabel("Temperatura: 0°C");
        JLabel lblParametro = new JLabel("Parâmetro Específico: 0");
        JButton btnLigar = new JButton("Ligar");
        JButton btnDesligar = new JButton("Desligar");

        panelMaquina.add(lblMaquina);
        panelMaquina.add(lblTemperatura);
        panelMaquina.add(lblParametro);
        panelMaquina.add(btnLigar);
        panelMaquina.add(btnDesligar);

        maquinasPanel.add(panelMaquina);
        maquinasPanel.revalidate();
        maquinasPanel.repaint();

        // Cria um Timer que atualiza a temperatura e o parâmetro específico a cada 3 segundos
        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (maquina.isLigada()) {
                    // Gera novos valores para temperatura e parâmetro específico
                    maquina.setTemperatura(30 + Math.random() * 70); // Exemplo para caldeira
                    double parametroEspecifico = maquina.getParametroEspecifico();

                    // Atualiza os labels no painel
                    lblTemperatura.setText("Temperatura: " + String.format("%.2f", maquina.getTemperatura()) + "°C");
                    lblParametro.setText("Parâmetro Específico: " + String.format("%.2f", parametroEspecifico));
                } else {
                    lblTemperatura.setText("Temperatura: 0°C");
                    lblParametro.setText("Parâmetro Específico: 0");
                }
            }
        });

        // Quando clicar em Ligar, o Timer inicia
        btnLigar.addActionListener(e -> {
            maquina.ligaMaquina();
            timer.start(); // Inicia o Timer
        });

        // Quando clicar em Desligar, o Timer é parado e os valores são zerados
        btnDesligar.addActionListener(e -> {
            maquina.desligaMaquina();
            timer.stop(); // Para o Timer
            lblTemperatura.setText("Temperatura: 0°C");
            lblParametro.setText("Parâmetro Específico: 0");
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PainelControle painel = new PainelControle();
            painel.setVisible(true);
        });
    }
}
