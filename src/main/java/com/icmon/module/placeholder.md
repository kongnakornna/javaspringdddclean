# Business Modules - How to Use

This folder is where you should implement all the **business logic** of your application following Domain-Driven Design (DDD) and Clean Architecture principles.

## 🎯 Recommended Structure

For each business context, create a module following this structure:

```
modules/
└── your_module/
    ├── application/           # Application Layer
    │   ├── interfaces/       # Service interfaces
    │   │   └── UserService.java
    │   └── impl/            # Service implementations
    │       └── UserServiceImpl.java
    ├── domain/              # Domain Layer
    │   ├── User.java        # Entities
    │   ├── Email.java       # Value Objects
    │   └── UserStatus.java  # Enums
    └── infrastructure/      # Infrastructure Layer
        ├── repository/
        │   ├── UserRepository.java      # Interface
        │   └── UserRepositoryImpl.java  # Implementation
        └── mapper/
            └── UserMapper.java          # MapStruct Mapper
```

## 🚀 Practical Example: User Module

### 1. Domain Layer

#### User.java (Entity)
```java
package com.template.app.modules.user.domain;

import com.template.app._shared.domain.GenericBusinessClass;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class User extends GenericBusinessClass {
    private String name;
    private String email;
    private UserStatus status;
    
    // Business methods
    public void activate() {
        if (this.status == UserStatus.SUSPENDED) {
            throw new DomainException("Cannot activate suspended user");
        }
        this.status = UserStatus.ACTIVE;
    }
    
    public boolean canLogin() {
        return status == UserStatus.ACTIVE;
    }
}
```

#### UserStatus.java (Enum)
```java
package com.template.app.modules.user.domain;

public enum UserStatus {
    PENDING,
    ACTIVE,
    INACTIVE,
    SUSPENDED
}
```

### 2. Application Layer

#### UserService.java (Interface)
```java
package com.template.app.modules.user.application.interfaces;

import com.template.app._shared.application.interfaces.GenericService;
import com.template.app.modules.user.domain.User;
import java.util.Optional;

public interface UserService extends GenericService<User> {
    User createUser(String name, String email);
    Optional<User> findByEmail(String email);
    void activateUser(UUID userId);
}
```

#### UserServiceImpl.java (Implementation)
```java
package com.template.app.modules.user.application.impl;

import com.template.app._shared.application.GenericServiceImpl;
import com.template.app.modules.user.application.interfaces.UserService;
import com.template.app.modules.user.domain.User;
import com.template.app.modules.user.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl 
    extends GenericServiceImpl<User, UserRepository> 
    implements UserService {

    public UserServiceImpl(UserRepository repository) {
        super(repository);
    }

    @Override
    public User createUser(String name, String email) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setStatus(UserStatus.PENDING);
        
        return create(user); // Method inherited from GenericServiceImpl
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email, getRepositoryAuth());
    }

    @Override
    public void activateUser(UUID userId) {
        User user = read(userId); // Inherited method
        user.activate(); // Business method
        update(userId, user); // Inherited method
    }
}
```

### 3. Infrastructure Layer

#### UserEntity.java (JPA Entity)
```java
package com.template.app.modules.user.infrastructure;

import com.template.app._shared.infrastructure.GenericBusinessEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "users")
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends GenericBusinessEntity {
    
    @Column(nullable = false)
    private String name;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Enumerated(EnumType.STRING)
    private UserStatus status;
}
```

#### UserRepository.java (Interface)
```java
package com.template.app.modules.user.infrastructure.repository;

import com.template.app._shared.infrastructure.repository.interfaces.GenericBusinessRepository;
import com.template.app.modules.user.domain.User;
import java.util.Optional;

public interface UserRepository extends GenericBusinessRepository<User> {
    Optional<User> findByEmail(String email, RepositoryAuth auth);
}
```

#### UserRepositoryImpl.java (Implementation)
```java
package com.template.app.modules.user.infrastructure.repository;

import com.template.app._shared.infrastructure.repository.GenericBusinessRepositoryImpl;
import com.template.app.modules.user.domain.User;
import com.template.app.modules.user.infrastructure.UserEntity;
import com.template.app.modules.user.infrastructure.mapper.UserMapper;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl 
    extends GenericBusinessRepositoryImpl<User, UserEntity>
    implements UserRepository {

    public UserRepositoryImpl(
        UserMapper mapper,
        SimpleJpaRepository<UserEntity, UUID> jpaRepository
    ) {
        super(mapper, jpaRepository, UserEntity.class);
    }

    @Override
    public Optional<User> findByEmail(String email, RepositoryAuth auth) {
        // Specific implementation using JPA Criteria or Query Methods
        return jpaRepository.findByEmailAndDeletedFalse(email)
            .map(mapper::toEntity);
    }
}
```

#### UserMapper.java (MapStruct)
```java
package com.template.app.modules.user.infrastructure.mapper;

import com.template.app._shared.infrastructure.mapper.GenericBusinessMapper;
import com.template.app.modules.user.domain.User;
import com.template.app.modules.user.infrastructure.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper extends GenericBusinessMapper<User, UserEntity> {
    // Additional specific mappings if needed
}
```

## 📊 Controller (Presentation Layer)

```java
package com.template.app.modules.user.presentation;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "User management operations")
public class UserController {

    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Create a new user")
    public ResponseEntity<UserDTO> createUser(@RequestBody CreateUserDTO dto) {
        User user = userService.createUser(dto.getName(), dto.getEmail());
        return ResponseEntity.ok(UserDTO.from(user));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID")
    public ResponseEntity<UserDTO> getUser(@PathVariable UUID id) {
        User user = userService.read(id);
        return ResponseEntity.ok(UserDTO.from(user));
    }

    @PutMapping("/{id}/activate")
    @Operation(summary = "Activate user")
    public ResponseEntity<Void> activateUser(@PathVariable UUID id) {
        userService.activateUser(id);
        return ResponseEntity.ok().build();
    }
}
```

## 🎁 What You Get Automatically

By following this structure, you automatically inherit:

### ✅ Complete CRUD Operations
- `create()`, `read()`, `update()`, `delete()`
- Synchronous and asynchronous versions
- Batch operations

### ✅ Audit System
- `createdAt`, `updatedAt`, `deletedAt`
- User/company control
- Automatic soft delete

### ✅ Pagination and Search
- `findAllPaginated()`
- `findAllByIds()`
- Optimized queries

### ✅ Error Handling
- Typed exceptions per layer
- Automatic logging
- Standardized HTTP responses

### ✅ AOP Monitoring
- Logging of all calls
- Exception capture
- Performance metrics

### ✅ Integrated Security
- Automatic JWT authentication
- Company-based access control
- Always available user context

## 🔧 Useful Commands

### Creating a New Module
```bash
# Directory structure
mkdir -p src/main/java/com/icmon/app/modules/your_module/{application/{interfaces,impl},domain,infrastructure/{repository,mapper}}

# Tests
mkdir -p src/test/java/com/icmon/app/modules/your_module/{application,domain,infrastructure}
```

### Running Module Tests
```bash
mvn test -Dtest="com.template.app.modules.your_module.**"
```

## 📚 Next Steps

1. **Create your first module** following the example above
2. **Implement tests** using the base classes
3. **Configure REST endpoints** if needed
4. **Add specific business rules**
5. **Document** your APIs in Swagger

## 💡 Tips

- **Keep the domain pure**: Don't use Spring annotations in domain entities
- **Use Value Objects**: For concepts like Email, CPF, Money, etc.
- **Apply validations**: At the moment of entity creation
- **Test business rules**: Focus on domain tests
- **Document decisions**: Comment the "why", not just the "how"

---

**🚀 Now you're ready to implement your business logic following DDD and Clean Architecture best practices!** 