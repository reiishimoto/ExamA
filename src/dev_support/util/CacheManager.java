package dev_support.util;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class CacheManager<K, V> {
  private final LinkedHashMap<K, V> cache;
  private int MAX_SIZE;
  private int RETAIN_SIZE;

  public CacheManager(int maxSize, double retainRatio) {
    cache = new LinkedHashMap<>();
    MAX_SIZE = maxSize;
    RETAIN_SIZE = (int) (maxSize * retainRatio);
    initCheck();
  }

  public CacheManager(int maxSize) {
    cache = new LinkedHashMap<>();
    MAX_SIZE = maxSize;
    RETAIN_SIZE = (int) (maxSize * 0.75);
    initCheck();
  }

  public CacheManager(int maxSize, boolean LRUmode) {
    cache = new LinkedHashMap<K, V>(Math.max(16, MAX_SIZE), 0.75f, LRUmode);
    MAX_SIZE = maxSize;
    RETAIN_SIZE = (int) (maxSize * 0.75);
    initCheck();
  }

  public CacheManager(int maxSize, double retainRatio, boolean LRUmode) {
    cache = new LinkedHashMap<K, V>(Integer.lowestOneBit((int) Math.sqrt(maxSize)), 0.75f, LRUmode);
    MAX_SIZE = maxSize;
    RETAIN_SIZE = (int) (maxSize * retainRatio);
    initCheck();
  }

  public V lookUp(K key) {
    return cache.get(key);
  }

  public void put(K key, V result) {
    synchronized (cache) {
      cache.put(key, result);
      if (cache.size() > MAX_SIZE) {
        capacityOver();
      }
    }
  }

  public boolean remove(K key) {
    synchronized (cache) {
      return cache.remove(key) != null;
    }
  }

  public void clear() {
    synchronized(cache) {
      cache.clear();
    }
  }

  private void capacityOver() {
    Iterator<K> iter = cache.keySet().iterator();
    while (cache.size() > RETAIN_SIZE && iter.hasNext()) {
      iter.next();
      iter.remove();
    }
  }


  private void initCheck() {
    List<String> messages = new ArrayList<>();
    if (MAX_SIZE < 1) {
      messages.add("最大サイズが小さすぎます:" + MAX_SIZE);
    }
    if (RETAIN_SIZE < 0 || RETAIN_SIZE > MAX_SIZE) {
      messages.add("維持割合が不正です:"+(MAX_SIZE/RETAIN_SIZE));
    }
    if (!messages.isEmpty()) throw new IllegalArgumentException(String.join(", ", messages));
  }
}