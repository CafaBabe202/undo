package cn.mrWeird.undo.spy.collector;

import cn.mrWeird.undo.spy.bean.SystemInfo;

import java.io.IOException;
import java.util.Scanner;

/**
 * 该类通过执行终端命令的方式进行收集系统信息。这种收集方式很不靠谱。但是并且只能运行在linux环境中。并且根据对命令命令输出的格式有要求。
 * 通过top -bn 1 -i -c来进行收集CPU和内存的信息。
 * 通过df的方式来收集硬盘相关的信息。
 *
 * @author MrWeird
 */
public class TerminalCollector implements Collector {

    private final ProcessBuilder dfBuilder;
    private final ProcessBuilder topBuilder;



    public TerminalCollector() {
        this.dfBuilder = new ProcessBuilder("df");
        this.topBuilder = new ProcessBuilder("top", "-bn", "1", "-i", "-c");
    }

    private void df(SystemInfo info) throws IOException {
        Process p = this.dfBuilder.start();
        Scanner scanner = new Scanner(p.getInputStream());
        while (scanner.hasNext()) {
            String str = scanner.nextLine();
            String[] s = str.split(" +");
            if (s.length == 6)
                info.getDisk().put(s[5], s[4]);
        }
        scanner.close();
    }

    private void top(SystemInfo info) throws IOException {
        Process p = this.topBuilder.start();
        Scanner scanner = new Scanner(p.getInputStream());
        while (scanner.hasNext()) {
            String str = scanner.nextLine();
            String[] s = str.split(" +");
            if (str.startsWith("%Cpu(s)")) {
                info.setCpu(Double.parseDouble(s[7]));
            } else if (str.startsWith("MiB Mem")) {
                info.setTotalMemory(Double.parseDouble(s[3]));
                info.setUsedMemory(Double.parseDouble(s[7]));
            }
        }
        info.setCpu(100-info.getCpu());
        scanner.close();
    }

    /**
     * 立即进行收集系统信息并返回数据。
     *
     * @return 将收集的信息封装在SystemInfo中进行返回。
     */
    @Override
    public SystemInfo collect() {
        SystemInfo info = new SystemInfo();
        try {
            this.df(info);
            this.top(info);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return info;
    }
}
