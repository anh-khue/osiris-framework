package io.osiris.data.jpa.binding;

import io.osiris.data.common.annotation.Column;
import io.osiris.data.common.annotation.Generated;
import io.osiris.data.common.annotation.Id;
import io.osiris.data.common.annotation.Table;
import io.osiris.data.common.binding.DataBindings;
import io.osiris.data.jpa.Entity;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static io.osiris.data.common.binding.function.DataBindingHandler.fetchColumns;
import static io.osiris.data.common.binding.function.DataBindingHandler.fetchFields;

public class EntityDataBindings implements DataBindings {

    private final Class<? extends Entity> entityClass;

    EntityDataBindings(Class<? extends Entity> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public String table() {
        return entityClass.getAnnotation(Table.class).value();
    }

    @Override
    public List<String> idColumns() {
        Field[] fields = entityClass.getDeclaredFields();

        return Arrays.stream(fields)
                .filter(field -> field.getAnnotation(Id.class) != null)
                .map(field -> field.getAnnotation(Column.class).value())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> generatedColumns() {
        Field[] fields = entityClass.getDeclaredFields();

        return Arrays.stream(fields)
                .filter(field -> field.getAnnotation(Generated.class) != null)
                .map(field -> field.getAnnotation(Column.class).value())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> columns() {
        return fetchColumns(entityClass);
    }

    @Override
    public List<Field> fields() {
        return fetchFields(entityClass);
    }
}
