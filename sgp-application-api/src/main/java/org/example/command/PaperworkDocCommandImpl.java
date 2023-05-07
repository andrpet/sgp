package org.example.command;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import reactor.core.publisher.Flux;

import java.net.MalformedURLException;
import java.nio.file.Path;

public class PaperworkDocCommandImpl implements PaperworkDocCommand {

    public static final int BUFFER_SIZE = 4096;

    @Value("${upload.directory}")
    private String uploadDirectory;

    @Override
    public Flux<DataBuffer> download(String paperworkId) {
        try {
            Path file = Path.of(uploadDirectory, paperworkId+".pdf");
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return DataBufferUtils.read(resource, new DefaultDataBufferFactory(), BUFFER_SIZE);
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
