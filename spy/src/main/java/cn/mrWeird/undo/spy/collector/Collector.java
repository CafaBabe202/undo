package cn.mrWeird.undo.spy.collector;

import cn.mrWeird.undo.spy.bean.SystemInfo;

/**
 * 通过收集这进行收集系统信息。收集之后，将封装在SystemInfo中返回。
 * @author MrWeird
 */
public interface Collector {
    SystemInfo collect();
}
