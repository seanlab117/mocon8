package com.example.samplemenu;

import java.io.*;

// 객체 직렬화를 위해 Serializable 인터페이스 구현
class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    String name;
    int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + "}";
    }
}

