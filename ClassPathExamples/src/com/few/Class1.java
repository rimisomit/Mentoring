package com.few;
/**
 * cd src
 * javac -d bin com/few/Class1.java
 * cd bin
 * java com.few.Class1
 * ------------------
 * java -jar <name>.jar
 */
public class Class1 {
    public static void main(String[] args) {
        Class1 c1 = new Class1();
        Class2 c2 = new Class2();
        c1.writeText();
        c2.writeText();
    }
    public void writeText() {
        System.out.println("Text from " + this.toString());
    }
}
