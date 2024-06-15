package com.bitsmi.springbootshowcase.clients.fluentclient.common.request;

public interface Selector
{
    default Selector and(Selector other)
    {
        return new ComposedSelector(this, LogicalOperator.AND, other);
    }

    default Selector or(Selector other)
    {
        return new ComposedSelector(this, LogicalOperator.OR, other);
    }
}
