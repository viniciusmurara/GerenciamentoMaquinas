import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Criação da Máquina
        Maquina maquina = new Maquina();

        // Criação de Painéis de Controle
        PainelControle painel1 = new PainelControle();

        // Adiciona Painéis à Máquina
        maquina.addPainelControle(painel1);

        // Criação de Funcionários (Gestores e Operadores)
        Gestor gestor1 = new Gestor("Gestor João");
        Operador operador1 = new Operador("Operador Maria");

        // Adiciona Funcionários à Máquina
        maquina.addFuncionario(gestor1);
        maquina.addFuncionario(operador1);

        // Simulação automática de atualizações de dados
        while(true){
            int escolha;
            System.out.println("\nAção referente a máquina\n[1]Ligar\n[2]Desligar");
            escolha = sc.nextInt();

            if (escolha == 1){
                maquina.ligaMaquina();
                // Gera valores aleatórios para temperatura, velocidade e acuracidade
                double temperatura = 30 + Math.random() * 40; // Temperatura entre 30 e 100
                double velocidade = Math.random() * 2000;     // Velocidade entre 0 e 2000
                // Atualiza os dados da máquina
                maquina.atualizarDados(temperatura, velocidade);
            } else if (escolha == 2){
                maquina.desligaMaquina();
                double temperatura = 5 + Math.random() * 10; // Temperatura entre 30 e 1000
                double velocidade = 0;     // Velocidade entre 0 e 2000
                // Atualiza os dados da máquina
                maquina.atualizarDados(temperatura, velocidade);
            }

            // Simula um atraso para atualizações em tempo real (3 segundos entre atualizações)
            try {
                Thread.sleep(3000); // 3000 milissegundos = 3 segundos
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}