package be.icc.Pid_Reservations_2024.Enums;


public enum Roles {

    ADMIN("admin"),
    MEMBER("member");

    private String role;

    Roles(String role) {
        this.role = role;
    }

    public String getValue() {
        return role;
    }

}
