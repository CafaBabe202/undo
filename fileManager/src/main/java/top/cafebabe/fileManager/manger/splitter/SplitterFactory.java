package top.cafebabe.fileManager.manger.splitter;

import top.cafebabe.fileManager.exception.ObjectInitException;

import java.io.InputStream;

/**
 * @author cafababe
 */
public interface SplitterFactory {
    Splitter getInstance(InputStream is) throws ObjectInitException;
}
