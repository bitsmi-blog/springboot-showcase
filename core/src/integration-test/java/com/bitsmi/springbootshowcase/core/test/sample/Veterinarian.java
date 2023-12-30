package com.bitsmi.springbootshowcase.core.test.sample;

import java.util.LinkedList;
import java.util.Queue;

public class Veterinarian {
    private LinkedList<String> queue = new LinkedList<>();

    public void accept(String petName) {
        queue.add(petName);
    }

    public String heal() {
        if(queue.isEmpty()) {
            return null;
        }
        return queue.pop();
    }

    public static void main(String[] args) {
        Veterinarian veterinarian = new Veterinarian();
        veterinarian.accept("Barkley");
        veterinarian.accept("Mittens");
        System.out.println(veterinarian.heal()); // Should print: Barkley
        System.out.println(veterinarian.heal()); // Should print: Mittens
    }
}
