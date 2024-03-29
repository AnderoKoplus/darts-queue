package com.company;

/**
 * Created by Andero on 18.05.2014.
 */
public class Board {
    private int size;
    private int[] regions;
    private int heuristicLimit = 0;
    private static int[] levelMax;
    private int max = 0;
    private int score;

    public Board(int i) {
        size = i;
        initializeRegions();
    }

    public Board(int[] regionMap) {
        this.regions = regionMap.clone();
        this.size = regionMap.length;
    }

    private void initializeRegions() {
        regions = new int[size];
        for (int i = 0; i < size; i++) {
            // increment by one
            regions[i] = i + 1;
        }
    }

    public int getHeuristicLimit() {
        if (0 == heuristicLimit) {
            heuristicLimit = calculateHeuristicLimit();
        }
        return heuristicLimit;
    }

    private int calculateHeuristicLimit() {
        return 3 * findMaxRegion() + 1;
    }
    public int getMax() {
        if (0 == max) {
            max = calculateMax();
        }
        return max;
    }

    private int findMaxRegion() {
        int max = 0;
        for (int i = 0; i < size; i++) {
            if (max < regions[i]) {
                max = regions[i];
            }
        }
        return max;
    }

    private int calculateMax() {
        int[] valueMap = new int[getHeuristicLimit() + 1]; // to compensate 0 pos
        int max = 0;

        // write initial values and multiples
        for (int i = 0; i < size; i++) {
            valueMap[regions[i]] = 1;
            valueMap[regions[i] * 2] = 2;
            valueMap[regions[i] * 3] = 3;
        }
        boolean changeMade;
        do {
            changeMade = false;
            for (int i = 1; i < valueMap.length; i++) {
                if (valueMap[i] < 3 && valueMap[i] > 0) {
                    for (int j = 0; j < size; j++) {
                        if (i + regions[j] < valueMap.length) {
                            if (valueMap[i + regions[j]] == 0) {
                                valueMap[i + regions[j]] = valueMap[i] + 1;
                                changeMade = true;
                            } else if (valueMap[i + regions[j]] > valueMap[i] + 1) {
                                valueMap[i + regions[j]] = valueMap[i] + 1;
                                changeMade = true;
                            }
                        }
                    }
                }
            }
        } while (changeMade);

        for (int i = 1; i < valueMap.length; i++) {
            if (valueMap[i] != 0) {
                max = i;
            } else {
                break;
            }
        }
        return max + 1;
    }

    public int[] getRegions() {
        return regions;
    }

//    public Board evolve() {
//        int pos = size -1;
//        int[] regionMap = regions.clone();
//        if (generateRegions(regionMap, pos)) {
//            return new Board(size, regionMap);
//        } else {
//            return null;
//        }
//    }
//
//    private boolean generateRegions(int[] regionMap, int pos) {
//        if (pos == 0) {
//            return false;
//        }
//        if (regionMap[pos] > regionMap[pos - 1] * 3) {
//            if (generateRegions(regionMap, pos - 1)) {
//                regionMap[pos - 1] = regionMap[pos - 2] + 1;
//                return true;
//            } else {
//                return false;
//            }
//        } else {
//            regionMap[pos]++;
//            return true;
//        }
//    }
//
    public int getSize() {
        return size;
    }

    public int getRegionLastValue() {
        return regions[size - 1];
    }


    public int getScore() {
        if (0 == score) {
            score = calculateScore();
        }
        return score;
    }

    private int calculateScore() {
        return this.getMax();
    }


}
