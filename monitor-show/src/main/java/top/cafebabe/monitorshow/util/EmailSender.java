package top.cafebabe.monitorshow.util;


import cn.hutool.extra.mail.MailUtil;
import top.cafebabe.monitorshow.bean.Envelope;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author cafababe
 */
public class EmailSender {
    private static final BlockingQueue<Envelope> deque = new LinkedBlockingQueue<>();

    static {
        new Thread(() -> {
            try {
                while (true) {
                    Envelope envelope = deque.take();
                    MailUtil.send(envelope.getTo(), envelope.getSubject(), envelope.getContent(), false);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();
    }

    public static boolean sender(String to, String serverName) {
       return deque.offer(Envelope.error(to, serverName));
    }
}
