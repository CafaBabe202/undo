package top.cafebabe.undo.file.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.cafebabe.fileManager.manger.TempFileManager;
import top.cafebabe.undo.file.bean.UserFile;
import top.cafebabe.undo.file.dao.UserFileMapper;

import java.util.List;

/**
 * @author cafababe
 */
@Service
public class UserFileSer {
    final UserFileMapper mapper;
    final TempFileManager tempFileManager;

    public UserFileSer(UserFileMapper mapper, TempFileManager tempFileManager) {
        this.mapper = mapper;
        this.tempFileManager = tempFileManager;
    }

    public boolean add(int userID, String name, String md5, long size) {
        UserFile file = new UserFile(userID, name, size, md5);
        return this.mapper.add(file) == 1;
    }

    public boolean delete(int userId, int id) {
        UserFile file = this.mapper.getFile(userId, id);
        return file != null && this.mapper.delete(userId, id) == 1 && this.tempFileManager.remove(file.getMd5());
    }

    public boolean changePrivate(int userId, int id) {
        return this.mapper.changePrivate(userId, id) == 1;
    }

    public boolean rename(int userId, int id, String newVal) {
        return this.mapper.rename(userId, id, newVal) == 1;
    }

    public List<UserFile> getAllFile(int userId) {
        return this.mapper.getAllFile(userId);
    }

}
