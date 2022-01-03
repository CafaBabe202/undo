package cn.mrWeird.undo.spy.bean;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MrWeird
 */
@Data
public class SystemInfo {
    private double cpu;
    private double totalMemory;
    private double usedMemory;
    private Map<String,String> disk;

    public SystemInfo(){
        this.disk = new HashMap<>();
    }
}
