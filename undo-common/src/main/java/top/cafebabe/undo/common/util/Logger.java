package top.cafebabe.undo.common.util;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.log.level.Level;

/**
 * @author cafababe
 */
public class Logger {
    private static final Log log = LogFactory.get();

    public static void logger(Object data) {
        if (data instanceof String)
            log.log(Level.INFO, data.toString());
        else
            log.log(Level.INFO, StringUtil.toJson(data));
    }
}
