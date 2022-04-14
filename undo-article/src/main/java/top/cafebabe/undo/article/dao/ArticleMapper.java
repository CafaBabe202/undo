package top.cafebabe.undo.article.dao;

import org.apache.ibatis.annotations.Mapper;
import top.cafebabe.undo.article.bean.Article;
import top.cafebabe.undo.article.bean.UserRank;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cafababe
 */
@Mapper
public interface ArticleMapper {
    int add(Article article);

    int deleteById(int articleId);

    int like(int articleId);

    int visit(int articleId);

    int update(Article article);

    int changePrivate(int userId, int articleId);

    HashMap<String, Long> getStatistics(int userId, int clazzId);

    int getArticleAuthor(int articleId);

    Article getArticleById(int articleId, boolean isPrivate);

    List<Article> getArticleByUser(int userId, boolean isPrivate);

    List<Article> getArticleByClazzId(int userId, int clazzId, boolean isPrivate);

    List<Article> getArticleByTitleLike(String likeTitle, int from, int size, boolean isPrivate);

    List<UserRank> getUserLikeTop();

    List<Article> getArticleVisitTop();
}
