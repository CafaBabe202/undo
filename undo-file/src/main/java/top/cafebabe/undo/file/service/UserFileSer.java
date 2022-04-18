package top.cafebabe.undo.file.service;

import org.springframework.stereotype.Service;
import top.cafebabe.fileManager.manger.TempFileManager;
import top.cafebabe.undo.file.bean.UserFile;
import top.cafebabe.undo.file.dao.UserFileMapper;
import top.cafebabe.undo.file.util.FileIdUtil;

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
        UserFile file = new UserFile(FileIdUtil.createId(userID, md5), userID, name, size, md5);
        return this.mapper.add(file) == 1;
    }

    public boolean delete(int userId, String id) {
        UserFile file = this.mapper.getFile(userId, id);
        return file != null && this.mapper.delete(userId, id) == 1 && this.tempFileManager.remove(file.getMd5());
    }

    public boolean changePrivate(int userId, String id) {
        return this.mapper.changePrivate(userId, id) == 1;
    }

    public boolean rename(int userId, String id, String newVal) {
        return this.mapper.rename(userId, id, newVal) == 1;
    }

    public UserFile getFile(String id) {
        return this.mapper.getOneFile(id);
    }

    public List<UserFile> getAllFile(int userId) {
        return this.mapper.getAllFile(userId);
    }

}
