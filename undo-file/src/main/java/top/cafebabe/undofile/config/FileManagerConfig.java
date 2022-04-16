package top.cafebabe.undofile.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import top.cafebabe.fileManager.manger.SimpleFileManager;
import top.cafebabe.fileManager.manger.integrator.SimpleIntegratorFactory;
import top.cafebabe.fileManager.manger.memory.MongodbMemory;
import top.cafebabe.fileManager.manger.splitter.SimpleSplitterFactory;

/**
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
    public SimpleFileManager simpleFileManager() {
        MongodbMemory memory = new MongodbMemory(this.host, this.port, this.username, this.password, this.dbName);
        SimpleSplitterFactory splitterFactory = new SimpleSplitterFactory(this.blockSize);
        SimpleIntegratorFactory integratorFactory = new SimpleIntegratorFactory();
        return new SimpleFileManager(memory, splitterFactory, integratorFactory);
    }
}
