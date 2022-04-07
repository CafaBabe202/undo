package top.cafebabe.undo.user.compent;

import org.springframework.stereotype.Component;

/**
 * @author cafababe
 */
@Component
public class AppPermissionCtl {
    public boolean verifyPermission(String app, Permission permission) {
        return true;
    }
}
