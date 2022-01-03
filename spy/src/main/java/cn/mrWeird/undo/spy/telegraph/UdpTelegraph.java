package cn.mrWeird.undo.spy.telegraph;

import cn.mrWeird.undo.spy.bean.TelegraphProperties;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * 通过UDP实现的一个抽象的电台，其中提供了基本的发送的方法，为实现自己的定制化电台提供了基本的功能。
 * 该电台基于UDP协议进行消息的发送，这意味着你将不会收到任何的反馈信息，不论对方有没有收到消息，你都将没有任何的提示，这也是由DUP性质所限定的。
 * 但也正式由于UDP无状态，这使得你发送消息的成本很低，如果你并不介意丢掉一两个消息，那你可以使用基于UdpTelegraph进行消息的发送。
 *
 * @author MrWeird
 */
public abstract class UdpTelegraph implements Telegraph {

    private final TelegraphProperties properties;
    private final DatagramSocket datagramSocket;

    /**
     * 创建一个电台，你必须提供电台的必要配置文件，电台将根据你的配置文件进行发送数据。
     *
     * @param properties 必要配置类。
     * @param autoPort 当设置为true时，你设置的发送端口将失效，采用系统自动分配的端口发送数据。
     * @throws SocketException 当你想使用的端口不可用时可能抛出该异常。
     */
    public UdpTelegraph(TelegraphProperties properties, boolean autoPort) throws SocketException {
        this.properties = properties;
        if (autoPort)
            this.datagramSocket = new DatagramSocket();
        else
            this.datagramSocket = new DatagramSocket(properties.getSpendPort());
    }

    /**
     * 一个使用UDP发送数据的基本方式，通过这个方法，可以将字节数据的数据发送给指定的接受者。
     *
     * @param message 消息的byte数据。
     */
    public void send(byte[] message) {
        DatagramPacket packet = new DatagramPacket(message, message.length, this.properties.getHost(), this.properties.getReceivePort());
        try {
            this.datagramSocket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
