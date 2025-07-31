# Usa imagen con Maven y JDK 17
FROM maven:3.9.4-eclipse-temurin-17 AS build

# Copia el código fuente
COPY . /app
WORKDIR /app

# Construye el .jar sin correr tests
RUN mvn clean package -DskipTests

# -----------------------------

# Usa una imagen más liviana para ejecutar
FROM eclipse-temurin:17-jdk

# Copia el .jar generado desde la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Expone el puerto 8080 (opcional, pero recomendable)
EXPOSE 8080

# Comando de inicio
ENTRYPOINT ["java", "-jar", "app.jar"]
