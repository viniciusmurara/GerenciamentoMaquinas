public abstract class Funcionario implements Observer {
    protected String nome;

    public Funcionario(String nome) {
        this.nome = nome;
    }

    @Override
    public void update(String mensagem) {
        System.out.println(nome + ": " + mensagem);
    }

    public abstract void executarAcao();
}
