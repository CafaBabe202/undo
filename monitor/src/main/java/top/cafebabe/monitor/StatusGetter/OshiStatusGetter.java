package top.cafebabe.monitor.StatusGetter;

import cn.hutool.system.oshi.OshiUtil;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HWPartition;
import oshi.hardware.NetworkIF;
import top.cafebabe.monitor.bean.Status;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cafababe
 */
public class OshiStatusGetter {
    public static Status getStatus() {
        Status status = new Status();
        status.setCpuUsed(OshiUtil.getCpuInfo().getUsed());
        status.setAllMemory(OshiUtil.getHardware().getMemory().getTotal());
        status.setAvailableMemory(OshiUtil.getHardware().getMemory().getAvailable());
        status.setVirtualMemory(OshiUtil.getHardware().getMemory().getVirtualMemory().toString());
        status.setOs(OshiUtil.getOs().toString());
        List<NetworkIF> networkIFs = OshiUtil.getNetworkIFs();
        List<String> netWork = new ArrayList<>();
        for (NetworkIF n : networkIFs) netWork.add(n.toString().replaceAll("\\n", "</br>"));
        status.setNetWork(netWork);

        List<String> disk = new ArrayList<>();
        List<HWDiskStore> diskStores = OshiUtil.getDiskStores();
        for (HWDiskStore h : diskStores) {
            List<HWPartition> partitions = h.getPartitions();
            for (HWPartition p : partitions) {
                disk.add(p.getIdentification() + "：" + p.getSize() / (1024 * 1024) + " MB");
            }
        }
        status.setDisk(disk);
        return status;
    }
}
