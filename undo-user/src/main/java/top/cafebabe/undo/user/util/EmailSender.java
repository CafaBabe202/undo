package top.cafebabe.undo.user.util;

import cn.hutool.extra.mail.MailUtil;
import top.cafebabe.undo.common.util.RandomUtils;
import top.cafebabe.undo.user.bean.Envelope;

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

    public static String sender(String email) {
        String code = RandomUtils.registerCode();
        return deque.offer(Envelope.registerCode(email, code)) ? code : null;
    }
}
