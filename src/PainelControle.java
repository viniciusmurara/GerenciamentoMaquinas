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

    public PainelMaquina(String nomeMaquina, PainelControle painelControle) {
        this.maquina = new Maquina();
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


public class PainelControle extends JFrame{
    private PainelMaquina[] maquinas = new PainelMaquina[4];
    private JTextArea alertArea;

    // Construtor da classe PainelControle
    public PainelControle() {
        setTitle("Gerenciamento de Máquinas");
        setSize(600, 400);
        setLayout(new BorderLayout());

        JPanel maquinasPanel = new JPanel(new GridLayout(2, 2));

        for (int i = 0; i < maquinas.length; i++) {
            maquinas[i] = new PainelMaquina("Máquina " + (i + 1), this);
            maquinasPanel.add(maquinas[i]);
        }

        alertArea = new JTextArea(5, 40);
        alertArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(alertArea);

        add(maquinasPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void addAlert(String alertMessage) {
        alertArea.append(alertMessage + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PainelControle::new);
    }
}