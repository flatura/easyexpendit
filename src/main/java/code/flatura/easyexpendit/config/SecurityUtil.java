package code.flatura.easyexpendit.config;

import code.flatura.easyexpendit.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public class SecurityUtil {
    private static User loggedUser;
    private static int changeCounter;

    private SecurityUtil() {
    }

    public static CustomUserSecurityPrincipal getLoggedUserPrincipal() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (CustomUserSecurityPrincipal) auth.getPrincipal();
    }

    public static User getLoggedUser() {
        return loggedUser;
    }

    public static int getChangeCounter() {
        return changeCounter;
    }

    public static void setLoggedUser(User loggedUser) {
        SecurityUtil.loggedUser = loggedUser;
        changeCounter++;
    }

    public static UUID getLoggedUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserSecurityPrincipal currentUser = (CustomUserSecurityPrincipal) auth.getPrincipal();
        if (currentUser != null) {
            return currentUser.getUserId();
        }
        return null;
    }
}
