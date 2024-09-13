public class Gestor extends Funcionario {

    public Gestor(String nome) {
        super(nome);
    }

    @Override
    public void executarAcao() {
        System.out.println("Gestor " + nome + " está analisando o problema.");
    }
}
