package top.cafebabe.undo.article.test;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import top.cafebabe.undo.article.util.HttpUtils;
import top.cafebabe.undo.common.bean.LoginUser;
import top.cafebabe.undo.common.bean.ResponseMessage;
import top.cafebabe.undo.common.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cafababe
 */
public class demo {

    @Test
    public void ObjectIdTime() {
        ObjectId id = new ObjectId();
        System.out.println(id.getTimestamp());
    }

    @Test
    public void requestGet() {
        String token = "564a10f1f0bc537acf0530be05f8e84dcb1c889c4d108ca962b98163c8ce8e9211b7b939de5bdde15eda307e6dabb71a";
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("innerApp", List.of("NOCAFE"));
        String get = HttpUtils.GET("http://127.0.0.1:8090/innerApp/getUserDetail/" + token, headers, null, 50);
        System.out.println(StringUtil.pareJson(get, ResponseMessage.class).getData().getClass());
    }
}
