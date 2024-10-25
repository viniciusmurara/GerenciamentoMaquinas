package factory;

import observer.Maquina;
import strategy.MedicaoStrategy;
import strategy.RefrigeradorMedicaoStrategy;

public class Refrigerador extends Maquina {
    private MedicaoStrategy medicaoStrategy = new RefrigeradorMedicaoStrategy();

    @Override
    public void notifySubscribersPanel() {
        medicaoStrategy.medir(this);
        super.notifySubscribersPanel();
    }

    @Override
    public double getParametroEspecifico() {
        return Math.random() * 100; // Umidade do refrigerador em %
    }

    public void setMedicaoStrategy(MedicaoStrategy strategy) {
        this.medicaoStrategy = strategy;
    }

}
