public class MaquinaResfriamentoFactory extends MaquinaFactory {
    @Override
    public Maquina criarMaquina() {
        return new MaquinaResfriamento();
    }
}