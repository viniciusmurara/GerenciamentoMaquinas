package observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Maquina {
    private List<Subscriber> subscribers = new ArrayList<>();
    private List<Funcionario> funcionarios = new ArrayList<>();

    private double temperatura;
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
        double limiteTemperatura1 = 85.0;
        double limiteTemperatura2 = 15.0;

        if (temperatura > limiteTemperatura1) {
            lastAlertMessage = "Alerta! Temperatura alta: " + temperatura + " graus!";
        } else if(temperatura < limiteTemperatura2){
            lastAlertMessage = "Alerta! Temperatura baixa: " + temperatura + " graus!";
        } else {
            lastAlertMessage = "";
        }

        for (Subscriber s : subscribers) {
            s.update(lastAlertMessage);
        }
    }

    public void notifySubscribersPanel() {
        notifySubscribersEmployees();
        double parametroEspecifico = getParametroEspecifico();
        for (Subscriber s : subscribers) {
            s.update(temperatura, parametroEspecifico, status);
        }

        // Para depuração
        System.out.println("Notificando subscritores: Temperatura = " + temperatura + ", Parametro Especifico = " + parametroEspecifico);
    }

    public abstract double getParametroEspecifico();

    // ==== GETTERS E SETTERS ====
    public void ligaMaquina() {
        status = true;
    }

    public void desligaMaquina() {
        status = false;
    }

    public boolean isLigada() {
        return status;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public String getLastAlertMessage() {
        return lastAlertMessage;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }
}
