package testcases;

import annotations.Before;
import annotations.Test;
import annotations.After;


public class SampleTestClass {


    @Before
    public void setUp() {
        System.out.println("Getting ready..");
        System.out.println("-----------------");
        throw new RuntimeException("Setup failed!");
    }


    @Test
    public void successfulTest() {
        System.out.println("Working test");
    }


    @Test
    public void failingTest() {
        System.out.println("Broken test");
        if (1 + 1 != 3) {
            throw new AssertionError("1 + 1 does not equal 3");
        }
    }


    @After
    public void tearDown() {
        System.out.println("-----------------");
        System.out.println("Finishing..");
    }
}
