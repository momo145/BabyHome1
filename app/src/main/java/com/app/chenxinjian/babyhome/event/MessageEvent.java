package com.app.chenxinjian.babyhome.event;

/**
 * Created by 陈欣健 on 16/4/23.
 * Email:416941523@qq.com
 */
public class MessageEvent {
    public enum Type {

    }

    private Type type;
    private Object args;

    public MessageEvent(Type type) {
        this.type = type;
    }

    public MessageEvent(Type type, Object args) {
        this.type = type;
        this.args = args;
    }

    public Type getType() {
        return type;
    }

    public Object getArgs() {
        return args;
    }
}

