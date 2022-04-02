package top.cafebabe.undo.article.test;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

/**
 * @author cafababe
 */
public class demo {

    @Test
    public void ObjectIdTime(){
        ObjectId id = new ObjectId();
        System.out.println(id.getTimestamp());
    }
}
