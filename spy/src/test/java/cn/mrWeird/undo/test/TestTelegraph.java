package cn.mrWeird.undo.test;

import cn.mrWeird.undo.spy.bean.ModuleProperties;
import cn.mrWeird.undo.spy.bean.TelegraphProperties;
import cn.mrWeird.undo.spy.collector.Collector;
import cn.mrWeird.undo.spy.collector.TerminalCollector;
import cn.mrWeird.undo.spy.exception.LengthOutOfLimitException;
import cn.mrWeird.undo.spy.factory.MessageFactory;
import cn.mrWeird.undo.spy.telegraph.SimpleTelegraph;
import cn.mrWeird.undo.spy.telegraph.Telegraph;
import cn.mrWeird.undo.spy.util.Spy;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * @author MrWeird
 */
@Slf4j
public class TestTelegraph {

    @Test
    public void spend() {
        try {
            ModuleProperties moduleProperties = new ModuleProperties("测试", "测试-1");
            TelegraphProperties telegraphProperties = new TelegraphProperties("127.0.0.1", 8088, -1);

            Spy spy = new Spy(moduleProperties, telegraphProperties);
            spy.setInterval(5);
            spy.start();

            Thread.sleep(30 * 1000);
            spy.stop();

            Thread.sleep(30 * 1000);
            spy.start();


            Thread.sleep(30 * 1000);
            System.out.println("end");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void test(){
        log.info("??????????");
    }
}
