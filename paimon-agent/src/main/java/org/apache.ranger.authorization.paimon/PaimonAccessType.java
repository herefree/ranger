package org.apache.ranger.authorization.paimon;

public enum PaimonAccessType {
    INSERT("insert"),
    ALTER("alter"),
    DROP("drop"),
    SELECT("select"),
    ALL("all");

    private String name;

    PaimonAccessType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
