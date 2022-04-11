package top.cafebabe.undo.article.util;

import top.cafebabe.undo.article.bean.AppConfig;
import top.cafebabe.undo.article.bean.form.AddClazzForm;
import top.cafebabe.undo.article.bean.form.EditArticleForm;
import top.cafebabe.undo.article.bean.form.RenameClazzForm;

/**
 * @author cafababe
 */
public class Checker {
    public static boolean check(AddClazzForm form) {
        if (null == form || null == form.getName()) return false;
        return form.getName().length() <= AppConfig.CLAZZ_NAME_LEN;
    }

    public static boolean check(EditArticleForm form) {
        return true;
    }

    public static boolean check(RenameClazzForm form) {
        if (null == form || null == form.getNewVal()) return false;
        return form.getNewVal().length() <= AppConfig.CLAZZ_NAME_LEN;
    }
}
