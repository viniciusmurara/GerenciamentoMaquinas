public interface Subscriber {
    String update(String mensagem);

    void update(double temperatura, double velocidade, boolean status);
}
