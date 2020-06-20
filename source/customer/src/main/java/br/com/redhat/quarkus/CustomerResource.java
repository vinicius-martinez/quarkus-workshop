package br.com.redhat.quarkus;

import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/customers")
public class CustomerResource {

    @Inject
    CustomerService customerService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Customer> listCustomer(){
        return customerService.listCustomer();
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/rg/{rg}")
    public Customer getCustomer(@PathParam("rg") Long rg){
        Customer customerEntity = new Customer();
        customerEntity.setRg(rg);
        customerEntity = customerService.getCustomer(customerEntity);
        return customerEntity;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Customer addCustomer(Customer customer){
        Customer customerEntity = customerService.addCustomer(customer);
        return customerEntity;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Customer updatCustomer(Customer customer){
        Customer customerEntity = customerService.updateCustomer(customer);
        return customerEntity;
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/rg/{rg}")
    public Customer deleteCustomer(@PathParam("rg") Long rg){
        Customer customerEntity = new Customer();
        customerEntity.setRg(rg);
        customerEntity = customerService.deleteCustomer(customerEntity);
        return customerEntity;
    }

}