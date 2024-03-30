package io.github.j3vs;

import redis.clients.jedis.commands.JedisCommands;

import java.util.*;

public class RedisList implements List<String> {
    private final String key;
    private final JedisCommands jedis;

    public RedisList(JedisCommands jedis, String key) {
        this.jedis = jedis;
        this.key = key;
    }

    public RedisList(String host, int port, String key) {
        this.jedis = new RedisConfig(host, port).toJedis();
        this.key = key;
    }

    @Override
    public int size() {
        return Math.toIntExact(this.jedis.llen(this.key));
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<String> iterator() {
        return this.subList(0, -1).iterator();
    }

    @Override
    public Object[] toArray() {
        return this.subList(0, -1).toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(String v) {
        this.jedis.rpush(v);
        return true;
    }

    @Override
    public boolean remove(Object v) {
        long occurrences = this.jedis.lrem(this.key, 0, v.toString());
        return occurrences > 0;
    }

    @Override
    public boolean addAll(Collection<? extends String> vs) {
        vs.forEach(this::add);
        return true;
    }

    @Override
    public boolean addAll(int index, Collection vs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        this.jedis.del(this.key);
    }

    @Override
    public String get(int index) {
        return this.jedis.lindex(this.key, index);
    }

    @Override
    public String set(int index, String element) {
        return null;
    }

    @Override
    public void add(int index, String element) {

    }

    @Override
    public String remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<String> listIterator() {
        return null;
    }

    @Override
    public ListIterator<String> listIterator(int index) {
        return null;
    }

    @Override
    public List<String> subList(int fromIndex, int toIndex) {
        return this.jedis.lrange(this.key, fromIndex, toIndex);
    }

    @Override
    public boolean retainAll(Collection c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection c) {
        return false;
    }

    @Override
    public boolean containsAll(Collection c) {
        return false;
    }


}
