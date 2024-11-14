package br.com.mythkrouz.MK.entities.enums;

public enum Roles {
    USER("ROLE_USER");

    private final String roleName;

    Roles(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
