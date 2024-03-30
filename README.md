## Redis Streams

A utility library for turning redis data structures into Java Collections

e.g.

```java

JedisPooled jedis = new JedisPooled("localhost", 6379);

RedisSet set = new RedisSet(jedis, "my-set");

set.add("first");
set.add("second");
set.add("second");

set.forEach((s) -> System.out.println(s));
```
would print out
```java
first
second
```
in some order.

Support is intended for lists, queues and hashmaps.