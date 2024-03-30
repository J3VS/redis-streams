package io.github.j3vs;

import redis.clients.jedis.JedisPooled;

public class RedisConfig
{
    private String host;
    private int port;
    public RedisConfig(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public JedisPooled toJedis() {
        return new JedisPooled(host, port);
    }
}
