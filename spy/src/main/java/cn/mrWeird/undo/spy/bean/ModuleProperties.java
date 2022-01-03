package cn.mrWeird.undo.spy.bean;

import cn.mrWeird.undo.spy.exception.LengthOutOfLimitException;
import lombok.Getter;

import java.nio.charset.StandardCharsets;

/**
 * 配置包括了该模块的名字和ID，ID应该用来区分唯一的模块，如果对模块进行重复的部署，那么应该拥有相同的模块名和不同的ID。
 * 同时为了传递方便，不论是模块名还是ID都不行应该大于 32 字节（可以等于）。
 * @author MrWeird
 */
@Getter
public class ModuleProperties {
    private String ModuleName;
    private String ModuleId;

    public ModuleProperties(String moduleName, String moduleId) {
        ModuleName = moduleName;
        ModuleId = moduleId;
    }

    /**
     * 设置模块明
     * @param name 模块名
     * @throws LengthOutOfLimitException 如果长度大于 32 字节，将抛出该异常。
     */
    public void setModuleName(String name)throws LengthOutOfLimitException {
        if(name.getBytes(StandardCharsets.UTF_8).length > 32)
            throw new LengthOutOfLimitException("Module length out of 32bytes");
        this.ModuleName = name;
    }

    /**
     * 设置模块ID
     * @param id 模块ID
     * @throws LengthOutOfLimitException 如果长度大于 32 字节，将抛出该异常。
     */
    public void setModuleId(String id)throws LengthOutOfLimitException {
        if(id.getBytes(StandardCharsets.UTF_8).length > 32)
            throw new LengthOutOfLimitException("Module length out of 32bytes");
        this.ModuleId = id;
    }
}
