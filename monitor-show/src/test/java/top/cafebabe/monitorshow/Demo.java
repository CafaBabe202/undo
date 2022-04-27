package top.cafebabe.monitorshow;

import org.junit.jupiter.api.Test;
import top.cafebabe.monitorshow.statusGetter.NetStatusGetter;

/**
 * @author cafababe
 */
public class Demo {

    @Test
    public void test() throws Exception{
        NetStatusGetter netStatusGetter = new NetStatusGetter();
        netStatusGetter.setHots("http://127.0.0.1:8080/getter");
        netStatusGetter.setEmail("498861397@qq.com");
        netStatusGetter.setServerName("Demo");
        netStatusGetter.setTime(3);
        netStatusGetter.setPass("CAFA");
        netStatusGetter.start();
        Thread.sleep(1000000);
    }
}
