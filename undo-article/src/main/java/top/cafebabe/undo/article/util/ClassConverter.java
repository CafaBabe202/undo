package top.cafebabe.undo.article.util;

import top.cafebabe.undo.article.bean.Clazz;
import top.cafebabe.undo.article.form.AddClazzForm;

/**
 * @author cafababe
 * 类型转换的工具类。
 */
public class ClassConverter {
    public static Clazz toClazz(AddClazzForm form){
        Clazz clazz = new Clazz();
        clazz.setName(form.getName());
        return clazz;
    }
}
