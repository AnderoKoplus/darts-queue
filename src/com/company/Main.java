package com.company;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Main {
    public static int N = 6;

    public static void main(String[] args) {
        PriorityQueue<Board> queue;
        Comparator<Board> boardComparator = new BoardComparator();
        queue = new PriorityQueue<Board>(1, boardComparator);
        Board board = new Board(1);
        queue.add(board);

        Board item;
        int[] maxMap = new int[N + 1]; // allow +1 to make results more readable??
        while (null != (item = queue.poll())) {
//            System.out.println("Progressing with " + Arrays.toString(item.getRegions()) + " with score " + item.getScore());
            if (item.getHeuristicLimit() < maxMap[item.getSize()]) {
                continue;
            }
            if (item.getMax() > maxMap[item.getSize()]) {
                maxMap[item.getSize()] = item.getMax();
                System.out.println(item.getSize() + "\t" + item.getMax() + "\t" + Arrays.toString(item.getRegions()));
            } else {
//                continue;
            }

//            if (item.getSize() > 2) {
//                if (item.getMax() <= maxMap[item.getSize() - 2]) {
////                    System.out.println("Trimming bad branch: " + Arrays.toString(item.getRegions()) + "\t" + item.getMax());
////                    continue;
//                }
//            }
//            System.out.println("H: " + item.getHeuristicLimit() + "\t M: " + item.getMax() + "\t S: " + item.getSize() + "\t" + Arrays.toString(item.getRegions()));

            if (item.getSize() < N) {
                int[] regions = new int[item.getSize() + 1];
                int rangeMin, rangeMax;
//                if (1 == item.getSize()) {
//                    rangeMin = 2;
//                    rangeMax = item.getMax();
//                } else {
//                    rangeMin = (int)(item.getRegionLastValue() * 1.25);
//                    rangeMax = item.getRegionLastValue() * 2;
//                }
                rangeMin = item.getRegionLastValue() + 1;
                rangeMax = item.getRegionLastValue() * 3 + 1;
                System.arraycopy(item.getRegions(), 0, regions, 0, item.getSize());
//                System.out.println("Original: " + Arrays.toString(item.getRegions())+ " Range[" + rangeMin + "; " + rangeMax + "]");
                for (int i = rangeMin; i <= rangeMax; i++) {
                    regions[item.getSize()] = i;
                    Board newBoard = new Board(regions);
                    queue.add(newBoard);
                    System.out.println("Adding: " + Arrays.toString(regions) + "\t" + newBoard.getScore() + "\t" + newBoard.getMax());
                }
//                if (item.getSize() == 6) {
//                    System.exit(-1);
//                }
            }
        }
        for (int i = 1; i <= N; i++) {
            System.out.println(i + "\t" + maxMap[i]);
        }
    }

}
