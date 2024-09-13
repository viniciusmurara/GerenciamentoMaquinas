public interface Subject {
    void notifySubscribers();
    void alertSubscribers(String mensagem);
    void attach(Observer o);
    void detach(Observer o);
}
