public class MaquinaResfriamento extends Maquina {

    public MaquinaResfriamento() {
        super();
        // Você pode inicializar variáveis específicas dessa máquina aqui, se necessário
    }

    // Sobrescreva ou adicione métodos que sejam específicos para a MaquinaResfriamento
    @Override
    public void notifySubscribersEmployees() {
        if (status) {
            if (temperatura >= 10) {
                lastAlertMessage = "Alerta! Resfriamento falhando, temperatura alta.";
            } else if (temperatura <= 0) {
                lastAlertMessage = "Alerta! Resfriamento excessivo, temperatura muito baixa.";
            } else if (velocidade == 0) {
                lastAlertMessage = "Alerta! Máquina de resfriamento parada.";
            } else {
                lastAlertMessage = null;
            }
        } else {
            lastAlertMessage = "Máquina de resfriamento desligada";
        }

        // Notifica os funcionários e outros assinantes com o alerta específico
        for (Subscriber s : subscribers) {
            s.update(lastAlertMessage);
        }
    }

    @Override
    public void notifySubscribersPanel() {
        // Simula o comportamento específico da máquina de resfriamento
        temperatura = -10 + Math.random() * 20; // Gera uma temperatura baixa para simular o resfriamento
        velocidade = Math.random() * 1500; // A velocidade também pode variar

        // Notifica os funcionários
        notifySubscribersEmployees();

        // Notifica o painel de controle
        for (Subscriber s : subscribers) {
            s.update(temperatura, velocidade, status);
        }
    }
}