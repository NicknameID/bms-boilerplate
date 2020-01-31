package tech.mufeng.boilerplate.bms.common.exception;

import lombok.Getter;
import lombok.Setter;

public class CustomException extends RuntimeException {
    private static final String defaultMsg = "未知错误，请及时联系开发人员!";
    @Getter
    private Integer code = -1;
    @Getter
    private String message = defaultMsg;

    public CustomException() {
        super(defaultMsg);
    }

    public CustomException(String msg) {
        super(msg);
        this.message = msg;
    }

    public CustomException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.message = msg;
    }

    public CustomException extendsMsg(String msg) {
        this.message = String.format("[%s]: %s", this.message, msg);
        return this;
    }
}
