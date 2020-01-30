package tech.mufeng.boilerplate.bms.id.generator.api;

public interface IdGenerator {
    /**
     * 获取全局ID
     * @return 全局ID
     */
    long nextId();
}
