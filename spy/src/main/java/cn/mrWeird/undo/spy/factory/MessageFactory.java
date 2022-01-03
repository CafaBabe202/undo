package cn.mrWeird.undo.spy.factory;

import cn.mrWeird.undo.spy.bean.Message;
import cn.mrWeird.undo.spy.bean.ModuleProperties;
import cn.mrWeird.undo.spy.bean.SystemInfo;
import cn.mrWeird.undo.spy.exception.LengthOutOfLimitException;

/**
 * 消息工厂，通过可以通过消息工厂来很简单的创建一个标准格式的消息。
 *
 * @author MrWeird
 */
public class MessageFactory {

    private final ModuleProperties properties;

    public MessageFactory(ModuleProperties properties) {
        this.properties = properties;
    }

    private Message createMessage(){
        Message message = new Message();
        message.setTime(System.currentTimeMillis());
        message.setModuleId(this.properties.getModuleId());
        message.setModuleName(this.properties.getModuleName());
        return message;
    }

    public Message newFullMessage(SystemInfo info) {
        Message message = this.createMessage();
        message.setType(Message.FULL);
        message.setInfo(info);
        return message;
    }

    public Message newWaningMessage(String waning)throws LengthOutOfLimitException{
        Message message = this.createMessage();
        message.setWarning(waning);
        return message;
    }
}
