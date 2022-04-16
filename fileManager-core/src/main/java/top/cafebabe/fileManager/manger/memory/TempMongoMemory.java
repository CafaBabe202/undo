package top.cafebabe.fileManager.manger.memory;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import top.cafebabe.fileManager.bean.TempBlockFile;
import top.cafebabe.fileManager.utils.DocumentUtils;

/**
 * @author cafababe
 */
public class TempMongoMemory extends MongodbMemory {

    private final MongoCollection<Document> tempFile;

    public TempMongoMemory(String url, int port, String username, String password, String databaseName) {
        super(url, port, username, password, databaseName);
        this.tempFile = super.database.getCollection("tempFile");
    }

    public void addTempFile(TempBlockFile file) {
        this.tempFile.insertOne(DocumentUtils.toDocument(file));
    }

    public boolean removeTempFile(String id) {
        return this.tempFile.deleteMany(Filters.eq("_id", new ObjectId(id))).getDeletedCount() == 1;
    }

    public TempBlockFile getTempFile(String id) {
        MongoCursor<Document> files = this.tempFile.find(Filters.eq("_id", new ObjectId(id))).iterator();
        return files.hasNext() ? DocumentUtils.toTempBlockFile(files.next()) : null;
    }

    public boolean updateTempFile(TempBlockFile file) {
        return this.tempFile.updateOne(Filters.eq("_id", file.getId()), new Document("$set", DocumentUtils.toDocument(file))).getModifiedCount() == 1;
    }
}
