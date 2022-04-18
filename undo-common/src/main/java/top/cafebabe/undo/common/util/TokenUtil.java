package top.cafebabe.undo.common.util;

import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

import java.nio.charset.StandardCharsets;

/**
 * @author cafababe
 * 用于用户登录 Token 的生成与校验
 */
public class TokenUtil {

    private static final String PREFIX = "CAFBABE";
    private static final String SEPARATOR = ":::";

    /**
     * 用来生成一个用户登录的 Token。Token 使用对称加密算法对用户的 id，ip，登录时间等信息进行加密。
     *
     * @param id 用户的 id。
     * @param ip 用户登录的 ip。
     * @return 生成的 Token。
     */
    public static String createLoginToken(int id, String ip, String key) {
        SymmetricCrypto des = new SymmetricCrypto(SymmetricAlgorithm.AES, key.getBytes(StandardCharsets.UTF_8));
        return des.encryptHex(PREFIX + SEPARATOR + id + SEPARATOR + ip + SEPARATOR + CurrentUtil.now());
    }

    /**
     * 验证 Token 是否合法。
     *
     * @param token token
     * @param key   生成该 Token 时的密钥。
     * @return 是否是合法的 Token。
     */
    public static boolean checkLoginToken(String token, String key) {
        String content = decode(token, key);
        if ("".equals(content)) return false;
        return checkLoginToken(content.split(SEPARATOR));
    }

    /**
     * 获取该 Token 的用户 id。
     *
     * @param token Token。
     * @param key   密钥。
     * @return 用户 id，如果 token 或者密钥出现问题，将返回 -1。
     */
    public static int getLoginTokenId(String token, String key) {
        String content = decode(token, key);
        if ("".equals(content)) return -1;

        String[] split = content.split(SEPARATOR);
        return checkLoginToken(split) ? Integer.parseInt(split[1]) : -1;
    }

    /**
     * 获取 Token 的 IP。
     *
     * @param token Token。
     * @param key   密钥。
     * @return 返回 Token 的 ip，如果 token 或者密钥出现问题，将返回 null。
     */
    public static String getLoginTokenIp(String token, String key) {
        String content = decode(token, key);
        if ("".equals(content)) return null;

        String[] split = content.split(SEPARATOR);
        return checkLoginToken(split) ? split[2] : null;
    }

    /**
     * 获取 Token 的签发时间。
     *
     * @param token Token。
     * @param key   密钥。
     * @return 签发时间，如果 token 或者密钥出现问题，将返回 -1。
     */
    public static long getLoginTokenTimeStamp(String token, String key) {
        String content = decode(token, key);
        if ("".equals(content)) return -1;
        String[] split = content.split(SEPARATOR);
        return checkLoginToken(split) ? Long.parseLong(split[3]) : -1;
    }

    private static String decode(String token, String key) {
        try {
            return (new SymmetricCrypto(SymmetricAlgorithm.AES, key.getBytes(StandardCharsets.UTF_8))).decryptStr(token);
        } catch (Exception e) {
            return "";
        }
    }

    private static boolean checkLoginToken(String[] content) {
        if (content.length != 4) return false;
        return content[0].equals(PREFIX) && Integer.parseInt(content[1]) > 0 && StringUtil.isIpv4(content[2]) && Long.parseLong(content[3]) <= CurrentUtil.now();
    }
}
