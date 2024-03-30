package io.github.j3vs;

import redis.clients.jedis.commands.JedisCommands;
import redis.clients.jedis.params.ScanParams;

public class RedisCollection {
    protected final String key;
    protected final JedisCommands jedis;

    public RedisCollection(JedisCommands jedis, String key) {
        this.jedis = jedis;
        this.key = key;
    }

    public RedisCollection(String host, int port, String key) {
        this.jedis = new RedisConfig(host, port).toJedis();
        this.key = key;
    }


    protected class Cursor {
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
}
