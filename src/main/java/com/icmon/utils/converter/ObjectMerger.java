package com.icmon.utils.converter;

import java.lang.reflect.Field;

import com.icmon.exception.SystemGlobalException;

public class ObjectMerger {

    /**
     * Atualiza os campos de 'target' com os valores de 'source' para todos os campos
     * que possuam o mesmo nome. Porém, caso algum campo em 'source' seja null,
     * esse campo não será copiado para 'target'.
     *
     * @param source objeto de origem (valores a serem copiados)
     * @param target objeto de destino (que receberá os valores)
     * @param <A>    tipo do objeto de origem
     * @param <B>    tipo do objeto de destino
     */
    public static <A, B> B mergeObjects(A source, B target) throws SystemGlobalException {
        if (source == null || target == null) {
            return target;
        }

        Class<?> sourceClass = source.getClass();
        Class<?> targetClass = target.getClass();

        Field[] sourceFields = sourceClass.getDeclaredFields();

        for (Field sourceField : sourceFields) {
            try {
                sourceField.setAccessible(true);
                String fieldName = sourceField.getName();

                Field targetField;
                try {
                    targetField = targetClass.getDeclaredField(fieldName);
                } catch (NoSuchFieldException e) {
                    continue;
                }

                targetField.setAccessible(true);
                Object value = sourceField.get(source);

                if (value != null
                        && targetField.getType().isAssignableFrom(sourceField.getType())) {
                    targetField.set(target, value);
                }
            } catch (IllegalAccessException e) {
                throw new SystemGlobalException("Something went wrong while merging objects in " + sourceClass.getName(), e);
            }
        }

        return target;
    }

    private static boolean isAssignable(Class<?> targetType, Class<?> sourceType) {
        return targetType.isAssignableFrom(sourceType);
    }
}
