import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.lang.Math;

public class MyHashMap<K, V> implements DefaultMap<K, V> {
	public static final double DEFAULT_LOAD_FACTOR = 0.75;
	public static final int DEFAULT_INITIAL_CAPACITY = 16;
	public static final String ILLEGAL_ARG_CAPACITY = "Initial Capacity must be non-negative";
	public static final String ILLEGAL_ARG_LOAD_FACTOR = "Load Factor must be positive";
	public static final String ILLEGAL_ARG_NULL_KEY = "Keys must be non-null";
	
	private double loadFactor;
	private int capacity;
	private int size;

	// Use this instance variable for Separate Chaining conflict resolution
	public List<HashMapEntry<K, V>>[] buckets;  
	
	// Use this instance variable for Linear Probing
	private HashMapEntry<K, V>[] entries; 	


	public MyHashMap() {
		this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);

	}
	

	/**
	 * 
	 * @param initialCapacity the initial capacity of this MyHashMap
	 * @param loadFactor the load factor for rehashing this MyHashMap
	 * @throws IllegalArgumentException if initialCapacity is negative or loadFactor not
	 * positive
	 */
	@SuppressWarnings("unchecked")
	public MyHashMap(int initialCapacity, double loadFactor) throws IllegalArgumentException {
		if (initialCapacity < 0  ) {
			throw new IllegalArgumentException(ILLEGAL_ARG_CAPACITY);
		}
		if (loadFactor <= 0) {
			throw new IllegalArgumentException(ILLEGAL_ARG_LOAD_FACTOR);
		}

		this.capacity = initialCapacity;
		this.loadFactor = loadFactor;
		this.size = 0;

		// if you use Separate Chaining
		this.buckets = (ArrayList<HashMapEntry<K, V>>[]) new ArrayList<?>[capacity];

		// if you use Linear Probing
		entries = (HashMapEntry<K, V>[]) new HashMapEntry<?, ?>[initialCapacity];
	}

	@Override
	public boolean put(K key, V value) throws IllegalArgumentException {
		if (key == null) {
			throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
		}
		// can also use key.hashCode() assuming key is not null
		int keyHash = Objects.hashCode(key); 
		int index = Math.abs(keyHash % capacity);
		HashMapEntry<K,V> entry = new HashMapEntry<K,V>(key, value);
		if (buckets[index] == null) {
		buckets[index] = new ArrayList<>();
		}
		if (!buckets[index].contains(key)) {
			buckets[index].add(entry);
			size+=1;
			return true;
		}
		
		return false;
	}

	@Override
	public boolean replace(K key, V newValue) throws IllegalArgumentException {
		if (key == null) {
			throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
		}
		int keyHash = key.hashCode();
		int index = Math.abs(keyHash % capacity);
		
		for (HashMapEntry<K,V> i :buckets[index]) {
			if (i.getKey().equals(key)) {
				i.setValue(newValue);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean remove(K key) throws IllegalArgumentException {
		if (key == null) {
			throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
		}
		int keyHash = Objects.hashCode(key);
		int index = Math.abs(keyHash % capacity);
		for (HashMapEntry<K,V> i :buckets[index]) {
			if (i.getKey().equals(key)) {
				buckets[index].remove(i);
				return true; 
			}
		}
		size-=1;
		return false;
	}

	@Override
	public void set(K key, V value) throws IllegalArgumentException {
		if (key == null) {
			throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
		}
		int keyHash = key.hashCode();
		int index = Math.abs(keyHash % capacity);
		//finds for same key and update value if there is
		if (buckets[index] != null) {
			for (HashMapEntry<K,V> i :buckets[index]) {
				if (i.getKey().equals(key)) {
					i.setValue(value);
					return;
				}
			}
		}
		//adds entry to the list
		buckets[index] = new ArrayList<>();
		buckets[index].add(new HashMapEntry<K,V>(key, value));
		size+=1;
	}

	@Override
	public V get(K key) throws IllegalArgumentException {
		if (key == null) {
			throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
		}
		int keyHash = key.hashCode();
		int index = Math.abs(keyHash % capacity);

		//finds the key and return value
		for (HashMapEntry<K,V> i :buckets[index]) {
			if (i.getKey().equals(key)) {
				return i.getValue();
			}
		}

		return null;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		if (size == 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean containsKey(K key) throws IllegalArgumentException {
		if (key == null) {
			throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
		}
		int keyHash = key.hashCode();
		int index = Math.abs(keyHash % capacity);
		for (HashMapEntry<K,V> i :buckets[index]) {
			if (i.getKey().equals(key)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<K> keys() {
		List<K> keys = new ArrayList<K>();
		for (int i = 0; i < buckets.length; ++i) {
			if (buckets[i] != null) {
			for (HashMapEntry<K,V> key: buckets[i]) {
				keys.add(key.getKey());
			}
			}
		}
	
		return keys;
	}
	
	private static class HashMapEntry<K, V> implements DefaultMap.Entry<K, V> {
		
		K key;
		V value;
		
		private HashMapEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}
		
		@Override
		public void setValue(V value) {
			this.value = value;
		}
	}
}
