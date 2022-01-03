package cn.mrWeird.undo.spy.util;

import cn.mrWeird.undo.spy.bean.ModuleProperties;
import cn.mrWeird.undo.spy.bean.TelegraphProperties;
import cn.mrWeird.undo.spy.collector.Collector;
import cn.mrWeird.undo.spy.collector.TerminalCollector;
import cn.mrWeird.undo.spy.exception.LengthOutOfLimitException;
import cn.mrWeird.undo.spy.factory.MessageFactory;
import cn.mrWeird.undo.spy.telegraph.SimpleTelegraph;
import cn.mrWeird.undo.spy.telegraph.Telegraph;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketException;

/**
 * 你可以通过间谍来帮助你一直监视系统的基本情况，每一个间谍都会启动一个线程，通过设置interval的值来设置每次发送数据之间的时间间隔，默认是10，你最少将interval设置为5,这意味这每个5秒会自动收集数据并发送。
 * 每一个间谍都会有默认的电台和情报收集器，如果你觉得他们不能满足的你的需求，你可以手动设置功能更加强大的电台和情报收集器。
 *
 * @author MrWeird
 */
@Slf4j
public class Spy {
    private Telegraph telegraph;
    private Collector collector;
    private Thread thread;
    private MessageFactory messageFactory;
    private int interval;

    /**
     * 创建一个间谍。
     *
     * @param moduleProperties    设置moduleProperties，他所发送的每一份情报都会到有这些信息。
     * @param telegraphProperties 设置telegraphProperties，告诉他收集的情报将发往何处。
     * @throws SocketException 如果你的telegraphProperties中配置的地址不能被正确的解析或者你想要使用的端口出现问题，将会抛出这个异常。
     */
    public Spy(ModuleProperties moduleProperties, TelegraphProperties telegraphProperties) throws SocketException {
        log.info("创建一个间谍");
        this.collector = new TerminalCollector();
        this.telegraph = new SimpleTelegraph(telegraphProperties);
        this.messageFactory = new MessageFactory(moduleProperties);
        this.interval = 10;
    }

    private void spendMessage(String str) {
        try {
            telegraph.send(messageFactory.newWaningMessage(str));
        } catch (LengthOutOfLimitException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 启动你的间谍，让他开始工作。
     */
    public void start() {

        this.thread = new Thread(() -> {
            try {
                log.info("间谍正在启动。");
                spendMessage("间谍准备启动！");
                while (true) {
                    telegraph.send(messageFactory.newFullMessage(collector.collect()));
                    Thread.sleep(interval * 1000L);
                }
            } catch (InterruptedException e) {
                spendMessage("间谍准备停止！");
                log.warn(e.toString());
            }
        });
        this.thread.start();
    }

    /**
     * 告诉他应该停止了，他会尽快的停止工作，然后发送出一个提示消息，告诉情报局他将要停止。
     * 停止工作后，你可以继续调用他的start让他继续收集并信息。
     */
    public void stop() {
        this.thread.interrupt();
        log.info("间谍已经停止。");
    }

    /**
     * 设置时间间隔
     *
     * @param interval 你所设置的时间间隔，单位：秒
     * @return 返回是否设置成功，如果你设置的时间间隔小于5，那么将会失败。
     */
    public boolean setInterval(int interval) {
        if (interval >= 5) {
            this.interval = interval;
            return true;
        }
        return false;
    }

    /**
     * 设置一个收集器。
     *
     * @param collector 收集器对象。
     */
    public void setCollector(Collector collector) {
        log.info("情报收集器更新，将采用新的收集器进行收集");
        this.collector = collector;
    }

    /**
     * 设置新的电台配置文件，这可以使你的数据发送到新的情报局地址，但是这将重新恢复到默认的电台，请注意你是否配置过自己的电台。
     *
     * @param properties 新的配置类。
     * @throws SocketException 如果新的地址出现问题，或者说你想要用的端口出现问题，将抛出此异常。
     * @deprecated 由于这会更新你的电台，所以很容易出现问题，不建议使用。
     */
    public void setTelegraphProperties(TelegraphProperties properties) throws SocketException {
        log.info("更新间谍的电台配置，将采用默认的电台，数据将发送到新的地址");
        this.telegraph = new SimpleTelegraph(properties);
    }

    /**
     * 设置一个新的ModuleProperties，这会更新整个MessageFactory对象。
     *
     * @param properties 新的ModuleProperties。
     */
    public void setModuleProperties(ModuleProperties properties) {
        log.info("间谍模块信息更新，将采用新的消息工厂");
        this.messageFactory = new MessageFactory(properties);
    }
}
