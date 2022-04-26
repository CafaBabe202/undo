package top.cafebabe.undo.article.dao;

import org.apache.ibatis.annotations.Mapper;
import top.cafebabe.undo.article.bean.Say;

import java.util.List;

/**
 * @author cafababe
 */
@Mapper
public interface SayMapper {
    List<Say> getAllSay(int articleId);

    int say(Say say);
}
