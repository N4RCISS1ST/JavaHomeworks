package org.homework;

import java.util.AbstractMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;


public class CustomerService {

    private final NavigableMap<Customer, String> customerData = new TreeMap<>((c1, c2) -> Long.compare(c1.getScores(), c2.getScores()));
    // todo: 3. надо реализовать методы этого класса
    // важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    public Map.Entry<Customer, String> getSmallest() {
        return createImmutableEntry(customerData.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return createImmutableEntry(customerData.higherEntry(customer));
    }

    public void add(Customer customer, String data) {
        customerData.put(new Customer(customer.getId(), customer.getName(), customer.getScores()), data);
    }

    private Map.Entry<Customer, String> createImmutableEntry(Map.Entry<Customer, String> entry) {
        if (entry == null) {
            return null;
        }
        Customer original = entry.getKey();
        Customer copy = new Customer(original.getId(), original.getName(), original.getScores());
        return new AbstractMap.SimpleEntry<>(copy, entry.getValue());
    }
}