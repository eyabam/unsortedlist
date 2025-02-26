package datastruct;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

class MyUnsortedListTest {

    @Test
    void testIsEmpty() {
        MyUnsortedList<String> list = MyUnsortedList.of();
        assertTrue(list.isEmpty(), "New list");

        list.append("firstelement");
        assertFalse(list.isEmpty(), "List after one add");

        list.remove(0);
        assertTrue(list.isEmpty(), "List after add and remove");
    }

    @Test
    void testPrepend() {
        MyUnsortedList<Integer> list = MyUnsortedList.of(2, 3);
        list.prepend(1);
        // Expected order: 1, 2, 3
        assertEquals(3, list.size(), "List should have 3 elements after prepend");
        MyUnsortedList<Integer> expected = MyUnsortedList.of(1, 2, 3);
        assertEquals(expected, list, "Prepending should add the element at the beginning");
    }

    @Test
    void testAppend() {
        MyUnsortedList<Integer> list = MyUnsortedList.of(1, 2);
        list.append(3);
        // Expected order: 1, 2, 3
        assertEquals(3, list.size(), "List should have 3 elements after append");
        MyUnsortedList<Integer> expected = MyUnsortedList.of(1, 2, 3);
        assertEquals(expected, list, "Appending should add the element at the end");
    }

    @Test
    void testInsert() {
        MyUnsortedList<String> list = MyUnsortedList.of("a", "c");
        // Insert in the middle
        list.insert("b", 1);
        MyUnsortedList<String> expected = MyUnsortedList.of("a", "b", "c");
        assertEquals(expected, list, "Inserting in the middle");

        // Insert at the beginning
        list.insert("start", 0);
        expected = MyUnsortedList.of("start", "a", "b", "c");
        assertEquals(expected, list, "Inserting at the beginning");

        // Insert at the end
        list.insert("end", list.size());
        expected = MyUnsortedList.of("start", "a", "b", "c", "end");
        assertEquals(expected, list, "Inserting at the end");
    }

    @Test
    void testPop() {
        MyUnsortedList<String> list = MyUnsortedList.of("first", "second", "third");
        String popped = list.pop();
        assertEquals("first", popped, "Pop should remove and return the first element");
        MyUnsortedList<String> expected = MyUnsortedList.of("second", "third");
        assertEquals(expected, list, "After pop, the remaining list");

        // Pop remaining elements
        list.pop();
        list.pop();
        assertTrue(list.isEmpty(), "List should be empty after removing all elements");

        // Attempting to pop from an empty list should throw an exception.
        assertThrows(EmptyListException.class, list::pop, "Popping an empty list should throw");
    }

    @Test
    void testPopLast() {
        MyUnsortedList<Integer> list = MyUnsortedList.of(1, 2, 3, 4);
        int popped = list.popLast();
        assertEquals(4, popped, "popLast should remove and return the last element");
        MyUnsortedList<Integer> expected = MyUnsortedList.of(1, 2, 3);
        assertEquals(expected, list, "After popLast, the remaining list");
    }

    @Test
    void testRemove() {
        MyUnsortedList<Character> list = MyUnsortedList.of('a', 'b', 'c', 'd');
        // Remove from the middle (removes 'c')
        char removed = list.remove(2);
        assertEquals('c', removed, "remove should return the removed element");
        MyUnsortedList<Character> expected = MyUnsortedList.of('a', 'b', 'd');
        assertEquals(expected, list, "After removal, the list order should be preserved");

        // Remove the first element using remove(0)
        removed = list.remove(0);
        assertEquals('a', removed, "Removing index 0 should remove the first element");
        expected = MyUnsortedList.of('b', 'd');
        assertEquals(expected, list, "List should be updated correctly after removal");

        // Remove the last element using remove(lastIndex)
        removed = list.remove(list.size() - 1);
        assertEquals('d', removed, "Removing the last element should work correctly");
        expected = MyUnsortedList.of('b');
        assertEquals(expected, list, "List should have one element left after removal");
    }

    @Test
    void testEquals() {
        MyUnsortedList<String> list1 = MyUnsortedList.of("x", "y", "z");
        MyUnsortedList<String> list2 = MyUnsortedList.of("x", "y", "z");
        assertEquals(list1, list2, "Lists with the same elements and order should be equal");

        list2.append("extra");
        assertNotEquals(list1, list2, "Lists of different sizes should not be equal");

        MyUnsortedList<String> list3 = MyUnsortedList.of("x", "z", "y");
        assertNotEquals(list1, list3, "Lists with the same elements in a different order should not be equal");
    }

    @Test
    void testToString() {
        MyUnsortedList<Integer> list = MyUnsortedList.of(10, 20, 30);
        String result = list.toString();
        assertTrue(result.contains("size = 3"), "toString should include the size of the list");
        assertTrue(result.contains("10"), "toString should include element 10");
        assertTrue(result.contains("20"), "toString should include element 20");
        assertTrue(result.contains("30"), "toString should include element 30");
    }

    @Test
    void testIndexOutOfBounds() {
        MyUnsortedList<Integer> list = MyUnsortedList.of(1, 2, 3);
        // Test insert with an invalid negative index
        assertThrows(IndexOutOfBoundsException.class, () -> list.insert(100, -1),
                "Inserting with a negative index should throw IndexOutOfBoundsException");
        // Test insert with an index greater than size
        assertThrows(IndexOutOfBoundsException.class, () -> list.insert(100, list.size() + 1),
                "Inserting with an index greater than size should throw IndexOutOfBoundsException");

        // Test remove with a negative index
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1),
                "Removing with a negative index should throw IndexOutOfBoundsException");
        // Test remove with an index equal to size (which is out of bounds)
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(list.size()),
                "Removing with index equal to size should throw IndexOutOfBoundsException");
    }

    @Test
    void testOfVarargsAndIterable() {
        // Using varargs
        MyUnsortedList<String> listFromVarargs = MyUnsortedList.of("a", "b", "c");
        // Using an Iterable (in this case, a List)
        MyUnsortedList<String> listFromIterable = MyUnsortedList.of(Arrays.asList("a", "b", "c"));
        assertEquals(listFromVarargs, listFromIterable, "Both creation methods should produce the same list");
    }

    @Test
    void testEqualsHandlesNullElements() {
        // Both lists contain null values in the same positions.
        MyUnsortedList<String> list1 = MyUnsortedList.of(null, "alpha", null);
        MyUnsortedList<String> list2 = MyUnsortedList.of(null, "alpha", null);

        // This call should not throw a NullPointerException and should indicate the lists are equal.
        try {
            assertEquals(list1, list2,
                    "Lists with matching null elements should be equal.");
        } catch (NullPointerException e) {
            fail("equals method threw NullPointerException when comparing lists with null elements.");
        }
    }

}