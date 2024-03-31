package io.github.j3vs;

import redis.clients.jedis.params.ScanParams;

public class RedisCursor {
    private String value = ScanParams.SCAN_POINTER_START;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean hasNext() {
        return !this.value.equals("0");
    }
}
