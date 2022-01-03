package cn.mrWeird.undo.spy.telegraph;

import cn.mrWeird.undo.spy.bean.Message;
import cn.mrWeird.undo.spy.bean.TelegraphProperties;
import cn.mrWeird.undo.spy.util.Util;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

/**
 * 这是一个十分简单的电台，他并不会对你的消息进行任何形式的处理，而是直接转换成数据包进行发送。
 *
 * @author MrWeird
 */
public class SimpleTelegraph extends UdpTelegraph {

    /**
     * 创建一个电台，你必须提供电台的必要配置文件，电台将根据你的配置文件进行发送数据。
     *
     * @param properties 必要配置类，如果你的发送端口设置成-1，那么将采用系统分配的端口。
     * @throws SocketException 如果你想要使用的端口不可用，将抛出一个SocketException异常。
     */
    public SimpleTelegraph(TelegraphProperties properties) throws SocketException {
        super(properties, properties.getSpendPort() == -1);
    }

    /**
     * 根据你的配置创建一个电台，电台将按照你的配置发送数据。
     * @param host 接受的主机地址。
     * @param receivePort 接受这的端口号。
     * @param spendPort 你发送数据时采用的端口号，如果设置成-1将采用系统自动分配的端口进行发送数据。
     * @throws SocketException 如果你的主机地址不可用，或者你设置的端口号不可用，将抛出此异常
     * @throws UnknownHostException 如果没能找到接受者的主机地址，将会抛出此异常。
     */
    public SimpleTelegraph(String host, int receivePort, int spendPort) throws SocketException, UnknownHostException {
        super(new TelegraphProperties(host, receivePort, spendPort), spendPort == -1);
    }

    @Override
    public void send(Message message) {
        super.send(Util.toJson(message).getBytes(StandardCharsets.UTF_8));
    }
}
