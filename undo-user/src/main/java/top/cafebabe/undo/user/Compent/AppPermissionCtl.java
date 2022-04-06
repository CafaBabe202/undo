package top.cafebabe.undo.user.Compent;

import org.springframework.stereotype.Repository;

/**
 * @author cafababe
 */
@Repository
public class AppPermissionCtl {
    public boolean verifyPermission(String app, Permission permission) {
        return true;
    }
}
