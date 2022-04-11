package top.cafebabe.undo.article.util;

import top.cafebabe.undo.article.bean.Article;
import top.cafebabe.undo.article.bean.Clazz;
import top.cafebabe.undo.article.bean.Content;
import top.cafebabe.undo.article.bean.form.AddClazzForm;
import top.cafebabe.undo.article.bean.form.EditArticleForm;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

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

        article.setId(form.getId());
        article.setTitle(form.getTitle());
        article.setSummary(form.getSummary());
        article.setPrivate(form.isPrivate());
        article.setClazzId(form.getClazzId());

        return article;
    }

    public static Map<String, String> showArticle(Article article, Content content) {
        Map<String, String> res = objectToMap(article);
        res.remove("recordsId");
        res.remove("isPrivate");
        res.put("content", content.getContent());
        return res;
    }

    private static Map<String, String> objectToMap(Object obj) {
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
