# Tasktool

A hexagonal flavored webapplication for trivial task management. Exploring [Quarkus](https://quarkus.io) with Panache and Qute, [HTMX](https://htmx.org/) and [Bulma](https://bulma.io/).

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw quarkus:dev
```

A working docker installation is required for spinnning up the database using testcontainers.

## Database for production builds

To setup a suitable database for the provided database configuration, running the application as JAR or container, do as follows, but consider altering the password:

```shell script
docker run --name prod-postgres -p 32775:5432 -e POSTGRES_PASSWORD=a-powerful-placeholder -d postgres
```

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it requires the dependencies in `target/quarkus-app/lib/`.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

## Docker Container

If you want to build a containerized app, execute the following command:

```shell script
./mvnw install -Dquarkus.container-image.build=true
```

You can then run your container with: `docker run --network=host -d *tasktool`