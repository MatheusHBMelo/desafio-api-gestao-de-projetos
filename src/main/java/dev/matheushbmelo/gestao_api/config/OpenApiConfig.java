package dev.matheushbmelo.gestao_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {
    @Value("${app.server.url.dev:http://localhost:8080}")
    private String devUrl;

    @Bean
    public OpenAPI myOpenApi() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("URL do ambiente de desenvolvimento");

        Contact contact = new Contact();
        contact.setName("Matheus Barbosa");
        contact.setEmail("matheushbmelo@gmail.com");

        License mitLicense = new License();
        mitLicense.setName("MIT License");
        mitLicense.setUrl("https://choosealicense.com/licenses/mit/");

        Info info = new Info();
        info.setTitle("Gestão API");
        info.setVersion("1.0.0");
        info.setContact(contact);
        info.setDescription("Sistema de gestão de projetos e tarefas");
        info.setLicense(mitLicense);

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer));
    }
}
