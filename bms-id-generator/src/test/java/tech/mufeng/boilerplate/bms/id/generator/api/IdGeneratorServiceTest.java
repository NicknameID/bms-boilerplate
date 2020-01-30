package tech.mufeng.boilerplate.bms.id.generator.api;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Slf4j
class IdGeneratorServiceTest {
    @Resource
    private IdGenerator idGenerator;

    @Test
    void nextId() {
        for (int i = 1; i <= 1000; i++) {
            long id = idGenerator.nextId();
            log.info("No.{} 生成新的ID: {}", i, id);
            assertTrue(id > 0, "生成的id小于0");
        }
    }
}