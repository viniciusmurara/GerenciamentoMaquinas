import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Classe principal que gerencia a interface gráfica para o gerenciamento de máquinas
public class PainelControle extends JFrame {
    private PainelMaquina[] maquinas = new PainelMaquina[4]; // Array para armazenar a quantidade de máquinas
    private JTextArea alertArea; // Área de texto para exibir mensagens de alerta

    // Construtor da classe PainelControle
    public PainelControle() {
        setTitle("Gerenciamento de Máquinas"); // Define o título da janela
        setSize(600, 400); // Define o tamanho da janela
        setLayout(new BorderLayout()); // Configura o layout principal da janela como BorderLayout

        JPanel maquinasPanel = new JPanel(new GridLayout(2, 2)); // Cria um painel para exibir as máquinas em um GridLayout 2x2

        // Loop para criar e adicionar os painéis das máquinas
        for (int i = 0; i < maquinas.length; i++) {
            maquinas[i] = new PainelMaquina("Máquina " + (i + 1), this); // Cria um painel para cada máquina
            maquinasPanel.add(maquinas[i]); // Adiciona o painel da máquina ao painel principal
        }

        // Cria a área de alertas na parte inferior da janela
        alertArea = new JTextArea(5, 40); // Cria uma área de texto com 5 linhas e 40 colunas
        alertArea.setEditable(false); // Define que a área de texto não pode ser editada
        JScrollPane scrollPane = new JScrollPane(alertArea); // Adiciona uma barra de rolagem à área de texto

        // Adiciona os componentes ao JFrame
        add(maquinasPanel, BorderLayout.CENTER); // Adiciona o painel das máquinas ao centro da janela
        add(scrollPane, BorderLayout.SOUTH); // Adiciona a área de alertas na parte inferior

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Define a operação padrão ao fechar a janela
        setVisible(true); // Torna a janela visível
    }

    // Método para adicionar uma mensagem de alerta à área de alertas
    public void addAlert(String alertMessage) {
        alertArea.append(alertMessage + "\n"); // Adiciona a mensagem de alerta com uma nova linha
    }

    // Método main que inicializa a aplicação gráfica
    public static void main(String[] args) {
        SwingUtilities.invokeLater(PainelControle::new);
    }
}

// Classe que representa o painel de controle de uma máquina
class PainelMaquina extends JPanel {
    private JLabel statusLabel; // Rótulo que exibe o status da máquina
    private JLabel temperaturaLabel; // Rótulo que exibe a temperatura da máquina
    private JLabel velocidadeLabel; // Rótulo que exibe a velocidade da máquina
    private JButton ligarDesligarButton; // Botão para ligar ou desligar a máquina
    private Maquina maquina; // Objeto que representa a máquina
    private PainelControle painelControle; // Referência ao gerenciador principal para enviar alertas

    // Construtor da classe PainelMaquina
    public PainelMaquina(String nomeMaquina, PainelControle painelControle) {
        this.maquina = new Maquina(); // Cria um novo objeto Maquina
        this.painelControle = painelControle; // Armazena a referência ao gerenciador principal

        // Adiciona um Gestor e um Operador para receber alertas
        Gestor gestor = new Gestor("Gestor " + nomeMaquina); // Cria um novo gestor
        Operador operador = new Operador("Operador " + nomeMaquina); // Cria um novo operador

        maquina.addFuncionario(gestor); // Adiciona o gestor à máquina
        maquina.addFuncionario(operador); // Adiciona o operador à máquina

        // Configura o layout do painel
        setLayout(new GridLayout(4, 1)); // Usa um GridLayout com 4 linhas e 1 coluna

        // Cria os componentes para exibir informações da máquina
        statusLabel = new JLabel("Status: Desligado"); // Rótulo de status inicial
        temperaturaLabel = new JLabel("Temperatura: 0"); // Rótulo de temperatura inicial
        velocidadeLabel = new JLabel("Velocidade: 0"); // Rótulo de velocidade inicial
        ligarDesligarButton = new JButton("Ligar Máquina"); // Botão inicial para ligar a máquina

        // Adiciona os componentes ao painel
        add(new JLabel(nomeMaquina)); // Rótulo com o nome da máquina
        add(statusLabel); // Rótulo de status
        add(temperaturaLabel); // Rótulo de temperatura
        add(velocidadeLabel); // Rótulo de velocidade
        add(ligarDesligarButton); // Botão de ligar/desligar

        // Adiciona ação ao botão de ligar/desligar
        ligarDesligarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (maquina.isLigada()) { // Verifica se a máquina está ligada
                    maquina.desligaMaquina(); // Desliga a máquina
                    ligarDesligarButton.setText("Ligar Máquina"); // Atualiza o texto do botão
                    maquina.setTemperatura(0); // Reseta a temperatura
                    maquina.setVelocidade(0); // Reseta a velocidade
                } else {
                    maquina.ligaMaquina(); // Liga a máquina
                    ligarDesligarButton.setText("Desligar Máquina"); // Atualiza o texto do botão
                }
                atualizarPainel(); // Atualiza os rótulos com os novos valores
            }
        });

        // Atualizações automáticas de temperatura e velocidade a cada 2 segundos
        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (maquina.isLigada()) { // Se a máquina estiver ligada
                    maquina.atualizarDadosAleatorios(); // Atualiza os dados aleatórios
                    atualizarPainel(); // Atualiza os rótulos na interface

                    // Notifica caso haja falha e captura a mensagem de alerta
                    for (Funcionario f : maquina.getFuncionarios()) {
                        f.update(maquina.getLastAlertMessage());
                        if (maquina.getLastAlertMessage() != null) {
                            painelControle.addAlert(f.nome + ": " + maquina.getLastAlertMessage());
                        }
                    }
                }
            }

        });
        timer.start(); // Inicia o timer para atualizações periódicas
    }

    // Método que atualiza os dados na interface
    private void atualizarPainel() {
        // Atualiza os rótulos com o status, temperatura e velocidade atuais
        statusLabel.setText("Status: " + (maquina.isLigada() ? "Ligada" : "Desligada"));
        temperaturaLabel.setText("Temperatura: " + Math.round(maquina.getTemperatura()));
        velocidadeLabel.setText("Velocidade: " + Math.round(maquina.getVelocidade()));
    }

}
