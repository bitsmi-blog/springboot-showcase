package com.bitsmi.springbootshowcase.springcore.validation.domain.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.RecordComponent;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UniqueValuesValidator implements ConstraintValidator<UniqueValues, Collection<?>>
{
    private UniqueValues constraintAnnotation;

    @Override
    public void initialize(UniqueValues constraintAnnotation)
    {
        this.constraintAnnotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Collection<?> values, ConstraintValidatorContext context)
    {
        if (values==null || values.isEmpty()) {
            return true;
        }

        HibernateConstraintValidatorContext hibernateValidatorCtx = context.unwrap( HibernateConstraintValidatorContext.class );

        String field = !UniqueValues.NOT_DEFINED_VALUE.equals(constraintAnnotation.field()) ? constraintAnnotation.field() : null;
        Map<Object, List<Object>> groupedValues = new HashMap<>();
        Collection<?> nullSafeValues =  ObjectUtils.defaultIfNull(values, Collections.emptyList());
        if (field!=null) {
            groupedValues.putAll(nullSafeValues.stream()
                    .collect(Collectors.groupingBy(fieldExtractor(field))));
        }
        else {
            groupedValues.putAll(nullSafeValues.stream()
                    .collect(Collectors.groupingBy(Function.identity())));
        }

        List<String> duplicatedValues = groupedValues.keySet().stream()
                .filter(key -> groupedValues.get(key).size()>1)
                .map(Object::toString)
                .toList();

        hibernateValidatorCtx.addMessageParameter("values", String.join(", ", duplicatedValues));

        return duplicatedValues.isEmpty();
    }

    private Function<Object, Object> fieldExtractor(String field)
    {
        return input -> {
            try {
                if (input.getClass().getRecordComponents()!=null) {
                    RecordComponent recordComponent = Arrays.stream(input.getClass().getRecordComponents())
                            .filter(component -> field.equals(component.getName()))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Field (%s) not found".formatted(field)));

                    return recordComponent.getAccessor().invoke(input);
                }
                else {
                    return PropertyUtils.getSimpleProperty(input, field);
                }
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
