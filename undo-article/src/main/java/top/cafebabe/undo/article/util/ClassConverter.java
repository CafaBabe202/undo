package top.cafebabe.undo.article.util;

import top.cafebabe.undo.article.bean.Article;
import top.cafebabe.undo.article.bean.Clazz;
import top.cafebabe.undo.article.form.AddClazzForm;
import top.cafebabe.undo.article.form.EditArticleForm;

/**
 * @author cafababe
 * 类型转换的工具类。
 */
public class ClassConverter {
    public static Clazz toClazz(AddClazzForm form) {
        Clazz clazz = new Clazz();
        clazz.setName(form.getName());
        return clazz;
    }

    public static Article toArticle(EditArticleForm form) {
        Article article = new Article();

        article.setTitle(form.getTitle());
        article.setSummary(form.getSummary());
        article.setPrivate(form.isPrivate());
        article.setClazzId(form.getClazzId());

        return article;
    }
}
