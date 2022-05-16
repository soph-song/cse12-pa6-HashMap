import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.*;

public class MyHashMapTest {
	
	private DefaultMap<String, String> testMap; // use this for basic tests
	private DefaultMap<String, String> mapWithCap; // use for testing proper rehashing
	public static final String TEST_KEY = "Test Key";
	public static final String TEST_VAL = "Test Value";
	
	@Before
	public void setUp() {
		testMap = new MyHashMap<>();
		mapWithCap = new MyHashMap<>(4, MyHashMap.DEFAULT_LOAD_FACTOR);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPut_nullKey() {
		testMap.put(null, TEST_VAL);
		testMap.remove(null);
		testMap.set(null, TEST_VAL);
		testMap.replace(null, TEST_VAL);
		testMap.get(null);
		
	}
		
	
	@Test
	public void testKeys_nonEmptyMap() {
		// You don't have to use array list 
		// This test will work with any object that implements List
		List<String> expectedKeys = new ArrayList<>(5);
		for(int i = 0; i < 5; i++) {
			// key + i is used to differentiate keys since they must be unique
			testMap.put(TEST_KEY + i, TEST_VAL + i);
			expectedKeys.add(TEST_KEY + i);
		}
		List<String> resultKeys = testMap.keys();
		// we need to sort because hash map doesn't guarantee ordering
		Collections.sort(resultKeys);
		assertEquals(expectedKeys, resultKeys);
		//test remove
		testMap.remove(TEST_KEY);
		resultKeys = testMap.keys();
		Collections.sort(resultKeys);
		expectedKeys.remove(TEST_KEY);
		assertEquals(expectedKeys,resultKeys);
		//test set
		testMap.set(TEST_KEY+6,TEST_VAL+6);
		expectedKeys.add(TEST_KEY+6);
		resultKeys = testMap.keys();
		Collections.sort(resultKeys);
		assertEquals(expectedKeys,resultKeys);
		testMap.set(TEST_KEY+6,TEST_VAL+7);
		assertEquals(TEST_VAL+7,testMap.get(TEST_KEY+6));
		//test replace
		testMap.put("hi","bye");
		assertEquals(true,testMap.replace("hi", "hello"));
		assertEquals("hello", testMap.get("hi"));
		//test get/put
		assertEquals(null,testMap.get("key"));




	}
	
	/* Add more of your tests below */
	
}