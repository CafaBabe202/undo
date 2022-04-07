package top.cafebabe.undo.article.service;

import org.springframework.stereotype.Service;
import top.cafebabe.undo.article.bean.Clazz;
import top.cafebabe.undo.article.dao.ClazzMapper;

import java.util.List;

/**
 * @author cafababe
 */
@Service
public class ClazzService {

    final ClazzMapper clazzMapper;

    public ClazzService(ClazzMapper clazzMapper) {
        this.clazzMapper = clazzMapper;
    }

    public boolean addClazz(Clazz clazz) {
        return this.clazzMapper.add(clazz) == 1;
    }

    public List<Clazz> getAllClazz(int userId) {
        return this.clazzMapper.getClazzByUserId(userId);
    }
}
