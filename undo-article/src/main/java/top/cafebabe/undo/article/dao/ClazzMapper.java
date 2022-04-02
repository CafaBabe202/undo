package top.cafebabe.undo.article.dao;

import org.apache.ibatis.annotations.Mapper;
import top.cafebabe.undo.article.bean.Clazz;

import java.util.List;

/**
 * @author cafababe
 */
@Mapper
public interface ClazzMapper {
    int add(Clazz clazz);

    int deleteById(int clazzId);

    int setName(int clazzId, String name);

    List<Clazz> getClazzByUserId(int userId);

    String getClazzName(int clazzId);
}
