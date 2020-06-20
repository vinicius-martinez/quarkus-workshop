package br.com.redhat.quarkus;

import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CustomerService {

    private Set<Customer> customerSet = new HashSet<Customer>(0);

    public Customer addCustomer(Customer customer){
        if (customerSet.contains(customer)){
            for (Customer customerEntity : customerSet) {
                if (customerEntity.equals(customer)){
                    return customerEntity;
                }
            }
        }
        customerSet.add(customer);
        return customer;
    }

    public Customer getCustomer(Customer customer){
        if (customerSet.contains(customer)){
            for (Customer customerEntity : customerSet) {
                if (customerEntity.equals(customer)){
                    return customerEntity;
                }
            }
        }
		return null;
    }

    public Set<Customer> listCustomer(){
        return customerSet;
    }

    public Customer deleteCustomer(Customer customer){
        if (customerSet.contains(customer)){
            customerSet.remove(customer);
            return customer;
        }
        return null;
    }

    public Customer updateCustomer(Customer customer){
        if (customerSet.contains(customer)){
            for (Customer customerEntity : customerSet) {
                if (customerEntity.equals(customer)){
                    customerEntity.setRg(customerEntity.getRg());
                    customerEntity.setPrimeiroNome(customer.getPrimeiroNome());
                    customerEntity.setSobreNome(customer.getSobreNome());
                    return customerEntity;
                }
            }
        }
        return null;
    }
    
}