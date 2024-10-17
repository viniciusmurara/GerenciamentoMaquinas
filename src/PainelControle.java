import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class PainelMaquina extends JPanel implements Subscriber{
    private JLabel statusLabel;
    private JLabel temperaturaLabel;
    private JLabel velocidadeLabel;
    private JButton ligarDesligarButton;
    private Maquina maquina;
    private PainelControle painelControle;

    public PainelMaquina(String nomeMaquina, PainelControle painelControle, MaquinaFactory factory) {
        this.maquina = factory.criarMaquina(); // Usando a fábrica para criar a máquina
        this.painelControle = painelControle;

        Gestor gestor = new Gestor("Gestor " + nomeMaquina);
        Operador operador = new Operador("Operador " + nomeMaquina);

        maquina.addFuncionario(gestor);
        maquina.addFuncionario(operador);

        setLayout(new GridLayout(4, 1));

        statusLabel = new JLabel("Status: Desligado");
        temperaturaLabel = new JLabel("Temperatura: 0");
        velocidadeLabel = new JLabel("Velocidade: 0");
        ligarDesligarButton = new JButton("Ligar Máquina");

        add(new JLabel(nomeMaquina));
        add(statusLabel);
        add(temperaturaLabel);
        add(velocidadeLabel);
        add(ligarDesligarButton);

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
                update(maquina.getTemperatura(), maquina.getVelocidade(), maquina.isLigada());
            }
        });

        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (maquina.isLigada()) {
                    maquina.notifySubscribersPanel();
                    update(maquina.getTemperatura(), maquina.getVelocidade(), maquina.isLigada());

                    for (Funcionario f : maquina.getFuncionarios()) {
                        f.update(maquina.getLastAlertMessage());
                        if (maquina.getLastAlertMessage() != null) {
                            painelControle.addAlert(f.update(maquina.getLastAlertMessage()));
                        }
                    }
                }
            }
        });
        timer.start();
    }


    @Override
    public String update(String mensagem) {
        return mensagem;
    }

    @Override
    public void update(double temperatura, double velocidade, boolean status) {
        statusLabel.setText("Status: " + (status ? "Ligada" : "Desligada"));
        temperaturaLabel.setText("Temperatura: " + Math.round(temperatura));
        velocidadeLabel.setText("Velocidade: " + Math.round(velocidade));
    }
}


public class PainelControle extends JFrame {
    private JPanel maquinasPanel;
    private JTextArea alertArea;

    public PainelControle() {
        setTitle("Gerenciamento de Máquinas");
        setSize(600, 400);
        setLayout(new BorderLayout());

        // Painel que vai conter as máquinas
        maquinasPanel = new JPanel(new GridLayout(0, 2)); // GridLayout flexível

        // Text area para exibir alertas
        alertArea = new JTextArea(5, 40);
        alertArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(alertArea);

        // Botão para adicionar nova máquina
        JButton adicionarMaquinaButton = new JButton("Adicionar Máquina");
        adicionarMaquinaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarNovaMaquina();
            }
        });

        // Adiciona os componentes à janela
        add(maquinasPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
        add(adicionarMaquinaButton, BorderLayout.NORTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Método para adicionar uma nova máquina ao painel
    public void adicionarNovaMaquina() {
        // Janela de diálogo para escolher o tipo de máquina
        String[] opcoes = {"Caldeira", "Resfriamento"};
        String escolha = (String) JOptionPane.showInputDialog(
                this,
                "Escolha o tipo de máquina:",
                "Adicionar Máquina",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
        );

        if (escolha != null) {
            MaquinaFactory factory;
            if (escolha.equals("Caldeira")) {
                factory = new MaquinaCaldeiraFactory();
            } else {
                factory = new MaquinaResfriamentoFactory();
            }

            // Cria um novo painel de máquina e adiciona ao layout
            PainelMaquina novaMaquina = new PainelMaquina("Máquina " + (maquinasPanel.getComponentCount() + 1), this, factory);
            maquinasPanel.add(novaMaquina);
            maquinasPanel.revalidate(); // Atualiza o layout
            maquinasPanel.repaint();
        }
    }

    // Método para adicionar alertas à área de texto
    public void addAlert(String alertMessage) {
        alertArea.append(alertMessage + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PainelControle::new);
    }
}
