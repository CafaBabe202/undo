package top.cafebabe.undo.user.bean;

import lombok.Data;

/**
 * @author cafababe
 */
@Data
public class Envelope {
    private String to;
    private String subject;
    private String content;

    public Envelope(String to, String subject, String content) {
        this.to = to;
        this.subject = subject;
        this.content = content;
    }

    public static Envelope registerCode(String to, String code) {
        return new Envelope(
                to,
                "验证码",
                "欢迎你注册 undo 博客！" +
                        "\n本次验证码为：" + code + "，有效时间：" + (AppConfig.SEND_TIME / 1000) + " 秒。" +
                        "\n如非本人操作，请忽略。");
    }
}
