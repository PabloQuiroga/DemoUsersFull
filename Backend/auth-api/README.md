# auth-api

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/auth-api-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Architecture

This project follows **Clean Architecture** principles to ensure separation of concerns and independence from frameworks.

### Structure

The code is organized into three main layers:

*   **Domain (`org.siar.domain`)**: Contains the core business logic and entities. It has no dependencies on frameworks or external libraries.
    *   `model`: Business entities (Pure Java Objects).
    *   `port`: Interfaces defining input/output boundaries (e.g., repositories).
    *   `exception`: Business exceptions.
*   **Application (`org.siar.application`)**: Orchestrates the flow of data between the UI/API and the Domain.
    *   `usecase`: Application specific business rules.
    *   `dto`: Data Transfer Objects for use cases.
    *   `mapper`: Converters between DTOs and Domain entities.
*   **Infrastructure (`org.siar.infrastructure`)**: Implements the interfaces defined in the Domain and interacts with external tools (Database, REST API, etc.).
    *   `entrypoint.rest`: REST Controllers (JAX-RS Resources).
    *   `adapter.persistence`: Database implementations (Hibernate/Panache).
    *   `config`: Framework configuration.

### Implementation Example: User Entity

To illustrate the architecture, the `User` feature is implemented with a strict separation between the domain and persistence models:

1.  **Domain Model (`domain/model/User.java`)**: A pure POJO that represents the user in the business context. It contains no framework-specific annotations or logic.

2.  **Domain Port (`domain/port/repository/UserRepository.java`)**: An interface that defines the contract for user persistence operations (e.g., `save`, `findById`). The domain layer depends on this abstraction, not on a concrete implementation.

3.  **Persistence Entity (`infrastructure/adapter/persistence/entity/UserEntity.java`)**: The JPA entity that maps to the `users` table in the database. It includes `@Entity` annotations and extends `PanacheEntityBase`. This class is only known within the infrastructure layer.

4.  **Persistence Mapper (`infrastructure/adapter/persistence/mapper/UserMapper.java`)**: A component responsible for converting `User` domain objects to `UserEntity` persistence objects, and vice-versa.

5.  **Persistence Repository (`infrastructure/adapter/persistence/repository/PanacheUserRepository.java`)**: The concrete implementation of the `UserRepository` interface. It uses Panache and the `UserMapper` to perform database operations, fulfilling the contract defined by the domain port.

This separation ensures the domain remains pure and independent of the database technology, making the system more modular, testable, and easier to maintain.

## Related Guides

- REST ([guide](https://quarkus.io/guides/rest)): A Jakarta REST implementation utilizing build time processing and Vert.x. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it.
- Flyway ([guide](https://quarkus.io/guides/flyway)): Handle your database schema migrations
- Hibernate ORM with Panache ([guide](https://quarkus.io/guides/hibernate-orm-panache)): Simplify your persistence code for Hibernate ORM via the active record or the repository pattern
- SmallRye Health ([guide](https://quarkus.io/guides/smallrye-health)): Monitor service health
- JDBC Driver - MySQL ([guide](https://quarkus.io/guides/datasource)): Connect to the MySQL database via JDBC

## Provided Code

### Hibernate ORM

Create your first JPA entity

[Related guide section...](https://quarkus.io/guides/hibernate-orm)

[Related Hibernate with Panache section...](https://quarkus.io/guides/hibernate-orm-panache)


### REST

Easily start your REST Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)

### SmallRye Health

Monitor your application's health using SmallRye Health

[Related guide section...](https://quarkus.io/guides/smallrye-health)
