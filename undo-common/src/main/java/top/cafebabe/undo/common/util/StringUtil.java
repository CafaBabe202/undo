package top.cafebabe.undo.common.util;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;

/**
 * @author cafababe
 * 各种字符串的基础工具。
 */
public class StringUtil {

    private static final Gson gson = new Gson();

    /**
     * 判断一个字符串是否是一个合法的 ipv4 地址。
     *
     * @param str 字符串。
     * @return 是否是 ipv4 地址。
     */
    public static boolean isIpv4(String str) {
        String[] split = str.split("\\.");
        if (split.length != 4) return false;
        boolean res = true;
        for (int i = 0; i < 4; i++) {
            int integer = Integer.parseInt(split[i]);
            res &= (integer >= 0 && integer <= 255);
        }
        return res;
    }

    /**
     * 验证正则。
     *
     * @param regex 正则。
     * @param str   字符串。
     * @return 是否符合正则。
     */
    public static boolean isMatch(String regex, String str) {
        return ReUtil.isMatch(regex, str);
    }

    /**
     * 验证是否有空白字符。空白字符指不可见字符，并不是只有空格才叫空白字符。
     *
     * @param str 字符串。
     * @return 是否存在空白字符
     */
    public static boolean hasBlank(String str) {
        return StrUtil.hasBlank(str);
    }

    public static String toJson(Object o) {
        return gson.toJson(o);
    }

    public static <T> T pareJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }
}
