package com.github.ayvazj.rokontrol;

/**
 * Types of Apps available.
 */
public enum RokuAppType {
    APPL("appl"),
    MENU("menu");

    private String value;

    RokuAppType(String str) {
        this.value = str;
    }

    @Override
    public String toString() {
        return this.value;

    }

    public static RokuAppType fromString(String value) {
        for (RokuAppType v : values())
            if (v.value.equalsIgnoreCase(value)) return v;
        throw new IllegalArgumentException();
    }
}
