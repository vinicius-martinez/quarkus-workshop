package br.com.redhat.quarkus;

import java.util.List;

import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.panache.common.Parameters;

@Entity
public class Customer extends PanacheEntity {

    public String primeiroNome;
    public Integer rg;
	public String sobreNome;
	
	public static List<Customer> findByPrimeiroNome(Customer customer){
		List<Customer> customerList = list("primeiroNome",  customer.getPrimeiroNome());
		return customerList;
	}

	public static List<Customer> findByPrimeiroOrSobreNome(Customer customer){
		List<Customer> customerList = list("primeiroNome = :firstName or sobreNome = :lastName",
										Parameters.with("firstName", customer.getPrimeiroNome())
											.and("lastName", customer.getSobreNome()));
		return customerList;
	}
	public static Customer findByRg(Customer customer){
		customer = Customer.find("rg", customer.getRg()).firstResult();
		return customer;
	}
	public String getPrimeiroNome() {
		return primeiroNome;
	}
	public void setPrimeiroNome(String primeiroNome) {
		this.primeiroNome = primeiroNome.toUpperCase();
	}
	public Integer getRg() {
		return rg;
	}
	public void setRg(Integer rg) {
		this.rg = rg;
	}
	public String getSobreNome() {
		return sobreNome;
	}
	public void setSobreNome(String sobreNome) {
		this.sobreNome = sobreNome.toUpperCase();
	}
	  
}