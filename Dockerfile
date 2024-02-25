FROM gradle:jdk11 AS builder

# Copy in the gradle wrapper files
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY src src

# Set the user to use when running the gradle commands
USER gradle

# Use the gradle wrapper script to build the jar
RUN ./gradlew clean bootjar

# create the second stage of the build that will contain only the compiled jar and the JRE to run it.
FROM eclipse-temurin:21-jre-alpine AS runner

# Optionally create a user for the application to run as
ARG USER_NAME=eshop
ARG USER_UID=1000
ARG USER_GID=${USER_UID}

RUN addgroup -g ${USER_GID} ${USER_NAME} && \
    adduser -h /opt/advshop -D -u ${USER_UID} -G ${USER_NAME} ${USER_NAME}

# Switch to the new user
USER ${USER_NAME}

# Set the working directory for the application
WORKDIR /opt/advshop

# Copy the jar file from the 'builder' stage to the 'runner' stage
COPY --from=builder --chown=${USER_UID}:${USER_GID} /home/gradle/src/build/libs/*.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
