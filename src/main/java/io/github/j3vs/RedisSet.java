package io.github.j3vs;

import redis.clients.jedis.commands.JedisCommands;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;

import java.util.*;

public class RedisSet extends RedisCollection implements Set<String> {
    public RedisSet(JedisCommands jedis, String key) {
        super(jedis, key);
    }

    public RedisSet(String host, int port, String key) {
        super(host, port, key);
    }

    @Override
    public int size() {
        return Math.toIntExact(this.jedis.scard(this.key));
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean contains(Object v) {
        return this.jedis.sismember(this.key, v.toString());
    }

    @Override
    public Iterator<String> iterator() {
        final RedisCursor cursor = new RedisCursor();
        ScanParams scanParams = new ScanParams().count(1000);
        LinkedList<String> results = new LinkedList<>();
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return !cursor.hasNext();
            }

            @Override
            public String next() {
                if (results.isEmpty()) {
                    ScanResult<String> result = jedis.sscan(key, cursor.getValue(), scanParams);
                    results.addAll(result.getResult());
                    cursor.setValue(result.getCursor());
                }
                return results.remove();
            }
        };
    }

    @Override
    public Object[] toArray() {
        return this.jedis.smembers(this.key).toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(String s) {
        this.jedis.sadd(this.key, s);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        long occurrences = this.jedis.srem(this.key, o.toString());
        return occurrences > 0;
    }

    @Override
    public boolean containsAll(Collection<?> vs) {
        return vs.stream().allMatch(this::contains);
    }

    @Override
    public boolean addAll(Collection<? extends String> vs) {
        vs.forEach(this::add);
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        for (String v : this) {
            if (!c.contains(v)) {
                remove(v);
            }
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        c.forEach(this::remove);
        return true;
    }

    @Override
    public void clear() {
        this.jedis.del(this.key);
    }
}
