package code.flatura.easyexpendit;

import code.flatura.easyexpendit.model.User;

import java.util.UUID;

public class SecurityUtil {
    private static UUID loggedUserId = UUID.fromString("0d9b7adf-7879-4c4d-915f-d7cb0419028a");
    private static User loggedUser;

    private SecurityUtil() {
    }

    // Initialized in UserService constructor


    public static UUID getLoggedUserId() {
        return loggedUserId;
    }

    public static void setLoggedUserId(UUID loggedUserId) {
        SecurityUtil.loggedUserId = loggedUserId;
    }

    public static User getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(User loggedUser) {
        SecurityUtil.loggedUser = loggedUser;
    }
}
