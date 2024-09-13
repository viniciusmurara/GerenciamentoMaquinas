import java.util.ArrayList;
import java.util.List;

public class Maquina implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private List<PainelControle> paineisControle = new ArrayList<>();
    private List<Funcionario> funcionarios = new ArrayList<>();

    private double temperatura;
    private double velocidade;
    private boolean status = false;

    public void addPainelControle(PainelControle painel) {
        paineisControle.add(painel);
    }

    public void addFuncionario(Funcionario funcionario) {
        funcionarios.add(funcionario);
        attach(funcionario);
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

    public double getTemperatura() {
        return temperatura;
    }

    public double getVelocidade() {
        return velocidade;
    }

    @Override
    public void notifySubscribers() {
        if (status){
            for (Observer o : observers) {
                if (temperatura > 85) {
                    o.update("Alerta! Falha detectada na máquina! Temperatura alta.");
                } else if (temperatura < 45){
                    o.update("Alerta! Falha detectada na máquina! Temperatura baixa.");
                } else if (velocidade == 0){
                    o.update("Alerta! Falha detectada na máquina! Parada forçada.");
                } else if (velocidade < 350) {
                    o.update("Alerta! Falha detectada na máquina! Lentidão anormal.");
                } else {
                    o.update("Nenhuma atualização enviada, tudo OK!");
                }
            }
        } else {
            for (Observer o : observers) {
                o.update("Máquina desligada");
            }
        }
    }

    @Override
    public void alertSubscribers(String mensagem) {
        for (Observer o : observers) {
            o.update(mensagem);
        }
    }

    @Override
    public void attach(Observer o) {
        observers.add(o);
    }

    @Override
    public void detach(Observer o) {
        observers.remove(o);
    }

    // Atualiza os dados da máquina e notifica painéis de controle
    public void atualizarDados(double temperatura, double velocidade) {
        this.temperatura = temperatura;
        this.velocidade = velocidade;

        for (PainelControle painel : paineisControle) {
            painel.updateDados(temperatura, velocidade);
        }
        notifySubscribers();
    }
}
