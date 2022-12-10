package Models;

import java.io.Serializable;

public class Message implements Serializable {
    private String functionName, params;
    private Object object;

    public Message() {
    }

    public Message(String functionName, String params) {
        this.functionName = functionName;
        this.params = params;
    }

    public Message(String functionName, Object object) {
        this.functionName = functionName;
        this.object = object;
    }

    public Message(String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
