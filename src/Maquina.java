import java.util.ArrayList;
import java.util.List;

public class Maquina {
    private List<Subscriber> subscribers = new ArrayList<>();
    private List<Funcionario> funcionarios = new ArrayList<>();

    private double temperatura;
    private double velocidade;
    private boolean status = false;
    private String lastAlertMessage;

    public void addFuncionario(Funcionario funcionario) {
        funcionarios.add(funcionario);
        add(funcionario);
    }

    public void add(Subscriber s) {
        subscribers.add(s);
    }

    public void notifySubscribersEmployees() {
        if (status) {
            if (temperatura >= 95) {
                lastAlertMessage = "Alerta! Falha detectada na máquina! Temperatura alta.";
            } else if (temperatura <= 35){
                lastAlertMessage = "Alerta! Falha detectada na máquina! Temperatura baixa.";
            } else if (velocidade == 0){
                lastAlertMessage = "Alerta! Falha detectada na máquina! Parada forçada.";
            } else if (velocidade <= 400) {
                lastAlertMessage = "Alerta! Falha detectada na máquina! Lentidão anormal.";
            } else {
                lastAlertMessage = null;
            }
        } else {
            lastAlertMessage = "Máquina desligada";
        }
        for (Subscriber s : subscribers) {
            s.update(lastAlertMessage);
        }
    }

    public void notifySubscribersPanel() {
        temperatura = 30 + Math.random() * 70;
        velocidade = Math.random() * 2000;
        notifySubscribersEmployees();
        for (Subscriber s : subscribers) {
            s.update(temperatura, velocidade, status);
        }
    }


    // ==== GETTERS E SETTERS ====
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
}