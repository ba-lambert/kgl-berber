# Kigali Barber

Backend learning project built to practice real backend engineering, not AI wrapper code. The goal is to get deep, hands-on experience with the architecture and infrastructure patterns that back most production systems, then close with a Retrieval-Augmented Generation (RAG) module once the fundamentals are solid.

## Focus

- **80% backend craftsmanship, not AI.** Clean architecture, layered modules (controller / service / repository / dto / entity), proper transaction boundaries, database migrations, authentication and authorization done right.
- **Deep dives into "over-engineered" infrastructure**, on purpose, for learning: gRPC and Kafka get real implementations here, even where a REST call or a simple queue would suffice for an app this size — the point is understanding how and why these tools are used at scale.
- **RAG last.** Once the backend fundamentals are solid, the project adds a Retrieval-Augmented Generation module on top, as the final, AI-facing layer rather than the starting point.

## Stack

- Java 17, Spring Boot 4.1.1
- PostgreSQL, Spring Data JPA, Hibernate
- Liquibase for schema migrations
- Spring Security
- gRPC (planned)
- Kafka (planned)
- RAG / LLM integration (planned, final phase)

## Architecture

Each feature module follows a strict layered structure:

- `controller` — HTTP layer only (routing, request/response, DTO validation)
- `service` — business logic / use cases
- `repository` — persistence layer (Spring Data JPA), no raw SQL in services
- `dto` — request/response shapes and validation
- `entity` — JPA/domain models
- Liquibase changesets under `db/changelog/changes`, one file per change, included from `db.changelog-master.yaml`

`spring.jpa.hibernate.ddl-auto=validate` — schema is owned by Liquibase, Hibernate only validates it matches the entities.

## Roadmap

1. Backend fundamentals — clean layering, migrations, auth, transactions (in progress)
2. gRPC service-to-service communication
3. Kafka event-driven messaging
4. RAG module
