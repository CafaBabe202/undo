package top.cafebabe.monitorshow.bean;

import lombok.Data;
import top.cafebabe.monitorshow.util.StringUtil;

/**
 * @author cafababe
 */
@Data
public class AddServerForm {
    private String serverName;
    private String ip;
    private int port;
    private String pass;
    private int time;
    private String email;

    public boolean check() {
        return serverName.length() < 16
                && time < 600
                && time >= 3
                && StringUtil.isIpv4(this.ip);
    }
}
