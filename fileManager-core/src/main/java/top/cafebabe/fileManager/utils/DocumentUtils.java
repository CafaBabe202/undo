package top.cafebabe.fileManager.utils;

import org.bson.Document;
import org.bson.types.Binary;
import top.cafebabe.fileManager.bean.Block;
import top.cafebabe.fileManager.bean.BlockFile;

/**
 * @author cafababe
 */
public class DocumentUtils {

    public static Document toDocument(Block block) {
        Document document = new Document();
        document.append("_id", block.getMd5());
        document.append("count", block.getCount());
        document.append("content", block.getContent());
        document.append("size", block.getSize());
        return document;
    }

    public static Document toDocument(BlockFile blockFile) {
        Document document = new Document();
        document.append("_id", blockFile.getMd5());
        document.append("content", blockFile.getContent());
        document.append("size", blockFile.getSize());
        document.append("count",blockFile.getCount());
        return document;
    }

    public static Block toBlock(Document document) {
        Block res = new Block();
        res.setMd5(document.getString("_id"));
        res.setContent(((Binary) document.get("content")).getData());
        res.setSize(document.getInteger("size"));
        res.setCount(document.getLong("count"));
        return res;
    }

    public static BlockFile toBlockFile(Document document) {
        BlockFile res = new BlockFile();
        res.setMd5(document.getString("_id"));
        res.setCount(document.getLong("count"));
        res.setContent(document.getList("content", String.class));
        res.setSize(document.getLong("size"));
        return res;
    }
}
