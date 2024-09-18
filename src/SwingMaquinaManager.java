import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingMaquinaManager extends JFrame {
    private MaquinaPanel[] maquinas = new MaquinaPanel[4]; // 4 máquinas
    private JTextArea alertArea; // Área para exibir alertas

    public SwingMaquinaManager() {
        setTitle("Gerenciamento de Máquinas");
        setSize(600, 400);
        setLayout(new BorderLayout()); // Layout principal com BorderLayout

        JPanel maquinasPanel = new JPanel(new GridLayout(2, 2)); // Layout de grade para 4 máquinas

        // Criando os painéis de máquinas
        for (int i = 0; i < maquinas.length; i++) {
            maquinas[i] = new MaquinaPanel("Máquina " + (i + 1), this);
            maquinasPanel.add(maquinas[i]); // Adiciona cada painel ao JFrame
        }

        // Área de alertas (na parte inferior)
        alertArea = new JTextArea(5, 40);
        alertArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(alertArea);

        add(maquinasPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH); // Área de alertas na parte inferior

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Método para atualizar a área de alertas
    public void addAlert(String alertMessage) {
        alertArea.append(alertMessage + "\n");
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
    private SwingMaquinaManager manager; // Referência ao gerenciador para enviar alertas

    public MaquinaPanel(String nomeMaquina, SwingMaquinaManager manager) {
        this.maquina = new Maquina();
        this.manager = manager;

        // Adiciona um Gestor e um Operador para receber alertas
        Gestor gestor = new Gestor("Gestor " + nomeMaquina);
        Operador operador = new Operador("Operador " + nomeMaquina);

        maquina.addFuncionario(gestor);
        maquina.addFuncionario(operador);

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
                    maquina.setTemperatura(0);
                    maquina.setVelocidade(0);
                } else {
                    maquina.ligaMaquina();
                    ligarDesligarButton.setText("Desligar Máquina");
                }
                atualizarPainel();
            }
        });

        // Atualizações automáticas de temperatura e velocidade
        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (maquina.isLigada()) {
                    double temperatura = 30 + Math.random() * 70; // Temperatura entre 30 e 100
                    double velocidade = Math.random() * 2000;     // Velocidade entre 0 e 2000
                    maquina.atualizarDados(temperatura, velocidade);
                    atualizarPainel();

                    // Notifica caso haja falha e captura a mensagem
                    for (Funcionario f : maquina.getFuncionarios()) {
                        f.update(maquina.getLastAlertMessage());
                        if(maquina.getLastAlertMessage() != null){
                            manager.addAlert(f.nome + ": " + maquina.getLastAlertMessage());
                        }
                    }
                }
            }
        });
        timer.start();
    }

    // Atualiza os dados na interface
    private void atualizarPainel() {
        statusLabel.setText("Status: " + (maquina.isLigada() ? "Ligada" : "Desligada"));
        temperaturaLabel.setText("Temperatura: " + Math.round(maquina.getTemperatura()));
        velocidadeLabel.setText("Velocidade: " + Math.round(maquina.getVelocidade()));
    }
}