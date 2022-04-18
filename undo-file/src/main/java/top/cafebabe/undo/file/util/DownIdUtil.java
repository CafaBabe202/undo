package top.cafebabe.undo.file.util;

import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

/**
 * @author cafababe
 */
public class DownIdUtil {
    private static final String KEY = "cafebabecafedade";
    private static final String SEPARATOR = ":@:";
    private static final Long TIMEOUT = 60L * 1000L;
    private static final SymmetricCrypto symmetricCrypto = new SymmetricCrypto(SymmetricAlgorithm.AES, KEY.getBytes());

    public static String createId(String fileId) {
        return symmetricCrypto.encryptHex(NowUtil.now() + SEPARATOR + fileId);
    }

    public static boolean check(String id) {
        String[] split = symmetricCrypto.decryptStr(id).split(SEPARATOR);
        return split.length == 2 && NowUtil.now() - Long.parseLong(split[0]) <= TIMEOUT && FileIdUtil.check(split[1]);

    }

    public static String getFileId(String id) {
        try {
            return symmetricCrypto.decryptStr(id).split(SEPARATOR)[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            return "";
        }
    }
}
