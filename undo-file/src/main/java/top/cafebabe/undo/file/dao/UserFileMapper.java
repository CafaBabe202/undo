package top.cafebabe.undo.file.dao;

import org.apache.ibatis.annotations.Mapper;
import top.cafebabe.undo.file.bean.UserFile;

import java.util.List;

/**
 * @author cafababe
 */
@Mapper
public interface UserFileMapper {
    int add(UserFile userFile);

    int delete(int userId, int id);

    int review(int id);

    int changePrivate(int userId,int id);

    int rename(int userId, int id, String name);

    UserFile getFile(int userId, int id);

    UserFile getOneNotReview();

    List<UserFile> getAllFile(int userId);

    long getNotReviewNumber();

}
