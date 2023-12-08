package com.bitsmi.springbootshowcase.api.content.request;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UniqueItemSchemaFieldsValidator implements ConstraintValidator<UniqueItemSchemaFields, CreateItemSchemaRequest>
{
    @Override
    public boolean isValid(CreateItemSchemaRequest request, ConstraintValidatorContext ctx)
    {
        HibernateConstraintValidatorContext hibernateValidatorCtx = ctx.unwrap( HibernateConstraintValidatorContext.class );

        Map<String, List<ItemSchemaField>> groupedFields = ObjectUtils.defaultIfNull(request.fields(), Collections.<ItemSchemaField>emptyList())
                .stream()
                .collect(Collectors.groupingBy(ItemSchemaField::name));

        String duplicatedFields = groupedFields.keySet().stream()
                .filter(key -> groupedFields.get(key).size()>1)
                .collect(Collectors.joining(", "));

        hibernateValidatorCtx.addMessageParameter("fields", duplicatedFields);

        return StringUtils.isBlank(duplicatedFields);
    }
}
