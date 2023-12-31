package com.bitsmi.springbootshowcase.utils.testdome;

import java.util.List;
import java.util.stream.Stream;

public class PlatformerV2
{
    private Tile currentTile;

    public PlatformerV2(int n, int position) {
        List<Tile> tiles = Stream.iterate(new Tile(0, null),
                        previous -> previous.position < n,
                        previousTile -> {
                            Tile nextTile = new Tile(previousTile.position + 1, previousTile);
                            previousTile.next = nextTile;
                            return nextTile;
                        })
                .toList();

        this.currentTile = tiles.get(position);
    }

    public static void main(String... args)
    {
        PlatformerV2 platformer = new PlatformerV2(6, 3);
        System.out.println(platformer.position()); // should print 3

        platformer.jumpLeft();
        System.out.println(platformer.position()); // should print 1

        platformer.jumpRight();
        System.out.println(platformer.position()); // should print 4
    }

    public void jumpLeft() {
        if(currentTile.hasPrevious() && currentTile.previous.hasPrevious()) {
            Tile newPosition = currentTile.previous.previous;
            currentTile.previous.next = currentTile.next;
            currentTile = newPosition;
        }
    }

    public void jumpRight() {
        if(currentTile.hasNext() && currentTile.next.hasNext()) {
            Tile newPosition = currentTile.next.next;
            currentTile.next.previous = currentTile.previous;
            currentTile = newPosition;
        }
    }

    public int position() {
        return currentTile.position;
    }

    static class Tile {
        int position;
        Tile previous;
        Tile next;

        public Tile(int position, Tile previous) {
            this.previous = previous;
            this.position = position;
        }

        public boolean hasNext() {
            return next!=null;
        }

        public boolean hasPrevious() {
            return previous!=null;
        }
    }
}
