package com.github.ayvazj.rokontrol;


public enum RokuKey {
    HOME("home"),
    REV("rev"),
    FWD("fwd"),
    SELECT("select"),
    LEFT("left"),
    RIGHT("right"),
    DOWN("down"),
    UP("up"),
    BACK("back"),
    INSTANTREPLAY("instantreplay"),
    INFO("info"),
    BACKSPACE("backspace"),
    SEARCH("search"),
    ENTER("enter");

    public final String endpoint;

    RokuKey(String endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public String toString() {
        return this.endpoint;
    }

    public static RokuKey getValueOf(String value) {
        for (RokuKey v : values()) {
            if (v.endpoint.equalsIgnoreCase(value)) {
                return v;
            }
        }
        throw new IllegalArgumentException();
    }
}
