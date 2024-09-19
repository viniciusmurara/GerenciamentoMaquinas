import java.util.ArrayList;
import java.util.List;

public class Maquina implements Subscriber {
    private List<Observer> observers = new ArrayList<>();
    private List<Funcionario> funcionarios = new ArrayList<>();

    private double temperatura;
    private double velocidade;
    private boolean status = false;
    private String lastAlertMessage; // Armazena a última mensagem de alerta

    public void addFuncionario(Funcionario funcionario) {
        funcionarios.add(funcionario);
        add(funcionario);
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void ligaMaquina(){
        status = true;
    }

    public void desligaMaquina(){
        status = false;
    }

    public boolean isLigada() {
        return status;
    }

    // Retorna a última mensagem de alerta gerada
    public String getLastAlertMessage() {
        return lastAlertMessage;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public double getVelocidade() {
        return velocidade;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public void setVelocidade(double velocidade) {
        this.velocidade = velocidade;
    }

    @Override
    public void notifySubscribers() {
        if (status) {
            if (temperatura > 90) {
                lastAlertMessage = "Alerta! Falha detectada na máquina! Temperatura alta.";
            } else if (temperatura < 40){
                lastAlertMessage = "Alerta! Falha detectada na máquina! Temperatura baixa.";
            } else if (velocidade == 0){
                lastAlertMessage = "Alerta! Falha detectada na máquina! Parada forçada.";
            } else if (velocidade < 300) {
                lastAlertMessage = "Alerta! Falha detectada na máquina! Lentidão anormal.";
            } else {
                lastAlertMessage = null;
            }
        } else {
            lastAlertMessage = "Máquina desligada";
        }
        for (Observer o : observers) {
            o.update(lastAlertMessage);
        }
    }

    @Override
    public void add(Observer o) {
        observers.add(o);
    }

    @Override
    public void remove(Observer o) {
        observers.remove(o);
    }

    // Atualiza os dados da máquina e notifica painéis de controle
    public void atualizarDadosAleatorios() {
        this.temperatura = 30 + Math.random() * 70; // Temperatura entre 30 e 100
        this.velocidade = Math.random() * 2000; // Velocidade entre 0 e 2000
        notifySubscribers(); // Notifica observadores sobre a atualização
    }

}