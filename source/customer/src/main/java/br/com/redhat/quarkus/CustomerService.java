package br.com.redhat.quarkus;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class CustomerService {

    @Transactional
    public Customer addCustomer(Customer customer){
        Customer.persist(customer);
        return customer;
    }

    @Transactional
    public Customer getCustomerById(Customer customer){
        customer = Customer.findById(customer.id);    
		return customer;
    }

    @Transactional
    public List<Customer> getByPrimeiroNome(Customer customer){
        List<Customer> cList = Customer.findByPrimeiroNome(customer);
        return cList;
    }

    @Transactional
    public List<Customer> getByPrimeiroNomeOrSobreNome(Customer customer){
        List<Customer> cList = Customer.findByPrimeiroOrSobreNome(customer);
        return cList;
    }

    @Transactional
    public Customer getCustomerByRg(Customer customer){
        customer = Customer.findByRg(customer);    
		return customer;
    }

    @Transactional
    public List<Customer> listCustomer(){
        List<Customer> cList = Customer.listAll();
        return cList;
    }

    @Transactional
    public Customer deleteCustomer(Customer customer){
        customer = Customer.findById(customer.id);
        Customer.deleteById(customer.id);
        return customer;
    }

    @Transactional
    public Customer updateCustomer(Customer customer){
        Customer customerEntity = Customer.findById(customer.id);
        if (customerEntity != null){
            customerEntity.setPrimeiroNome(customer.getPrimeiroNome());
            customerEntity.setSobreNome(customer.getSobreNome());
            customerEntity.setRg(customer.getRg());
        }
        return customerEntity;
    }
    
}