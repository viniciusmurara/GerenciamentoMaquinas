package observer;

public abstract class Funcionario implements Subscriber{
    protected String nome;

    public Funcionario(String nome) {
        this.nome = nome;
    }

    @Override
    public void update(String mensagem) {
        System.out.println(nome + ": " + mensagem);
    }

    @Override
    public void update(double temperatura, double parametroEspecifico, boolean status){
        // nao precisa fazer nada, esse serve so para o painel
    }

    public abstract void executarAcao();
}
