package publisher;

import org.example.model.SGPNofiticationMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

public class SGPPublisherImpl implements SGPPublisher {

    @Value("${kafka.topic}")
    String topic;

    private final KafkaTemplate<String, SGPNofiticationMessage> kafkaTemplate;

    public SGPPublisherImpl(KafkaTemplate<String, SGPNofiticationMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publishStatusChange(String paperworkId, SGPNofiticationMessage sgpNofiticationMessage) {
        kafkaTemplate.send(topic, sgpNofiticationMessage);
    }
}
