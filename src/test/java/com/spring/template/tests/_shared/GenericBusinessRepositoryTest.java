package com.spring.template.tests._shared;

import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.icmon._shared.domain.GenericClass;
import com.icmon._shared.infrastructure.RepositoryAuth;
import com.icmon._shared.infrastructure.repository.interfaces.GenericBusinessRepository;
import com.icmon.exception.SystemGlobalException;
import com.icmon.exception.models.InfrastructureException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


public abstract class GenericBusinessRepositoryTest<
        E extends GenericClass> extends GenericTest{

    protected GenericBusinessRepository<E> repository;
    protected RepositoryAuth repositoryAuth;

    protected abstract E createEntity() throws BadRequestException, SystemGlobalException;

    protected abstract GenericBusinessRepository<E> createRepository();


    @BeforeEach
    public void setUp() {
        repository = createRepository();
        repositoryAuth = new RepositoryAuth(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());
    }

    @Test
    @Transactional
    public void testCreate(){
        try {
            E entity = createEntity();
            E createdEntity = repository.create(entity, repositoryAuth);

            assertNotNull(createdEntity.getId(), "A entidade criada deveria ter um ID gerado.");
            assertNotNull(createdEntity.getCreatedAt(), "A entidade criada deveria ter a data de criação definida.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Transactional
    public void testRead(){
        try {
        E entity = createEntity();
        E createdEntity = repository.create(entity, repositoryAuth);
        E readEntity = repository.read(createdEntity.getId(), repositoryAuth);

        assertEquals(createdEntity.getId(), readEntity.getId(), "O ID da entidade lida deveria corresponder ao ID da entidade criada.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Transactional
    public void testReadDeleted(){
        try {

        E entity = createEntity();
        E createdEntity = repository.create(entity, repositoryAuth);
        repository.delete(createdEntity.getId(), repositoryAuth);

        assertThrows(InfrastructureException.class, () -> repository.read(createdEntity.getId(), repositoryAuth));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Transactional
    public void testUpdate(){
        try {
        E entity = createEntity();
        E createdEntity = repository.create(entity, repositoryAuth);

        E updatedEntity = repository.update(createdEntity, repositoryAuth);

        assertNotNull(updatedEntity.getId(), "O ID da entidade atualizada não deve ser nulo.");
        assertNotEquals(createdEntity.getUpdatedAt(), updatedEntity.getUpdatedAt(), "A data de atualização deveria ser alterada.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Transactional
    public void testUpdateDeleted(){
        try {
        E entity = createEntity();
        E createdEntity = repository.create(entity, repositoryAuth);
        repository.delete(createdEntity.getId(), repositoryAuth);

            Exception exception = assertThrows(SystemGlobalException.class, () -> repository.update(createdEntity, repositoryAuth));
        assertNotNull(exception, "Deveria ser lançada uma exceção ao tentar atualizar uma entidade deletada.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Transactional
    public void testDelete(){
        try {
        E entity = createEntity();
        E createdEntity = repository.create(entity, repositoryAuth);
        repository.delete(createdEntity.getId(), repositoryAuth);

        assertThrows(InfrastructureException.class, () -> repository.read(createdEntity.getId(), repositoryAuth));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Transactional
    public void testFindAllAfterDelete(){
        try {
        E entity = createEntity();
        E createdEntity = repository.create(entity, repositoryAuth);

        repository.delete(createdEntity.getId(), repositoryAuth);
        Optional<List<E>> allEntities = repository.findAll(repositoryAuth);

        assertTrue(allEntities.isEmpty(), "Deveria retornar uma lista de vazia.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Transactional
    public void testFindAll(){
        try {
        E entity = createEntity();
        repository.create(entity, repositoryAuth);

        Optional<List<E>> allEntities = repository.findAll(repositoryAuth);

        assertTrue(allEntities.isPresent(), "A busca por todas as entidades não deveria retornar nulo.");
        assertFalse(allEntities.get().isEmpty(), "A lista de entidades não deve estar vazia.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCreateAsync() {
        try {
            E entity = createEntity();
            E createdEntity = repository.createAsync(entity, repositoryAuth).join();

            assertNotNull(createdEntity.getId(), "A entidade criada deveria ter um ID gerado.");
            assertNotNull(createdEntity.getCreatedAt(), "A entidade criada deveria ter a data de criação definida.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testUpdateAsync(){
        try {
            E entity = createEntity();
            E createdEntity = repository.create(entity, repositoryAuth);

            E updatedEntity = repository.updateAsync(createdEntity, repositoryAuth).join();

            assertNotNull(updatedEntity.getId(), "O ID da entidade atualizada não deve ser nulo.");
            assertNotEquals(createdEntity.getUpdatedAt(), updatedEntity.getUpdatedAt(), "A data de atualização deveria ser alterada.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}