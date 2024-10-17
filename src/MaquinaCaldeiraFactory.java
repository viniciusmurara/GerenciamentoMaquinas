public class MaquinaCaldeiraFactory extends MaquinaFactory {
    @Override
    public Maquina criarMaquina() {
        return new MaquinaCaldeira();
    }
}