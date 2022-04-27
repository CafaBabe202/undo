package top.cafebabe.monitor;

import org.junit.jupiter.api.Test;
import top.cafebabe.monitor.StatusGetter.OshiStatusGetter;

/**
 * @author cafababe
 */
public class TestDemo {
    @Test
    public void test(){
        System.out.println(OshiStatusGetter.getStatus());
    }
}
