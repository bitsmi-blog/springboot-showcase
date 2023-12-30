package com.bitsmi.springbootshowcase.core.test.sample;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Plataformes
{
    private static List<int[]> positions;
    private static int currentPosition = 0;

    public static void main(String... args)
    {
        positions = Stream.iterate(1, index -> index < 10, index -> index+1)
                .map(index -> new int[]{Math.max(0, index - 1), Math.min(10, index + 1)})
                .toList();
    }

    private static void moveRight()
    {
        int previousPositionIndex = positions.get(currentPosition)[0];
        int[] previousPosition = positions.get(previousPositionIndex);
        int nextPositionIndex = positions.get(currentPosition)[1];
        int[] nextPosition = positions.get(nextPositionIndex);


    }
}
