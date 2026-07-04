package com.git.spring_boot_ddd_template._shared.infrastructure.mapper;

import org.mapstruct.*;

import com.git.spring_boot_ddd_template._shared.domain.GenericClass;
import com.git.spring_boot_ddd_template._shared.infrastructure.GenericEntity;

import java.util.List;

import static java.util.stream.Collectors.toList;

public interface GenericBusinessMapper<E extends GenericClass, S extends GenericEntity> {
    E toEntity(S schema);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "whitelabelId", ignore = true)
    S toSchema(E entity);

    default List<E> toEntityList(List<S> schemaList) {
        if (schemaList == null) {
            return null;
        }
        return schemaList.stream()
                .map(this::toEntity)
                .collect(toList());
    }

    default List<S> toSchemaList(List<E> entityList) {
        if (entityList == null) {
            return null;
        }
        return entityList.stream()
                .map(this::toSchema)
                .collect(toList());
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "whitelabelId", ignore = true)
    S toSchemaForCreate(E entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "deletedAt", ignore = true),
            @Mapping(target = "deleted", ignore = true),
            @Mapping(target = "userId", ignore = true),
            @Mapping(target = "whitelabelId", ignore = true)
    })
    void updateEntityFromDomain(@MappingTarget S entity, E domain);
}