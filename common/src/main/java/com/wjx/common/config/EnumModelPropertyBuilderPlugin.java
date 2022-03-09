package com.wjx.common.config;

import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.google.common.base.Optional;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import springfox.documentation.builders.ModelPropertyBuilder;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@SuppressWarnings(value = "all")
public class EnumModelPropertyBuilderPlugin implements ModelPropertyBuilderPlugin {

    @Override
    public void apply(ModelPropertyContext context) {
        Optional<BeanPropertyDefinition> optional = context.getBeanPropertyDefinition();
        if (!optional.isPresent()) {
            return;
        }

        if (optional.get() == null || optional.get().getField() == null) {
            return;
        }
        final Class<?> fieldType = optional.get().getField().getRawType();

        addDescForEnum(context, fieldType);
    }

    @Override
    public boolean supports(DocumentationType delimiter) {
        return true;
    }

    private void addDescForEnum(ModelPropertyContext context, Class<?> fieldType) {
        if (Enum.class.isAssignableFrom(fieldType)) {
            SwaggerDisplayEnum annotation = AnnotationUtils.findAnnotation(fieldType, SwaggerDisplayEnum.class);
            if (annotation != null) {
                String index = annotation.index();
                String name = annotation.name();

                Object[] enumConstants = fieldType.getEnumConstants();

                List<String> displayValues =
                        Arrays.stream(enumConstants)
                                .filter(Objects::nonNull)
                                .map(item -> {
                                    Class<?> currentClass = item.getClass();
                                    Object value = item;
                                    if (StringUtils.hasText(index)) {
                                        Field indexField = ReflectionUtils.findField(currentClass, index);
                                        ReflectionUtils.makeAccessible(indexField);
                                        value = ReflectionUtils.getField(indexField, item);
                                    }
                                    Object desc = item;
                                    if (StringUtils.hasText(name)) {
                                        Field descField = ReflectionUtils.findField(currentClass, name);
                                        ReflectionUtils.makeAccessible(descField);
                                        desc = ReflectionUtils.getField(descField, item);
                                    }
                                    return value + ":" + desc;

                                }).collect(Collectors.toList());


                ModelPropertyBuilder builder = context.getBuilder();
                Field descField = ReflectionUtils.findField(builder.getClass(), "description");
                ReflectionUtils.makeAccessible(descField);
                String joinText = ReflectionUtils.getField(descField, builder) + " (" + String.join("; ", displayValues) + ")";
                builder.description(joinText).type(context.getResolver().resolve(String.class));
            }
        }

    }
}
