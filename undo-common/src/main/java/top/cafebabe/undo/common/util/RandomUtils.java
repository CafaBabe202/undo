package top.cafebabe.undo.common.util;

import java.util.Random;
import java.util.UUID;

/**
 * @author cafababe
 */
public class RandomUtils {

    private static final Random random = new Random();

    public static String UUID() {
        return UUID.randomUUID().toString();
    }

    public static String registerCode() {
        return toString(toByteArr(random.nextInt()));
    }

    private static byte[] toByteArr(int i) {
        byte[] res = new byte[4];
        for (int j = 3; j >= 0; j--)
            res[j] = (byte) (i >> (3 - j) * 8 & 0xFF);
        return res;
    }

    private static byte[] toByteArr(long i) {
        byte[] res = new byte[8];
        for (int j = 7; j >= 0; j--)
            res[j] = (byte) (i >> (7 - j) * 8 & 0xFF);
        return res;
    }

    private static String toString(byte[] arr) {
        StringBuilder sb = new StringBuilder();
        for (byte b : arr)
            sb.append(String.format("%02X", b));
        return sb.toString();
    }
}
