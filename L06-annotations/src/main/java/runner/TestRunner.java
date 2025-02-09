package runner;

import annotations.After;
import annotations.Before;
import annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestRunner {
    private static int passedTests = 0;
    private static int totalTests = 0;
    private static int failedTests = 0;
    private static final Logger logger = Logger.getLogger(TestRunner.class.getName());
    public static void runTests(String className) {
        try {
            // Загружаем тестовый класс по имени
            Class<?> testClass = Class.forName(className);
            List<Method> beforeMethods = new ArrayList<>();
            List<Method> afterMethods = new ArrayList<>();
            List<Method> testMethods = new ArrayList<>();

            // Собираем методы с аннотациями
            for (Method method : testClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Before.class)) {
                    beforeMethods.add(method);
                } else if (method.isAnnotationPresent(After.class)) {
                    afterMethods.add(method);
                } else if (method.isAnnotationPresent(Test.class)) {
                    testMethods.add(method);
                }
            }

            // Выполняем тесты
            for (int i = 0; i < testMethods.size(); i++) {
                Method testMethod = testMethods.get(i);
                totalTests++;
                System.out.println("=====================");
                System.out.println("Test Number " + totalTests);
                System.out.println("=====================");
                Object testInstance = testClass.getDeclaredConstructor().newInstance();


                try {
                    // Выполнение методов @Before
                    for (Method beforeMethod : beforeMethods) {
                        beforeMethod.setAccessible(true);
                        beforeMethod.invoke(testInstance);
                    }

                    // Выполнение тестового метода
                    testMethod.setAccessible(true);
                    testMethod.invoke(testInstance);
                    System.out.println("[PASS] " + testMethod.getName());
                    passedTests++;

                } catch (InvocationTargetException e) {
                    System.out.println("[FAIL] " + testMethod.getName() + " - " + e.getCause().getMessage());
                    failedTests++;
                } catch (Exception e) {
                    System.out.println("[ERROR] Unexpected error in test: " + e.getMessage());
                } finally {
                    // Выполнение методов @After
                    for (Method afterMethod : afterMethods) {
                        try {
                            afterMethod.setAccessible(true);
                            afterMethod.invoke(testInstance);
                        } catch (Exception e) {
                            System.out.println("[ERROR in @After] " + e.getCause());
                        }
                    }
                }

            }

            // Итоговая статистика
            System.out.println("Total Tests: " + testMethods.size());
            System.out.println("Passed: " + passedTests);
            System.out.println("Failed: " + failedTests);

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Test execution failed", e);
        }
    }

    public static void main(String[] args)
    {
        if (args.length != 1) {
            System.out.println("Usage: TestRunner <full-class-name>");
            return;
        }
        runTests(args[0]);

    }
}
