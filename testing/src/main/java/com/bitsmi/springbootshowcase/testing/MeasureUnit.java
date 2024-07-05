package com.bitsmi.springbootshowcase.testing;

public enum MeasureUnit {

    KILOMETER(true),
    METER(true),
    CENTIMETER(true),
    MILE(false),
    YARD(false),
    INCH(false);

    private final boolean decimalMetricSystem;

    MeasureUnit(boolean decimalMetricSystem)
    {
        this.decimalMetricSystem = decimalMetricSystem;
    }

    public boolean isDecimalMetricSystem()
    {
        return decimalMetricSystem;
    }
}
