public interface Subscriber {
    void notifySubscribers();
    void add(Observer o);
    void remove(Observer o);
}
