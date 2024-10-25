package factory;

import observer.Maquina;
import strategy.CaldeiraMedicaoStrategy;
import strategy.MedicaoStrategy;

public class Caldeira extends Maquina {
    private MedicaoStrategy medicaoStrategy = new CaldeiraMedicaoStrategy();

    @Override
    public void notifySubscribersPanel() {
        medicaoStrategy.medir(this);
        super.notifySubscribersPanel();
    }

    @Override
    public double getParametroEspecifico() {
        return Math.random() * 100; // Capacidade da caldeira em %
    }

    public void setMedicaoStrategy(MedicaoStrategy strategy) {
        this.medicaoStrategy = strategy;
    }

}
