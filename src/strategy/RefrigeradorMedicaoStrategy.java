package strategy;

import observer.Maquina;

public class RefrigeradorMedicaoStrategy implements MedicaoStrategy {
    @Override
    public void medir(Maquina maquina) {
        double temperatura = -10 + Math.random() * 10;
        double umidade = Math.random() * 100; // percentual de umidade

        maquina.setTemperatura(temperatura);
        System.out.println("Umidade do refrigerador: " + umidade + "%");
    }
}