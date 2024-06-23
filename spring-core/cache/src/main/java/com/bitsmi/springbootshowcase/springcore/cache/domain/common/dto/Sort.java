package com.bitsmi.springbootshowcase.springcore.cache.domain.common.dto;

import lombok.Builder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public record Sort(List<Order> orders)
{
    public static final Sort UNSORTED = Sort.by(Collections.emptyList());

    public static Sort by(Order... orders)
    {
        Objects.requireNonNull(orders);

        return new Sort(Arrays.asList(orders));
    }

    public static Sort by(List<Order> orders)
    {
        Objects.requireNonNull(orders);

        return new Sort(Collections.unmodifiableList(orders));
    }

    public static Sort by(Direction direction, String... properties) {

        Objects.requireNonNull(direction);
        Objects.requireNonNull(properties);

        return Sort.by(Arrays.stream(properties)
                .map(property -> Order.builder().direction(direction).property(property).build())
                .collect(Collectors.toList()));
    }

    @Builder(toBuilder = true, builderClassName = "Builder")
    public record Order(
            Direction direction,
            String property,
            boolean ignoreCase,
            NullHandling nullHandling
    )
    {
        private static final Direction DEFAULT_DIRECTION = Direction.ASC;
        private static final boolean DEFAULT_IGNORE_CASE = false;
        private static final NullHandling DEFAULT_NULL_HANDLING = NullHandling.NATIVE;

        public static Order by(String property)
        {
            return Order.builder()
                    .direction(DEFAULT_DIRECTION)
                    .property(property)
                    .build();
        }

        public static Order asc(String property)
        {
            return Order.builder()
                    .direction(Direction.ASC)
                    .property(property)
                    .build();
        }

        public static Order desc(String property)
        {
            return Order.builder()
                    .direction(Direction.DESC)
                    .property(property)
                    .build();
        }

        public static class Builder
        {
            public Order build()
            {
                Objects.requireNonNull(direction);
                Objects.requireNonNull(property);

                return new Order(
                        direction,
                        property,
                        ignoreCase,
                        nullHandling!=null ? nullHandling : DEFAULT_NULL_HANDLING
                );
            }
        }
    }

    public enum Direction
    {
        ASC, DESC;

        public boolean isAscending()
        {
            return this.equals(ASC);
        }

        public boolean isDescending()
        {
            return this.equals(DESC);
        }
    }

    public enum NullHandling
    {
        NATIVE,
        NULLS_FIRST,
        NULLS_LAST
    }
}
