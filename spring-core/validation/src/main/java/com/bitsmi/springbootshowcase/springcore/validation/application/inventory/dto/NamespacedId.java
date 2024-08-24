package com.bitsmi.springbootshowcase.springcore.validation.application.inventory.dto;

import java.util.Objects;

public record NamespacedId(
        String namespace,
        String id
) {
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        if (namespace != null) {
            builder.append(namespace)
                    .append(":");
        }
        builder.append(id);

        return builder.toString();
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(namespace, id);
    }

    @Override
    public boolean equals(Object o)
    {
        return this == o
                || o instanceof NamespacedId other
                && Objects.equals(namespace, other.namespace)
                && Objects.equals(id, other.id);
    }
}
