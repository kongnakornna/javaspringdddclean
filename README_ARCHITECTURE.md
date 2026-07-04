# Spring Boot DDD Template Architecture

This document details the architecture and design patterns used in the template.

## 📋 Table of Contents

- [Architecture Overview](#architecture-overview)
- [Domain-Driven Design (DDD)](#domain-driven-design-ddd)
- [Clean Architecture](#clean-architecture)
- [Generic Structures](#generic-structures)
- [Design Patterns](#design-patterns)
- [Data Flow](#data-flow)
- [Error Handling](#error-handling)

## Architecture Overview

The template follows a hexagonal architecture (ports and adapters) combined with DDD and Clean Architecture, organizing code into well-defined layers:

```
┌─────────────────────────────────────────────────────────────┐
│                    Presentation Layer                       │
│                   (Controllers/REST)                       │
├─────────────────────────────────────────────────────────────┤
│                   Application Layer                        │
│              (Services, Use Cases, DTOs)                   │
├─────────────────────────────────────────────────────────────┤
│                     Domain Layer                           │
│               (Entities, Value Objects)                    │
├─────────────────────────────────────────────────────────────┤
│                 Infrastructure Layer                       │
│           (Repositories, External Services)                │
└─────────────────────────────────────────────────────────────┘
```

## Domain-Driven Design (DDD)

### Applied Concepts

#### 1. **Bounded Contexts**
Each module represents a bounded context:

```
modules/
├── user/           # User Context
├── company/        # Company Context
├── product/        # Product Context
└── order/          # Order Context
```

#### 2. **Entities**
Objects with unique identity that represent domain concepts:

```java
public class User extends GenericBusinessClass {
    private String name;
    private Email email;
    private UserStatus status;
    
    // Business methods
    public void activate() {
        this.status = UserStatus.ACTIVE;
    }
}
```

#### 3. **Value Objects**
Immutable objects that represent values:

```java
@Value
public class Email {
    private final String value;
    
    public Email(String email) {
        if (!isValid(email)) {
            throw new DomainException("Invalid email format");
        }
        this.value = email;
    }
}
```

#### 4. **Aggregates**
Groups of entities and value objects treated as a unit:

```java
public class Order extends GenericBusinessClass {
    private List<OrderItem> items;
    private OrderStatus status;
    
    public void addItem(Product product, int quantity) {
        // Business rules for adding item
        if (status != OrderStatus.DRAFT) {
            throw new DomainException("Cannot modify confirmed order");
        }
        // ...
    }
}
```

#### 5. **Domain Services**
Services that encapsulate domain logic that doesn't belong to a specific entity:

```java
@Component
public class OrderPricingService {
    public Money calculateTotal(Order order) {
        // Complex calculation logic
    }
}
```

## Clean Architecture

### Dependencies

The architecture follows the dependency rule: **outer layers depend on inner layers, never the opposite**.

```
Infrastructure → Application → Domain
     ↑              ↑
   Database      Use Cases
   External      Services
   APIs
```

### Layers

#### 1. **Domain Layer** (Core)
- **Entities**: Business objects with identity
- **Value Objects**: Immutable values
- **Domain Services**: Domain logic
- **Repository Interfaces**: Contracts for persistence

```java
package com.template.app.modules.user.domain;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(UUID id);
    Optional<User> findByEmail(Email email);
}
```

#### 2. **Application Layer** (Use Cases)
- **Services**: Use case orchestration
- **DTOs**: Data transfer objects
- **Interfaces**: Contracts for external services

```java
@Service
public class UserService extends GenericServiceImpl<User, UserRepository> {
    
    public User createUser(CreateUserDTO dto) {
        // Business validations
        // Dependency orchestration
        // Persistence
    }
}
```

#### 3. **Infrastructure Layer** (Details)
- **Repositories**: Persistence implementations
- **External Services**: External API integration
- **Configuration**: Technical configurations

```java
@Repository
public class UserRepositoryImpl 
    extends GenericBusinessRepositoryImpl<User, UserEntity>
    implements UserRepository {
    
    // Specific implementation if needed
}
```

## Generic Structures

### Entity Hierarchy

The system uses a class hierarchy to maximize code reuse:

```
GenericClass (Base)
├── GenericBusinessClass (Business)
└── User, Product, Order... (Specific)

GenericEntity (JPA Base)
├── GenericBusinessEntity (JPA Business)
└── UserEntity, ProductEntity... (JPA Specific)
```

### Benefits

1. **Reduced Code Duplication**: Common functionality centralized
2. **Consistency**: Uniform patterns throughout the system
3. **Maintainability**: Changes in one place affect the entire system
4. **Testability**: Reusable base tests

### Generic Repository

```java
public interface GenericBusinessRepository<E extends GenericClass> {
    // Basic CRUD operations
    E create(E entity, RepositoryAuth auth);
    E read(UUID id, RepositoryAuth auth);
    E update(E entity, RepositoryAuth auth);
    void delete(UUID id, RepositoryAuth auth);
    
    // Advanced operations
    Page<E> findAllPaginated(PageRequest pageRequest, RepositoryAuth auth);
    Optional<List<E>> findAllByIds(List<UUID> ids, RepositoryAuth auth);
    
    // Asynchronous operations
    CompletableFuture<E> createAsync(E entity, RepositoryAuth auth);
    CompletableFuture<E> updateAsync(E entity, RepositoryAuth auth);
}
```

## Design Patterns

### 1. **Repository Pattern**
Encapsulates data access logic:

```java
// Interface in domain
public interface UserRepository {
    User save(User user);
    Optional<User> findByEmail(String email);
}

// Implementation in infrastructure
@Repository
public class JpaUserRepository implements UserRepository {
    // JPA implementation
}
```

### 2. **Factory Pattern**
For complex object creation:

```java
@Component
public class UserFactory {
    public User createUser(String name, String email, UserType type) {
        // Creation logic based on type
    }
}
```

### 3. **Strategy Pattern**
For interchangeable algorithms:

```java
public interface PricingStrategy {
    Money calculatePrice(Order order);
}

@Component
public class RegularPricingStrategy implements PricingStrategy {
    // Regular price implementation
}

@Component
public class PremiumPricingStrategy implements PricingStrategy {
    // Premium price implementation
}
```

### 4. **Observer Pattern** (via Spring Events)
For inter-module communication:

```java
@EventListener
public void handleUserCreated(UserCreatedEvent event) {
    // Action when user is created
}
```

## Data Flow

### Request Flow

```
1. Controller receives HTTP request
2. Validates input data
3. Calls Service (Application Layer)
4. Service executes business logic
5. Service calls Repository
6. Repository persists/retrieves data
7. Data flows back through the stack
8. Controller returns HTTP response
```

### Practical Example

```java
// 1. Controller
@PostMapping("/users")
public ResponseEntity<UserDTO> createUser(@RequestBody CreateUserDTO dto) {
    User user = userService.createUser(dto);
    return ResponseEntity.ok(userMapper.toDTO(user));
}

// 2. Service
@Transactional
public User createUser(CreateUserDTO dto) {
    // Validations
    validateUserData(dto);
    
    // Entity creation
    User user = User.builder()
        .name(dto.getName())
        .email(new Email(dto.getEmail()))
        .build();
    
    // Persistence
    return repository.create(user, getRepositoryAuth());
}

// 3. Repository
public User create(User user, RepositoryAuth auth) {
    UserEntity entity = mapper.toSchemaForCreate(user);
    setCommonFields(entity, auth);
    UserEntity saved = jpaRepository.save(entity);
    return mapper.toEntity(saved);
}
```

## Error Handling

### Exception Hierarchy

```
SystemGlobalException (Base)
├── DomainException (Business rules)
├── ApplicationException (Use cases)
├── InfrastructureException (Persistence)
└── AdapterException (Integrations)
```

### Global Exception Handler

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> handleDomainException(DomainException ex) {
        // Log exception
        logService.saveErrorLogAsync(buildErrorLog(ex));
        
        // Return standardized response
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(ex.getMessage()));
    }
}
```

### Aspect-Oriented Programming (AOP)

The system uses AOP for:

1. **Automatic logging** of method calls
2. **Exception capture** with context
3. **Operation auditing**
4. **Performance monitoring**

```java
@Aspect
@Component
public class SystemMonitor {
    
    @Around("execution(* com.template.app.modules..application..*(..))")
    public Object domainMonitor(ProceedingJoinPoint joinPoint) throws Throwable {
        // Capture context
        String requestId = MDC.get("requestId");
        String userId = MDC.get("userId");
        
        try {
            return joinPoint.proceed();
        } catch (Exception ex) {
            // Log exception with full context
            saveMethodCallLog(joinPoint, requestId, userId, true);
            throw ex;
        }
    }
}
```

## Multi-Profile Configuration

### Multi-Profile Strategy

```yaml
# application.yml (Base)
spring:
  profiles:
    active: ${SPRING_PROFILES_ACTIVE:dev}

# application-dev.yml (Development)
spring:
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: validate

# application-prod.yml (Production)
spring:
  jpa:
    show-sql: false
    hibernate:
      ddl-auto: validate
```

### Conditional Configuration

```java
@Configuration
@Profile({"dev", "test"})
public class DevConfiguration {
    // Development-only configurations
}

@Configuration
@Profile("prod")
public class ProductionConfiguration {
    // Production-only configurations
}
```

## Security

### JWT Authentication

```java
@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                  HttpServletResponse response, 
                                  FilterChain filterChain) {
        // Extract JWT token
        // Validate token
        // Configure security context
        // Configure MDC for logging
    }
}
```

### Access Control

The system uses MDC (Mapped Diagnostic Context) for:

1. **User tracking** in all operations
2. **Access control** based on company/tenant
3. **Complete audit** of user actions

```java
// Automatically configured by JWT Filter
MDC.put("userId", user.getId().toString());
MDC.put("whitelabelId", user.getCompanyId().toString());
MDC.put("requestId", UUID.randomUUID().toString());
```

## Conclusion

This architecture provides:

✅ **Clear separation of responsibilities**
✅ **High testability and maintainability**
✅ **Maximum code reuse**
✅ **Flexibility for changes**
✅ **Complete observability**
✅ **Integrated security**

The template serves as a solid foundation for complex enterprise applications, maintaining simplicity for smaller projects through intelligent use of generic structures and well-established conventions.
