package cn.mrWeird.undo.spy.telegraph;

import cn.mrWeird.undo.spy.bean.Message;

/**
 * 这定义了一个电台的接口，所有的电台都将实现这个接口，通过实现这个接口，可以定制自己的电台。
 *
 * @author MrWeird
 */
public interface Telegraph {

    /**
     * 所有的消息将全部通过这个方法进行发送。
     *
     * @param message 指定你要发送的消息，将由电台将你的消息发送到接受者。
     */
    void send(Message message);

}
