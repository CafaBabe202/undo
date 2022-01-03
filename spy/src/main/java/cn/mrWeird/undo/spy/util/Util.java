package cn.mrWeird.undo.spy.util;

import com.google.gson.Gson;

/**
 * 一个简单的工具类，方便使用。
 *
 * @author MrWeird
 */
public class Util {

    private static final Gson gson = new Gson();

    /**
     * 将对象转换成json。
     *
     * @param obj 需要转换的对象。
     * @return 返回通过Gson转换的结果。
     */
    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }
}
