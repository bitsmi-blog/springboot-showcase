package com.bitsmi.springshowcase.sampleservice.client.common.request;

public interface ISelector
{
    default ISelector and(ISelector other)
    {
        return new ComposedSelector(this, LogicalOperator.AND, other);
    }

    default ISelector or(ISelector other)
    {
        return new ComposedSelector(this, LogicalOperator.OR, other);
    }
}
