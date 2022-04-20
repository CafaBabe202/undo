package top.cafebabe.undo.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author cafababe
 */
public class Md5Util {

    private MessageDigest messageDigest;

    public static String md5(byte[] data) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(data);
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest.digest())
                sb.append(String.format("%02X", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Md5Util() {
        try {
            this.messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public void update(byte[] data, int offset, int len) {
        this.messageDigest.update(data, offset, len);
    }

    public String design() {
        StringBuilder stringBuilder = new StringBuilder();
        byte[] digest = this.messageDigest.digest();
        for (byte b : digest)
            stringBuilder.append(String.format("%02x", b));
        return stringBuilder.toString();
    }
}
