package top.cafebabe.undofile.bean;

/**
 * @author cafababe
 */
public class AppConfig {

    // api 配置
    public static final String USER_GET_LOGIN_USER_API = "http://127.0.0.1:8090/innerApp/getUserDetail/"; // 通过 Token 获取 loginUser 的接口地址

    // 密钥配置
    public static final String INNER_APP_PASSWORD = "NOCAFE"; // 访问 User 模块的口令
}
