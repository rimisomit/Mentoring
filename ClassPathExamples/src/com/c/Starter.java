package com.c;
import com.b.*;
import com.a.*;

/**
 * javac -d bin com/c/Starter.java
 * cd bin
 * java com.c.Starter
 * -----------------
 * java -jar <name>.jar
 */
public class Starter {
    public static void main(String[] args) {
        A1 a1 = new A1();
        A2 a2 = new A2();
        B1 b1 = new B1();
        B2 b2 = new B2();
        a1.printSomething();
        a2.printSomething();
        b1.printSomething();
        b2.printSomething();
    }
}
