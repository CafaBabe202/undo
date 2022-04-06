package top.cafebabe.undo.user.bean;

/**
 * @author cafababe
 * <p>
 * 这是一个全局的配置类，所有的全局配置都在这个类中。
 */
public class AppConfig {
    public static final long TOKEN_TIME_OUT = 24; // 设置 Token 的超时时间，单位：分钟
    public static final String TOKEN_KEY = "cafebabecafedade"; // 必须是 16 位
    public static final String PASSWORD = "NOCAFE"; // 其他组件访问时的口令

    public static final String DEFAULT_USER_SIGN = ""; // 默认用户签名
    public static final String DEFAULT_USER_AVATAR = ""; // 默认用户头像的 id

    public static final int USERNAME_LEN = 16; // 用户名长度限制
    public static final int PASSWORD_LEN = 128; // 用户密码长度限制
    public static final String EMAIL_PATTEN = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$"; // 邮箱正则
    public static final long AVATAR_SIZE = 2 * 1024 * 1024; // 头像文件的大小，单位：B
    public static final int SIGN_LEN = 64; // 用户签名长度

    public static final String REDIS_TOKEN_PREFIX = "Token:";
    public static final String REDIS_TOKEN_VALUE_TOKEN_KEY = "token";
    public static final String REDIS_TOKEN_VALUE_USER_KEY = "user";

    public static final String TOKEN_NAME_IN_HEADER = "token"; // token 在 header 中的 key

    public static final String AVATAR_BASE_URL = "/userApi/avatar/";
    public static final String LOCAL_AVATAR_DIR = "/home/zh/图片/undo/"; // 用户上传的头像的保存位置

}
