public interface Publisher {
    void notifySubscribers();
    void add(Subscriber o);
    void remove(Subscriber o);
}
