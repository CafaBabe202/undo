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
import top.cafebabe.fileManager.utils.DocumentUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cafababe
 */
public class MongodbMemory implements Memory {

    private final String url;
    private final int port;
    private final String username;
    private final String password;
    private final String databaseName;

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
        MongoDatabase database = client.getDatabase(this.databaseName);
        this.file = database.getCollection("file");
        this.block = database.getCollection("block");
    }

    @Override
    public boolean addBlock(Block block) throws ExistException {
        if (this.existBlock(block.getMd5()))
            throw new ExistException(block.getMd5());

        block.setCount(1L);
        this.block.insertOne(DocumentUtils.toDocument(block));
        return true;
    }

    @Override
    public boolean deleteBlock(String md5) {
        return this.block.deleteOne(Filters.eq("_id", md5)).getDeletedCount() == 1;
    }

    @Override
    public Block getBlock(String md5) {
        MongoCursor<Document> blockByMd5 = this.findBlockByMd5(md5);
        return blockByMd5.hasNext() ? DocumentUtils.toBlock(blockByMd5.next()) : null;
    }

    @Override
    public boolean existBlock(String md5) {
        return this.findBlockByMd5(md5).hasNext();
    }

    @Override
    public boolean addFile(BlockFile file) throws ExistException {
        if (this.existFile(file.getMd5()))
            throw new ExistException(file.getMd5());

        file.setCount(1L);
        this.file.insertOne(DocumentUtils.toDocument(file));
        return true;
    }

    @Override
    public boolean deleteFile(String md5) {
        if (!this.existFile(md5))
            return true;

        return this.file.deleteOne(Filters.eq("_id", md5)).getDeletedCount() == 1;
    }

    @Override
    public BlockFile getFile(String md5) {
        MongoCursor<Document> fileByMd5 = this.findFileByMd5(md5);
        return fileByMd5.hasNext() ? DocumentUtils.toBlockFile(fileByMd5.next()) : null;
    }

    @Override
    public boolean existFile(String md5) {
        return this.findFileByMd5(md5).hasNext();
    }

    @Override
    public boolean setBlockCount(String md5, long count) {
        this.block.updateOne(Filters.eq("_id", md5), new Document("$set", new Document("count", count))).getModifiedCount();
        return this.getBlockCount(md5) == count;
    }

    @Override
    public long getBlockCount(String md5) {
        MongoCursor<Document> it = this.findBlockByMd5(md5);
        return it.hasNext() ? it.next().getLong("count") : 0;
    }

    @Override
    public boolean setFileCount(String md5, long count) {
        return this.file.updateOne(Filters.eq("_id", md5), new Document("$set", new Document("count", count))).getModifiedCount() == 1;
    }

    @Override
    public long getFileCount(String md5) {
        MongoCursor<Document> fileByMd5 = this.findFileByMd5(md5);
        return fileByMd5.hasNext() ? fileByMd5.next().getLong("count") : 0;
    }

    private MongoCursor<Document> findBlockByMd5(String md5) {
        return this.block.find(Filters.eq("_id", md5)).iterator();
    }

    private MongoCursor<Document> findFileByMd5(String md5) {
        return this.file.find(Filters.eq("_id", md5)).iterator();
    }

    public void list() {
        System.out.println("块：");
        for (Document document : this.block.find()) {
            System.out.println("\t" + DocumentUtils.toBlock(document));
        }
        System.out.println("\n\n文档：");
        for (Document document : this.file.find()) {
            System.out.println("\t" + DocumentUtils.toBlockFile(document));
        }
    }

}
