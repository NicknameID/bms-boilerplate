package tech.mufeng.boilerplate.bms.id.generator.service;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;
import tech.mufeng.boilerplate.bms.id.generator.api.IdGenerator;
import tech.mufeng.boilerplate.bms.id.generator.component.IdGeneratorComponent;

import javax.annotation.Resource;

@Service
@Component
public class IdGeneratorImp implements IdGenerator {
    @Resource
    private IdGeneratorComponent idGeneratorComponent;

    @Override
    public long nextId() {
        return idGeneratorComponent.nextId();
    }
}
