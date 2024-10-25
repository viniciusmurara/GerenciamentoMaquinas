package observer;

public class Operador extends Funcionario {

    public Operador(String nome) {
        super(nome);
    }

    @Override
    public void executarAcao() {
        System.out.println("Operador " + nome + " está resolvendo o problema.");
    }
}
