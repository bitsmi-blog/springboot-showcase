package com.bitsmi.springbootshowcase.utils.testdome;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Platformer {
    private List<Integer> tiles;
    private int position;

    public Platformer(int n, int position) {
        this.tiles = Stream.iterate(0, count -> count<n, count -> count + 1)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        this.position = position;
    }

    public void jumpLeft() {
        int newPosition = position - 2;
        if(newPosition >= 0) {
            tiles.remove(position);
            position = newPosition;
        }
    }

    public void jumpRight() {
        int newPosition = position + 1;
        if(newPosition < tiles.size()) {
            tiles.remove(position);
            position = newPosition;
        }
    }

    public int position() {
        return tiles.get(position);
    }

    public static void main(String[] args) {
        Platformer platformer = new Platformer(6, 3);
        System.out.println(platformer.position()); // should print 3

        platformer.jumpLeft();
        System.out.println(platformer.position()); // should print 1

        platformer.jumpRight();
        System.out.println(platformer.position()); // should print 4
    }
}
