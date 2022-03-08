package homework.tastus;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Tests running framework - finds and runds annotated methods in a given class
 */
public class TastusFramework {
    private static final List<Class<? extends Annotation>> ANNOTATION_TYPES =
            List.of(Before.class, After.class, Test.class);

    private final static String CLASS_NOT_FOUND_TEMPLATE = "Class not found: %s";
    private final static String CONSTRUCTOR_NOT_FOUND_TEMPLATE = "ZeroParamsConstructor not found for class %s";
    private final static String TEST_METHODS_TEMPLATE = "%d test methods found";
    private final static String TEST_PASSED_TEMPLATE = "Test PASSED for %s";
    private final static String TEST_FAILED_TEMPLATE = "Test FAILED for %s";

    public static void main(String... args) {
        TastusFramework framework = new TastusFramework();
        framework.checkClass("homework.TheSuspect");
    }

    public void checkClass(String className) {
        Class<?> checkedClass = getClass(className);
        Constructor<?> constructor = getZeroParamsConstructor(checkedClass);

        Map<Class<? extends Annotation>, List<Method>> annotatedMethodsMap =
                getAnnotatedMethodsMap(checkedClass.getDeclaredMethods());

        List<Method> testMethods = annotatedMethodsMap.get(Test.class);
        System.out.println(String.format(TEST_METHODS_TEMPLATE, testMethods.size()));
        testMethods.forEach(method -> runTestMethod(constructor, method,
                annotatedMethodsMap.get(Before.class), annotatedMethodsMap.get(After.class)));
    }

    private Class<?> getClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(String.format(CLASS_NOT_FOUND_TEMPLATE, className), e);
        }
    }

    private Constructor<?> getZeroParamsConstructor(Class<?> checkedClass) {
        for (Constructor<?> constructor : checkedClass.getConstructors()) {
            if (constructor.getParameterCount() == 0)
                return constructor;
        }

        throw new RuntimeException(String.format(CONSTRUCTOR_NOT_FOUND_TEMPLATE, checkedClass));
    }

    private Map<Class<? extends Annotation>, List<Method>> getAnnotatedMethodsMap(Method[] methods) {
        Map<Class<? extends Annotation>, List<Method>> annotationTypesMap = new HashMap<>();
        for (Class<? extends Annotation> annotationType : ANNOTATION_TYPES) {
            List<Method> methodsList = Stream.of(methods)
                    .filter(method -> method.isAnnotationPresent(annotationType))
                    .collect(Collectors.toList());

            annotationTypesMap.put(annotationType, methodsList);
        }

        return annotationTypesMap;
    }

    private void runTestMethod(Constructor<?> constructor, Method testMethod,
                                      List<Method> beforeMethods, List<Method> afterMethods)
    {
        try {
            Object instance = constructor.newInstance();
            invokeMethods(instance, beforeMethods);
            invokeMethods(instance, Collections.singletonList(testMethod));
            invokeMethods(instance, afterMethods);
        } catch (Exception e) {
            System.out.println(String.format(TEST_FAILED_TEMPLATE, testMethod));
            e.printStackTrace();
            return;
        }

        System.out.println(String.format(TEST_PASSED_TEMPLATE, testMethod));
    }

    private void invokeMethods(Object instance, List<Method> methods) {
        methods.forEach(method -> {
            try {
                method.invoke(instance);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

}
