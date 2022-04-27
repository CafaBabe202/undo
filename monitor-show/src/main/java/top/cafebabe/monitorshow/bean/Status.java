package top.cafebabe.monitorshow.bean;

import lombok.Data;

import java.util.List;

/**
 * @author cafababe
 */
@Data
public class Status {
    private double cpuUsed;
    private long allMemory;
    private long availableMemory;
    private String virtualMemory;
    private String os;
    private List<String> netWork;
    private List<String> disk;
}
