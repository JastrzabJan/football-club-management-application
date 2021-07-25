package pjatk.edu.pl.footballclubmanagementapplication.backend.data;

public class Role {

    private static final String PLAYER = "player";
    private static final String COACH = "coach";
    private static final String MANAGER = "manager";
    private static final String ADMIN = "admin";

    private Role() {

    }

    public static String[] getAllRoles() {
        return new String[]{PLAYER, COACH, ADMIN, MANAGER};
    }

}
