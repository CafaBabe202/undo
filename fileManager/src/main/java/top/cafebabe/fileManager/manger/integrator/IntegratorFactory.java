package top.cafebabe.fileManager.manger.integrator;

import top.cafebabe.fileManager.exception.ObjectInitException;
import top.cafebabe.fileManager.manger.memory.Memory;

/**
 * @author cafababe
 */
public interface IntegratorFactory {

    Integrator getInstance(Memory memory, String md5) throws ObjectInitException;

}
