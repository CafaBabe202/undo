package top.cafebabe.undo.user.util;

import top.cafebabe.undo.common.bean.SysUser;
import top.cafebabe.undo.user.form.RegisterForm;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cafababe
 * 类型转换的工具类。
 */
public class ClassConverter {

    /**
     * 将前端传过来的 RegisterForm 转换成 SysUser。<b>注意：</b>这里并不进行数据的合法性校验，只是单纯的转换。
     *
     * @param form 表单对象。
     * @return SysUser 对象。
     */
    public static SysUser toSysUser(RegisterForm form) {
        SysUser user = new SysUser();
        user.setUsername(form.getUsername());
        user.setPassword(form.getPassword());
        user.setEmail(form.getEmail());
        return user;
    }

    /**
     * 将用户转换成 Map 以方便向前端回传数据。
     *
     * @param obj 数据类对象。
     * @return Map
     */
    public static Map<String, String> objectToMap(Object obj) {
        Map<String, String> res = new HashMap<>();
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            String name = field.getName();
            try {
                res.put(name, field.get(obj) + "");
            } catch (IllegalAccessException e) {
                res.put(name, "null");
            }
        }
        return res;
    }
}
