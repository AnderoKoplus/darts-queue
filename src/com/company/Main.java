package com.company;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Main {
    public static int N = 25;
    public static long ctr = 1;

    public static void main(String[] args) {
        PriorityQueue<Board> queue;
        Comparator<Board> boardComparator = new BoardComparator();
        queue = new PriorityQueue<Board>(1, boardComparator);
        Board board = new Board(1);
        queue.add(board);

        Board item;
//        int[] maxMap = new int[N + 1]; // allow +1 to make results more readable??
        long max = 1;
        while (null != (item = queue.poll())) {
//            System.out.println("Processing: " + Arrays.toString(item.getRegions()));
//            System.out.println("Progressing with " + Arrays.toString(item.getRegions()) + " with score " + item.getScore());
//            if (item.getHeuristicLimit() < maxMap[item.getSize()]) {
//                continue;
//            }
//            if (item.getMax() > maxMap[item.getSize()]) {
//                maxMap[item.getSize()] = item.getMax();
//                System.out.println(item.getSize() + "\t" + item.getMax() + "\t" + Arrays.toString(item.getRegions()));
//            } else {
//                 continue;
//            }
            if (max < item.getMax()) {
                max = item.getMax();
                System.out.println(item.getSize() + "\t" + item.getMax() + "\t" + Arrays.toString(item.getRegions()) + "\t" + queue.size() + "\t" + ctr);
            }

            if (item.canBranch(N)) {
                if (max > item.getHeuristicMax()) {
                    System.out.println("Trimming Root: " + Arrays.toString(item.getRegions()) + "\t" + max + "\t" + item.getHeuristicMax());
                    continue;
                }
                int[] regions = new int[item.getSize() + 1];
                System.arraycopy(item.getRegions(), 0, regions, 0, item.getSize());
//                System.out.println("Original: " + Arrays.toString(item.getRegions())+ " Range[" + item.getNextRegionMin() + "; " + item.getNextRegionMax() + "]");
                for (int i = item.getNextRegionMin(); i <= item.getNextRegionMax(); i++) {
                    regions[item.getSize()] = i;
                    Board newBoard = new Board(regions);
                    if (max > newBoard.getHeuristicMax()) {
//                        if (newBoard.getRegions().length < N - 1) {
//                            System.out.println("Skipping board: " + Arrays.toString(newBoard.getRegions())
//                                    + "\t" + max
//                                    + "\t" + newBoard.getHeuristicMax()
//                                    + "\t" + queue.size()
//                                    + "\t" + newBoard.getRegions().length + "/" + N
//                            );
//                        }
                        continue;
                    }
                    newBoard.addBaseScore(item.getScore());
//                    System.out.println(Arrays.toString(newBoard.getRegions())+ "\t" + newBoard.getHeuristicMax());
                    newBoard.setScore(++ctr);
                    queue.add(newBoard);
//                    System.out.println("Adding: " + Arrays.toString(regions) + "\t" + newBoard.getScore() + "\t" + queue.size());
                }
//                if (item.getSize() == 6) {
//                    System.exit(-1);
//                }
            }
            if (0 == queue.size()) {
                break;
            }
        }
        System.out.println("-----");
//        for (int i = 1; i <= N; i++) {
//            System.out.println(i + "\t" + maxMap[i]);
            System.out.println(N + "\t" + max);
//        }
    }

}
