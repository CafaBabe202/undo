package top.cafebabe.undo.article.dao;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author cafababe
 */
@Mapper
public interface GoodMapper {
    int good(int userId, int articleId);

    int isGood(int userId, int articleId);
}
