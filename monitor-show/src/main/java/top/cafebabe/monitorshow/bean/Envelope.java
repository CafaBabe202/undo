package top.cafebabe.monitorshow.bean;

import lombok.Data;
import top.cafebabe.monitorshow.util.StringUtil;

/**
 * @author cafababe
 */
@Data
public class Envelope {
    private String to;
    private String subject;
    private String content;

    private Envelope(String to, String subject, String content) {
        this.to = to;
        this.subject = subject;
        this.content = content;
    }

    public static Envelope error(String to, String serverName) {
        return new Envelope(
                to,
                "服务器异常","您的服务器：" + serverName + "没有响应，请及时处理。\n" + StringUtil.formatData(System.currentTimeMillis())
        );
    }
}
