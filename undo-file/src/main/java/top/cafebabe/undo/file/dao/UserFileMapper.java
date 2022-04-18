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

    int delete(int userId, String id);

    int review(String id);

    int changePrivate(int userId,String id);

    int rename(int userId, String id, String name);

    UserFile getFile(int userId, String id);

    UserFile getOneFile(String id);

    UserFile getOneNotReview();

    List<UserFile> getAllFile(int userId);

    long getNotReviewNumber();

}
