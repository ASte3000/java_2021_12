package homework;


import java.util.*;

public class CustomerService {
    private final TreeMap<Customer, String> map = new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        return createDeepCopy(map.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return createDeepCopy(map.higherEntry(customer));
    }

    public void add(Customer customer, String data) {
        map.put(customer, data);
    }

    private static Map.Entry<Customer, String> createDeepCopy(Map.Entry<Customer, String> entry) {
        if (entry == null)
            return null;

        Customer customer = entry.getKey();
        Customer customerCopy = new Customer(customer.getId(), customer.getName(), customer.getScores());

        return new AbstractMap.SimpleImmutableEntry<>(customerCopy, entry.getValue());
    }
}
