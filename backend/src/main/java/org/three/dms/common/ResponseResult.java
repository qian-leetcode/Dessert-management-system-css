package org.three.dms.common;

import java.util.HashMap;

public class ResponseResult extends HashMap<String, Object> {
    public static final String CODE_TAG = "code";
    public static final String MSG_TAG = "msg";
    public static final String DATA_TAG = "data";
    public static final String TIMESTAMP_TAG = "timestamp";

    public ResponseResult(int code, String msg) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        super.put(TIMESTAMP_TAG, System.currentTimeMillis());
    }
    public ResponseResult(int code, String msg, Object data) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        super.put(DATA_TAG, data);
        super.put(TIMESTAMP_TAG, System.currentTimeMillis());
    }
    public static ResponseResult success(String msg, Object data) {
        return new ResponseResult(200, msg, data);
    }
    public static ResponseResult success(String msg) {
        return new ResponseResult(200, msg, null);
    }
    public static ResponseResult success(Object data) {
        return new ResponseResult(200, "success", data);
    }
    public static ResponseResult success() {
        return new ResponseResult(200, "success", null);
    }
    public static ResponseResult error(int code, String msg) {
        return new ResponseResult(code, msg, null);
    }
    public static ResponseResult error(String msg, Object data) {
        return new ResponseResult(500, msg, data);
    }
    public static ResponseResult error(String msg) {
        return new ResponseResult(500, msg, null);
    }
    public static ResponseResult error() {
        return new ResponseResult(500, "error", null);
    }
}