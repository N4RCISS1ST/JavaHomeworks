package org.example;
import com.google.common.collect.Lists;
import com.google.common.base.Joiner;

import java.util.List;

public class HelloOtus {

    public static void main() {
        // Пример использования Lists.newArrayList()
        List<String> myList = Lists.newArrayList("One", "Two", "Three");
        System.out.println("List: " + myList);

        // Пример использования Joiner для объединения элементов списка в строку
        String result = Joiner.on(", ").join(myList);
        System.out.println("Concatenated elements of list: " + result);
    }
}
