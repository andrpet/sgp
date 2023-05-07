package publisher;

import org.example.model.SGPNofiticationMessage;

public interface SGPPublisher {

    void publishStatusChange(String paperworkId, SGPNofiticationMessage sgpNofiticationMessage);

}
