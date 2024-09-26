public abstract class Funcionario implements Subscriber {
    protected String nome;

    public Funcionario(String nome) {
        this.nome = nome;
    }

    @Override
    public String update(String mensagem) {
        return nome + ": " + mensagem;
    }

    @Override
    public void update(double temperatura, double velocidade, boolean status) {}

    public abstract void executarAcao();
}
