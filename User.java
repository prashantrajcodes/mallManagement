package mallmanagement2;

class User {
    String role;

    public User(String role) {
        this.role = role;
    }

    public boolean isAdmin() {
        return role.equalsIgnoreCase("admin");
    }
}