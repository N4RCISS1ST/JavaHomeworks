package runner;

import annotations.After;
import annotations.Before;
import annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestRunner {


    public static void runTests(String className) {
        try {
            Class<?> testClass = Class.forName(className);
            List<Method> beforeMethods = getAnnotatedMethods(testClass, Before.class);
            List<Method> afterMethods = getAnnotatedMethods(testClass, After.class);
            List<Method> testMethods = getAnnotatedMethods(testClass, Test.class);

            executeAllTests(testClass, beforeMethods, testMethods, afterMethods);

        } catch (Exception e) {
            System.err.println("Error loading test class: " + e.getMessage());
        }
    }


    private static List<Method> getAnnotatedMethods(Class<?> clazz, Class<? extends Annotation> annotation) {
        List<Method> annotatedMethods = new ArrayList<>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(annotation)) {
                method.setAccessible(true);
                annotatedMethods.add(method);
            }
        }
        return annotatedMethods;
    }


    private static void executeAllTests(Class<?> testClass, List<Method> beforeMethods,
                                        List<Method> testMethods, List<Method> afterMethods) {
        int passed = 0, failed = 0;

        for (int i = 0; i < testMethods.size(); i++) {
            Method testMethod = testMethods.get(i);
            Object testInstance = null;

            try {
                testInstance = testClass.getDeclaredConstructor().newInstance();
                try {
                    runLifecycleMethods(beforeMethods, testInstance);
                } catch (Exception e) {
                    System.out.printf("[%d/%d] [FAIL] %s - Failed in @Before method: %s%n",
                            i + 1, testMethods.size(), testMethod.getName(), e.getCause());
                    failed++;
                    runLifecycleMethods(afterMethods, testInstance);
                    continue;
                }


                System.out.printf("[%d/%d] Running test: %s%n", i + 1, testMethods.size(), testMethod.getName());
                testMethod.invoke(testInstance);
                passed++;
                System.out.printf("[%d/%d] [PASS] %s%n", i + 1, testMethods.size(), testMethod.getName());

            } catch (Exception e) {
                failed++;
                System.out.printf("[%d/%d] [FAIL] %s - %s%n", i + 1, testMethods.size(),
                        testMethod.getName(), e.getCause());
            } finally {
                if (testInstance != null) {
                    // Выполнение методов @After
                    runLifecycleMethods(afterMethods, testInstance);
                }
            }
        }

        printSummary(testMethods.size(), passed, failed);
    }


    private static void runLifecycleMethods(List<Method> methods, Object instance) {
        for (Method method : methods) {
            try {
                method.invoke(instance);
            } catch (Exception e) {
                throw new RuntimeException(e.getCause());
            }
        }
    }


    private static void printSummary(int total, int passed, int failed) {
        System.out.println("---------------");
        System.out.printf("Total Tests: %d, Passed: %d, Failed: %d%n", total, passed, failed);
    }
}
