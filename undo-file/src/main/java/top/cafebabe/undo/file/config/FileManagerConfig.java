package top.cafebabe.undo.file.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import top.cafebabe.fragment.manger.integrator.SimpleIntegratorFactory;
import top.cafebabe.fragment.manger.memory.TempMongoMemory;
import top.cafebabe.fragment.manger.splitter.SimpleSplitterFactory;
import top.cafebabe.fragment.manger.TempFileManager;

/**
 * 文件管理对的配置文件
 * 配置一个文件管理的对象到 SpringIOC 中，方便使用。
 *
 * @author cafababe
 */
@Data
@Component
@ConfigurationProperties(prefix = "file.manager")
public class FileManagerConfig {
    private String host;
    private int port;
    private String username;
    private String password;
    private String dbName;
    private int blockSize;

    @Bean
    public TempFileManager tempFileManager() {
        TempMongoMemory memory = new TempMongoMemory(this.host, this.port, this.username, this.password, this.dbName);
        SimpleSplitterFactory splitterFactory = new SimpleSplitterFactory(this.blockSize);
        SimpleIntegratorFactory integratorFactory = new SimpleIntegratorFactory();
        return new TempFileManager(memory, splitterFactory, integratorFactory);
    }
}
