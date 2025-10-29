# Etapa única — apenas executa o jar já gerado
FROM eclipse-temurin:17-jdk

# Diretório de trabalho no container
WORKDIR /app

# Copia o JAR gerado localmente
COPY target/gestao_api-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Comando de execução
ENTRYPOINT ["java", "-jar", "app.jar"]
