package com.mpakhomov.cache;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Example implementation of LRU cache based on {@link LinkedHashMap}
 *
 * @author mpakhomov
 * @since: 7/10/2015
 */
public class CacheLinkedHashMap extends LinkedHashMap {
    private static final int MAX_ENTRIES = 3;

    /**
     * @param  initialCapacity the initial capacity
     * @param  loadFactor      the load factor
     * @param  accessOrder     the ordering mode - <tt>true</tt> for
     *         access-order, <tt>false</tt> for insertion-order
     * @throws IllegalArgumentException if the initial capacity is negative
     *         or the load factor is nonpositive
     */
    public CacheLinkedHashMap(int initialCapacity, float loadFactor, boolean accessOrder) {
        super(initialCapacity, loadFactor, accessOrder);
    }

    /**
     * need to override this protected method to implement cache eviction policy
     *
     * @param eldest
     * @return true if current size of the cache is greater than {@code MAX_ENTRIES},
     *         return false otherwise
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > MAX_ENTRIES;
    }

    public static void main(String[] args) {
        Map<Integer, Integer> cache = new CacheLinkedHashMap(3, 0.75f, true);
        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(3, 3);
        cache.put(4, 4);
        // notice that the first entry(1, 1) was removed
        for(Map.Entry<Integer, Integer> entry : cache.entrySet()) {
            System.out.println(entry.getKey());
        }
        // prints 2, 3, 4

    }
}
