package observer;

public interface Subscriber {
    void update(double temperatura, double parametroEspecifico, boolean status);

    void update(String mensagem);
}
