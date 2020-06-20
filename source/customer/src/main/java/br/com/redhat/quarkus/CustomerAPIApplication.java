package br.com.redhat.quarkus;

import javax.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@OpenAPIDefinition(
    tags = {
        @Tag(name="customers", description="API de Gerenciamento de Customers"),
    },
    info = @Info(
        title="Customers API",
        version = "1.0.0",
        contact = @Contact(
            name = "Customers API Support",
            url = "http://customersapi.com/contact",
            email = "customersapi@customersapi.com"),
        license = @License(
            name = "Apache 2.0",
            url = "http://www.apache.org/licenses/LICENSE-2.0.html"))
)
public class CustomerAPIApplication extends Application{
    
}