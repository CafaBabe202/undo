package top.cafebabe.undo.article.dao;

import org.apache.ibatis.annotations.Mapper;
import top.cafebabe.undo.article.bean.Article;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author cafababe
 */
@Mapper
public interface ArticleMapper {
    int add(Article article);

    int deleteById(int articleId);

    int like(int articleId);

    int visit(int articleId);

    int setClazz(int articleId, int clazzId);

    int setUpdateTime(int articleId, Timestamp updateTime);

    int setPrivate(int articleId, boolean isPrivate);

    int setSummary(int articleId, String summary);

    int getArticleAuthor(int articleId);

    Article getArticleById(int articleId, boolean isPublic);

    List<Article> getArticleByUser(int userId, boolean isPublic);

    List<Article> getArticleByClazzId(int userId, int clazzId, boolean isPublic);

    List<Article> getArticleByTitleLike(String likeTitle, boolean isPublic);
}
