package za.co.pnb;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "Kalaha Gaming As A Service",
        version = "1.0",
        description = "Gaming as a service",
        contact = @Contact(
                name = "Pieter Botha",
                email = "p@pnb.co.za"
        )),
        servers = {
                @Server(description = "Hosted-GCP-GAE", url = "https://kalaha-339608.ew.r.appspot.com/kalaha"),
                @Server(description = "Localhost", url = "http://localhost:8080/kalaha")
        },
        externalDocs = @ExternalDocumentation(description = "Sourcecode", url = "https://gitlab.com/bolcom/pieter-nicolaas-botha/-/tree/master")
)
public class KalahaApplication {
    public static void main(String[] args) {
        SpringApplication.run(KalahaApplication.class, args);
    }
}

