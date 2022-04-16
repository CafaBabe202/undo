package top.cafebabe.fileManager.manger.memory;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import top.cafebabe.fileManager.bean.Block;
import top.cafebabe.fileManager.bean.BlockFile;
import top.cafebabe.fileManager.exception.ExistException;
import top.cafebabe.fileManager.exception.NotFileException;
import top.cafebabe.fileManager.utils.DocumentUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * @author cafababe
 */
public class MongodbMemory implements Memory {

    private final String url;
    private final int port;
    private final String username;
    private final String password;
    private final String databaseName;

    protected MongoDatabase database;

    private MongoCollection<Document> file;
    private MongoCollection<Document> block;


    public MongodbMemory(String url, int port, String username, String password, String databaseName) {
        this.url = url;
        this.port = port;
        this.username = username;
        this.password = password;
        this.databaseName = databaseName;
        this.init();
    }

    private void init() {

        ServerAddress serverAddress = new ServerAddress(this.url, this.port);

        MongoCredential credential = MongoCredential.createScramSha1Credential(this.username, this.databaseName, this.password.toCharArray());
        List<MongoCredential> credentials = new ArrayList<>();
        credentials.add(credential);

        MongoClient client = new MongoClient(List.of(serverAddress), credentials);
        this.database = client.getDatabase(this.databaseName);
        this.file = database.getCollection("file");
        this.block = database.getCollection("block");
    }

    @Override
    public boolean addBlock(Block block) {
        if (existBlock(block.getMd5()))
            return incrBlock(block.getMd5());
        else
            return insertBlock(block);
    }

    @Override
    public boolean removeBlock(String md5) {
        if (getBlockCount(md5) == 1)
            return deleteBlock(md5);
        else
            return downBlock(md5);
    }

    @Override
    public Block getBlock(String md5) {
        MongoCursor<Document> blockByMd5 = this.findBlockByMd5(md5);
        return blockByMd5.hasNext() ? DocumentUtils.toBlock(blockByMd5.next()) : null;
    }

    @Override
    public boolean addFile(BlockFile blockFile) throws ExistException {
        if (this.existFile(blockFile.getMd5()))
            throw new ExistException("文件：" + blockFile.getMd5() + " 已经存在，如果想使计数 +1，请使用 addFile(String md5) 方法");
        return this.insertFile(blockFile);
    }

    /**
     * 使文件的计数 +1，这个方法会对文件包含的每个块的计数都 +1。
     *
     * @param md5 文件的 md5。
     * @return 是否添加成功。
     * @throws NotFileException 如果文件不存在，将会抛出该异常。
     */
    @Override
    public boolean incrFile(String md5) throws NotFileException {
        BlockFile file = this.getFile(md5);
        if (file == null)
            throw new NotFileException("文件：" + md5 + " 不存在");

        ListIterator<String> it = file.getContent().listIterator();
        while (it.hasNext()) {
            if (!this.incrBlock(it.next())) {
                it.previous();
                while (it.hasPrevious()) {
                    this.downBlock(it.previous());
                }
                return false;
            }
        }
        return this.setFileCount(md5, getFileCount(md5) + 1);
    }

    @Override
    public boolean removeFile(String md5) {
        if (getFileCount(md5) == 1)
            return deleteFile(md5);
        else
            return downFile(md5);
    }

    @Override
    public BlockFile getFile(String md5) {
        MongoCursor<Document> fileByMd5 = this.findFileByMd5(md5);
        if (fileByMd5.hasNext())
            return DocumentUtils.toBlockFile(fileByMd5.next());
        else
            return null;
    }

    @Override
    public boolean existFile(String md5) {
        return this.getFileCount(md5) != 0;
    }

    /**
     * 不支持软删除，这是一个空实现。
     */
    @Override
    public void gc() {
    }

    private boolean insertBlock(Block block) {
        block.setCount(1L);
        this.block.insertOne(DocumentUtils.toDocument(block));
        return true;
    }

    private boolean insertFile(BlockFile blockFile) {
        blockFile.setCount(1L);
        this.file.insertOne(DocumentUtils.toDocument(blockFile));
        return true;
    }

    private boolean existBlock(String md5) {
        return this.getBlockCount(md5) != 0;
    }

    private boolean incrBlock(String md5) {
        return setBlockCount(md5, getBlockCount(md5) + 1);
    }

    private boolean deleteBlock(String md5) {
        return this.block.deleteOne(Filters.eq("_id", md5)).getDeletedCount() == 1;
    }

    private boolean deleteFile(String md5) {
        return this.file.deleteOne(Filters.eq("_id", md5)).getDeletedCount() == 1;
    }

    private boolean downBlock(String md5) {
        return setBlockCount(md5, getBlockCount(md5) - 1);
    }

    private boolean downFile(String md5) {
        return setFileCount(md5, getFileCount(md5) - 1);
    }

    private long getBlockCount(String md5) {
        MongoCursor<Document> it = this.findBlockByMd5(md5);
        return it.hasNext() ? it.next().getLong("count") : 0;
    }

    private long getFileCount(String md5) {
        MongoCursor<Document> fileByMd5 = this.findFileByMd5(md5);
        return fileByMd5.hasNext() ? fileByMd5.next().getLong("count") : 0;
    }

    private boolean setBlockCount(String md5, long count) {
        return this.block.updateOne(Filters.eq("_id", md5), new Document("$set", new Document("count", count))).getModifiedCount() == 1;
    }

    private boolean setFileCount(String md5, long count) {
        return this.file.updateOne(Filters.eq("_id", md5), new Document("$set", new Document("count", count))).getModifiedCount() == 1;
    }

    private MongoCursor<Document> findBlockByMd5(String md5) {
        return this.block.find(Filters.eq("_id", md5)).iterator();
    }

    private MongoCursor<Document> findFileByMd5(String md5) {
        return this.file.find(Filters.eq("_id", md5)).iterator();
    }
}
