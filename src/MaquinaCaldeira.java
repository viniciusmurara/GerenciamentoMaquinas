public class MaquinaCaldeira extends Maquina {

    public MaquinaCaldeira() {
        super();
        // Inicializações específicas da caldeira, se necessário
    }

    // Sobrescreve métodos com lógica específica para a caldeira
    @Override
    public void notifySubscribersEmployees() {
        if (status) {
            if (temperatura >= 100) {
                lastAlertMessage = "Alerta! Temperatura crítica na caldeira! Superaquecimento.";
            } else if (temperatura <= 50) {
                lastAlertMessage = "Alerta! Caldeira operando abaixo da temperatura mínima.";
            } else if (velocidade == 0) {
                lastAlertMessage = "Alerta! Caldeira paralisada.";
            } else {
                lastAlertMessage = null; // Nenhum alerta se tudo estiver normal
            }
        } else {
            lastAlertMessage = "Caldeira desligada";
        }

        // Notifica todos os assinantes (funcionários e painel)
        for (Subscriber s : subscribers) {
            s.update(lastAlertMessage);
        }
    }

    @Override
    public void notifySubscribersPanel() {
        // Simulação específica da caldeira
        temperatura = 50 + Math.random() * 100; // A temperatura da caldeira deve variar entre 50 e 150 graus
        velocidade = Math.random() * 1000; // A velocidade pode variar menos do que outras máquinas, pois é uma caldeira

        // Notifica os funcionários com base nos alertas
        notifySubscribersEmployees();

        // Atualiza o painel com os dados de temperatura e velocidade
        for (Subscriber s : subscribers) {
            s.update(temperatura, velocidade, status);
        }
    }
}
