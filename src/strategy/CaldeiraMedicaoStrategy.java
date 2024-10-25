package strategy;

import observer.Maquina;

public class CaldeiraMedicaoStrategy implements MedicaoStrategy {
    @Override
    public void medir(Maquina maquina) {
        double temperatura = 30 + Math.random() * 70;
        double capacidade = Math.random() * 100; // percentual de capacidade

        maquina.setTemperatura(temperatura);
        System.out.println("Capacidade da caldeira: " + capacidade + "%, Temperatura: " + temperatura);
    }
}