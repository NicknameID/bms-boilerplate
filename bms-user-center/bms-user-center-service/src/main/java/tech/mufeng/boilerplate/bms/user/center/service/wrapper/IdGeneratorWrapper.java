package tech.mufeng.boilerplate.bms.user.center.service.wrapper;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;
import tech.mufeng.boilerplate.bms.id.generator.api.IdGenerator;

@Component
public class IdGeneratorWrapper {
    @Reference
    private IdGenerator idGenerator;

    public long nextId() {
        return idGenerator.nextId();
    }
}
