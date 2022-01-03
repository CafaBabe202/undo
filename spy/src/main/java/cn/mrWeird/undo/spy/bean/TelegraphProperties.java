package cn.mrWeird.undo.spy.bean;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 这是电台的配置类，通过这个类你可以指定接受者的主机地址端口和你发送消息时使用的端口。
 *
 * @author MrWeird
 */
@Getter
@Setter
public class TelegraphProperties {

    private InetAddress host;
    private Integer receivePort;
    private Integer spendPort;

    /**
     * @param host        指定接受者的主机地址。
     * @param receivePort 指定接收者的端口。
     * @param spendPort   指定发送消息所使用的端口。
     * @throws UnknownHostException 如果主机地址解析失败，将抛出该异常。
     */
    public TelegraphProperties(String host, int receivePort, int spendPort) throws UnknownHostException {
        this.host = InetAddress.getByName(host);
        this.receivePort = receivePort;
        this.spendPort = spendPort;
    }

}
