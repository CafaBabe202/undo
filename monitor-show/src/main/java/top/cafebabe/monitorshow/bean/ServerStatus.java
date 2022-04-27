package top.cafebabe.monitorshow.bean;

import top.cafebabe.monitorshow.statusGetter.NetStatusGetter;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author cafababe
 */
public class ServerStatus {
    private static final Map<String, NetStatusGetter> all = new ConcurrentHashMap<>();

    public static void add(String serverName, String host, int port, String pass, int time, String email) {
        NetStatusGetter getter = new NetStatusGetter();
        getter.setServerName(serverName);
        getter.setHots("http://" + host + ":" + port + "/getter");
        getter.setPass(pass);
        getter.setTime(time);
        getter.setEmail(email);
        getter.start();
        all.put(serverName, getter);
    }

    public static void stop(String serverName) {
        all.get(serverName).stop();
        all.remove(serverName);
    }

    public static Status get(String serName) {
        return all.get(serName).get();
    }

    public static boolean exist(String serverName) {
        return all.containsKey(serverName);
    }

    public static Set<String> getAllName(){
        return all.keySet();
    }
}
