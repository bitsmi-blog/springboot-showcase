package com.bitsmi.springbootshowcase.domain.content.model;

public enum ItemStatus
{
    // Not owned
    WANTED,
    // Not owned. Once was wanted
    DISCARDED,
    // Owned but not in use
    READY,
    IN_USE,
    // Still in use but ready to retire
    DEPRECATED,
    // Not in use. Stored
    ARCHIVED,
    // Not owned anymore
    RETIRED;
}
