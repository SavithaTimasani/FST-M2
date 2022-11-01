package activities;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Activity1 {
    static ArrayList<String> list;

    @BeforeEach
    void setUp() {
        // Initialize a new ArrayList
        list = new ArrayList<String>();

        // Add values to the list
        list.add("alpha"); // at index 0
        list.add("beta"); // at index 1
    }

    @Test
    public void insertTest() {

        // Assert size of list
        assertEquals(2, list.size(), "Wrong size");
        list.add(2,"charlie");
        assertEquals(3, list.size(), "Wrong size");

        // Assert individual elements
        assertEquals("alpha", list.get(0), "Wrong element");
        assertEquals("beta", list.get(1), "Wrong element");
        assertEquals("charlie", list.get(2), "Wrong element");
    }

    @Test
    public void replaceTest() {

        // Assert size of list
        assertEquals(2, list.size(), "Wrong size");
        list.add(2,"charlie");
        assertEquals(3, list.size(), "Wrong size");

        // Replace element in ArrayList
        list.set(1, "sigma");
        // Assert individual elements
        assertEquals("alpha", list.get(0), "Wrong element");
        assertEquals("sigma", list.get(1), "Wrong element");
        assertEquals("charlie", list.get(2), "Wrong element");
    }
}
