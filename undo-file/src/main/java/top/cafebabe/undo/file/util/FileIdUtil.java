package top.cafebabe.undo.file.util;

import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

/**
 * @author cafababe
 */
public class FileIdUtil {

    private static final String KEY = "cafebabecafedade";
    private static final String SEPARATOR = ":";
    private static final SymmetricCrypto symmetricCrypto = new SymmetricCrypto(SymmetricAlgorithm.AES, KEY.getBytes());

    /**
     * 这里存在一个 Bug，统一人创建上次传同一个文件时将会出现相同的 ID 然后抛 SQL 异常。
     * 这是梦见的。
     * 这是梦见的。
     * 这是梦见的。
     *
     * @param userId 用户 ID。
     * @param md5    文件 MD5。
     * @return 创建的文件 ID。
     */
    public static String createId(int userId, String md5) {
        return symmetricCrypto.encryptHex(userId + SEPARATOR + md5);
    }

    public static int getUserId(String id) {
        if (check(id))
            return -1;
        return Integer.parseInt(symmetricCrypto.decryptStr(id).split(SEPARATOR)[0]);
    }

    public static String getMd5(String id) {
        if (!check(id))
            return "";
        return symmetricCrypto.decryptStr(id).split(SEPARATOR)[1];
    }

    public static boolean check(String id) {
        return symmetricCrypto.decryptStr(id).split(SEPARATOR).length == 2;
    }
}
