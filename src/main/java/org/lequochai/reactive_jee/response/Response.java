package org.lequochai.reactive_jee.response;

public class Response<T> {
    // Fields:
    private boolean success;
    private String message;
    private String code;
    private T result;

    // Constructors:
    public Response() {
    }
    public Response(boolean success, String message, String code, T result) {
        this.success = success;
        this.message = message;
        this.code = code;
        this.result = result;
    }

    // Getters / setters:
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public T getResult() {
        return result;
    }
    public void setResult(T result) {
        this.result = result;
    }
}
