package top.cafebabe.undo.article.dao;

import org.apache.ibatis.annotations.Mapper;
import top.cafebabe.undo.article.bean.Clazz;

import java.util.List;
import java.util.Map;

/**
 * @author cafababe
 */
@Mapper
public interface ClazzMapper {
    int add(Clazz clazz);

    int deleteById(int userId, int clazzId);

    int setName(int userId, int clazzId, String name);

    List<Clazz> getClazzByUserId(int userId);

    String getClazzName(int clazzId);
}
