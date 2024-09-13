import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingMaquinaManager extends JFrame {
    private MaquinaPanel[] maquinas = new MaquinaPanel[4]; // 4 máquinas

    public SwingMaquinaManager() {
        setTitle("Gerenciamento de Máquinas");
        setSize(600, 400);
        setLayout(new GridLayout(2, 2)); // Layout de grade para 4 máquinas

        // Criando os painéis de máquinas
        for (int i = 0; i < maquinas.length; i++) {
            maquinas[i] = new MaquinaPanel("Máquina " + (i + 1));
            add(maquinas[i]); // Adiciona cada painel ao JFrame
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SwingMaquinaManager::new);
    }
}

class MaquinaPanel extends JPanel {
    private JLabel statusLabel;
    private JLabel temperaturaLabel;
    private JLabel velocidadeLabel;
    private JButton ligarDesligarButton;
    private Maquina maquina;

    public MaquinaPanel(String nomeMaquina) {
        this.maquina = new Maquina();

        // Configura o layout do painel
        setLayout(new GridLayout(4, 1));

        // Cria os componentes
        statusLabel = new JLabel("Status: Desligado");
        temperaturaLabel = new JLabel("Temperatura: 0");
        velocidadeLabel = new JLabel("Velocidade: 0");
        ligarDesligarButton = new JButton("Ligar Máquina");

        // Adiciona os componentes ao painel
        add(new JLabel(nomeMaquina));
        add(statusLabel);
        add(temperaturaLabel);
        add(velocidadeLabel);
        add(ligarDesligarButton);

        // Adiciona ação ao botão de ligar/desligar
        ligarDesligarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (maquina.isLigada()) {
                    maquina.desligaMaquina();
                    ligarDesligarButton.setText("Ligar Máquina");
                } else {
                    maquina.ligaMaquina();
                    ligarDesligarButton.setText("Desligar Máquina");
                }
                atualizarPainel();
            }
        });

        // Atualizações automáticas de temperatura e velocidade
        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (maquina.isLigada()) {
                    double temperatura = 30 + Math.random() * 40; // Temperatura entre 30 e 100
                    double velocidade = Math.random() * 2000;     // Velocidade entre 0 e 2000
                    maquina.atualizarDados(temperatura, velocidade);
                    atualizarPainel();
                }
            }
        });
        timer.start();
    }

    // Atualiza os dados na interface
    private void atualizarPainel() {
        statusLabel.setText("Status: " + (maquina.isLigada() ? "Ligada" : "Desligada"));
        temperaturaLabel.setText("Temperatura: " + maquina.getTemperatura());
        velocidadeLabel.setText("Velocidade: " + maquina.getVelocidade());
    }
}