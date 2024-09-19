public abstract class Funcionario implements Subscriber {
    protected String nome;

    public Funcionario(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public void update(String mensagem) {
        System.out.println(nome + ": " + mensagem);
    }

    public abstract void executarAcao();
}
