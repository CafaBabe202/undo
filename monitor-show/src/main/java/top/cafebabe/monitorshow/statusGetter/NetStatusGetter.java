package top.cafebabe.monitorshow.statusGetter;

import lombok.Data;
import top.cafebabe.monitorshow.bean.Status;
import top.cafebabe.monitorshow.util.EmailSender;
import top.cafebabe.monitorshow.util.HttpUtils;
import top.cafebabe.monitorshow.util.StringUtil;

/**
 * @author cafababe
 */
@Data
public class NetStatusGetter {

    private String serverName;
    private String hots;
    private String pass;
    private String status;
    private int time;
    private String email;

    private Thread thread;

    public Status get() {
        try {
            return StringUtil.pareJson(this.status, Status.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void stop() {
        try {
            this.thread.interrupt();
            this.thread = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        createThread();
        this.thread.start();
    }

    private void createThread() {
        this.thread = new Thread(() -> {
            try {
                while (!Thread.interrupted()) {
                    String s = HttpUtils.GET(this.hots + "/" + this.pass);
                    if (s == null) {
                        EmailSender.sender(this.email, this.serverName);
                        System.out.println("邮件提醒");
                    } else {
                        this.status = s;
                        System.out.println(this.status);
                    }
                    Thread.sleep(this.time * 1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
