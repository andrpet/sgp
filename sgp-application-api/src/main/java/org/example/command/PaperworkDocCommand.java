package org.example.command;

import org.springframework.core.io.buffer.DataBuffer;
import reactor.core.publisher.Flux;

public interface PaperworkDocCommand {
    Flux<DataBuffer> download(String paperworkId);
}
