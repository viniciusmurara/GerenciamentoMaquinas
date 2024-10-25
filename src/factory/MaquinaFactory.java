package factory;

import observer.Maquina;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class MaquinaFactory {
    private static final Map<String, Supplier<Maquina>> maquinas = new HashMap<>();

    static {
        maquinas.put("caldeira", Caldeira::new);
        maquinas.put("refrigerador", Refrigerador::new);
    }

    public static Maquina createMaquina(String tipo) {
        Supplier<Maquina> maquina = maquinas.get(tipo.toLowerCase());
        if (maquina != null) {
            return maquina.get();
        }
        throw new IllegalArgumentException("Tipo de máquina desconhecido.");
    }
}

