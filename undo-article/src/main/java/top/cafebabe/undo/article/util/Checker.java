package top.cafebabe.undo.article.util;

import top.cafebabe.undo.article.bean.AppConfig;
import top.cafebabe.undo.article.bean.form.AddClazzForm;
import top.cafebabe.undo.article.bean.form.AdminLoginForm;
import top.cafebabe.undo.article.bean.form.EditArticleForm;
import top.cafebabe.undo.article.bean.form.RenameClazzForm;

/**
 * 检查各种标签是否合法。
 *
 * @author cafababe
 */
public class Checker {
    public static boolean check(AddClazzForm form) {
        if (null == form || null == form.getName()) return false;
        return form.getName().length() <= AppConfig.CLAZZ_NAME_LEN;
    }

    public static boolean check(AdminLoginForm form) {
        return "ZH".equals(form.getId()) && "Admin@123".equals(form.getPassword());
    }

    public static boolean check(EditArticleForm form) {
        boolean res = form.getTitle().length() < 32
                && form.getTitle().length() > 0
                && form.getSummary().length() < 256
                && form.getSummary().length() > 0
                && form.getClazzId() > 0;
        if (form.getId() != -1)
            res = res
                    && form.getUpdateSummary().length() < 255
                    && form.getUpdateSummary().length() > 0;
        return res;
    }

    public static boolean check(RenameClazzForm form) {
        if (null == form || null == form.getNewVal()) return false;
        return form.getNewVal().length() <= AppConfig.CLAZZ_NAME_LEN;
    }
}
