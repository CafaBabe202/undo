package util;

import org.junit.Test;
import top.cafebabe.undo.common.util.TokenUtil;

/**
 * @author cafababe
 */
public class TokenUtilTest {
    @Test
    public void createToke() {
        String key = "cafebabecafebabe";
        String conte = TokenUtil.createLoginToken(10, "192.168.2.202", key);
        System.out.println(conte);
        boolean c = TokenUtil.checkLoginToken(conte, key);
        int id = TokenUtil.getLoginTokenId(conte, key);
        String ip = TokenUtil.getLoginTokenIp(conte, key);
        System.out.println(c + "\n" + id + "\n" + ip);
    }
}
