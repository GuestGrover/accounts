package com.bmc.interview.saurabh.dbOperator.util;

import java.util.LinkedHashMap;

public class LRUCacheusingLinkedHashMap<K, V> extends LinkedHashMap<K, V> {

	private static final long serialVersionUID = 1L;
	private final int capacity;

	@Override
	protected boolean removeEldestEntry(java.util.Map.Entry<K, V> oldest) {
		// Remove the eldest element whenever size of cache exceeds the capacity
		return (size() > this.capacity);
	}

	public LRUCacheusingLinkedHashMap(int capacity) {
		/*
		 * Call constructor of LinkedHashMap with accessOrder set to true to
		 * achieve LRU Cache behaviour
		 */
		super(capacity + 1, 1.0f, true);
		this.capacity = capacity;
	}

	public V find(K key) {
		return super.get(key);
	}

	public void set(K key, V value) {
		super.put(key, value);
	}
}
