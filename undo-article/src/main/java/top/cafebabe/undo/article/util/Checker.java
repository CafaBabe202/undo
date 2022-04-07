package top.cafebabe.undo.article.util;

import top.cafebabe.undo.article.bean.AppConfig;
import top.cafebabe.undo.article.form.AddClazzForm;

/**
 * @author cafababe
 */
public class Checker {
    public static boolean check(AddClazzForm form) {
        if (null == form || null == form.getName()) return false;
        return form.getName().length() <= AppConfig.CLAZZ_NAME_LEN;
    }
}
