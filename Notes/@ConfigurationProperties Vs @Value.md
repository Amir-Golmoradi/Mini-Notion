# Difference Between @ConfigurationProperties and Value Object

## 1. @ConfigurationProperties
- **Purpose**: Binds external configuration (from `application.yml` or `application.properties`) into strongly typed Java objects.
- **Scope**: Infrastructure-level; used to configure framework or application settings.
- **Example Use Case**: MinIO configuration, database settings, Kafka properties.
- **Characteristics**:
    - Mapped automatically by Spring Boot.
    - Supports validation annotations (`@NotNull`, `@Min`, etc.).
    - Centralizes configuration in one place.
    - Typically immutable but can be mutable depending on design.

```java
@ConfigurationProperties(prefix = "minio")
public record MinioProperties(
    String endpoint,
    String bucket,
    String accessKey,
    String secretKey
) {}
