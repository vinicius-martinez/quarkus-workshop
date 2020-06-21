package br.com.redhat.quarkus;

import java.util.Random;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/cep")
public class CEPResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getNumeroCEP() {
        CEP  cep = new CEP();
        cep.setNumeroCep(new Random().ints(1, 99999).findFirst().getAsInt());
        return Response.ok(cep).build();
    }

}