package tools.skiplist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

/**
 * Hello world!
 *
 */
public class Main
{
    /**
     * Compare the skiplist with the treemap, which is an implementation of
     * red-black tree.
     * @param testSize the size of the test.
     */
    public static void benchmark(int testSize) {
        TreeMap<Integer, Integer> tree = new TreeMap<>();
        Skiplist list = new Skiplist();
        List<Integer> data = new ArrayList<>();

        for(int i = 0; i < testSize; i++) {
            data.add(i);
        }
        Collections.shuffle(data);

        long stop1 = System.nanoTime();
        for(int i = 0; i < testSize; i++)
            list.add(data.get(i));
        long stop2 = System.nanoTime();
        for(int i = 0; i < testSize; i++)
            list.search(data.get(i));
        long stop3 = System.nanoTime();
        for(int i = 0; i < testSize; i++)
            list.erase(data.get(i));
        long stop4 = System.nanoTime();
        for(int i = 0; i < testSize; i++)
            tree.put(data.get(i), 1);
        long stop5 = System.nanoTime();
        for(int i = 0; i < testSize; i++)
            tree.containsKey(data.get(i));
        long stop6 = System.nanoTime();
        for(int i = 0; i < testSize; i++)
            tree.remove(data.get(i));
        long stop7 = System.nanoTime();



        int nano2sec = 1000000000;
        System.out.println("Skiplist benchmark:");
        System.out.println("with test size: " + list.getSize());
        System.out.println("--- " + testSize + " times INSERTATION: " + (double) (stop2 - stop1) / nano2sec + "sec");
        System.out.println("--- " + testSize + " times SEARCH     : " + (double) (stop3 - stop2) / nano2sec + "sec");
        System.out.println("--- " + testSize + " times ERASE      : " + (double) (stop4 - stop3) / nano2sec + "sec");

        System.out.println("TreeMap benchmark:");
        System.out.println("with test size: " + list.getSize());
        System.out.println("--- " + testSize + " times INSERTATION: " + (double) (stop5 - stop4) / nano2sec + "sec");
        System.out.println("--- " + testSize + " times SEARCH     : " + (double) (stop6 - stop5) / nano2sec + "sec");
        System.out.println("--- " + testSize + " times ERASE      : " + (double) (stop7 - stop6) / nano2sec + "sec");
    }


    public static void main(String[] args) {
        // test the skiplist
        benchmark(200000);
    }
}
