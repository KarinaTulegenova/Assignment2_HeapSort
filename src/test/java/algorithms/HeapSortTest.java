package algorithms;

import metrics.PerformanceTracker;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class HeapSortTest {

    private int[] gen(int n, String dist, long seed) {
        Random r = new Random(seed);
        int[] a = new int[n];
        switch (dist) {
            case "sorted":
                for (int i = 0; i < n; i++) a[i] = i;
                break;
            case "reversed":
                for (int i = 0; i < n; i++) a[i] = (n - 1) - i;
                break;
            case "nearly":
                for (int i = 0; i < n; i++) a[i] = i;
                for (int k = 0; k < Math.max(1, n / 100); k++) {
                    int i1 = r.nextInt(n), i2 = r.nextInt(n);
                    int t = a[i1]; a[i1] = a[i2]; a[i2] = t;
                }
                break;
            default:
                for (int i = 0; i < n; i++) a[i] = r.nextInt();
        }
        return a;
    }

    @Test
    void handlesTrivial() {
        PerformanceTracker t = new PerformanceTracker();
        HeapSort hs = new HeapSort(t);

        int[] a = {};
        hs.sort(a);
        assertArrayEquals(new int[]{}, a);

        int[] b = {42};
        hs.sort(b);
        assertTrue(HeapSort.isSorted(b));
    }

    @Test
    void sortsDuplicates() {
        PerformanceTracker t = new PerformanceTracker();
        HeapSort hs = new HeapSort(t);
        int[] a = {5, 5, 5, 5, 5, 5};
        hs.sort(a);
        assertTrue(HeapSort.isSorted(a));
    }

    @Test
    void sortsVariousDistributions() {
        long seed = 7L;
        String[] dist = {"random", "sorted", "reversed", "nearly"};
        int[] sizes = {100, 1_000, 10_000, 100_000};

        for (int n : sizes) {
            for (String d : dist) {
                int[] a = gen(n, d, seed);
                PerformanceTracker t = new PerformanceTracker();
                HeapSort hs = new HeapSort(t);
                hs.sort(a);
                assertTrue(HeapSort.isSorted(a), "failed on dist=" + d + ", n=" + n);
            }
        }
    }

    @Test
    void crossValidateWithJavaArraysSort() {
        Random r = new Random(1);
        int[] a = r.ints(50_000).toArray();
        int[] b = Arrays.copyOf(a, a.length);

        PerformanceTracker t = new PerformanceTracker();
        HeapSort hs = new HeapSort(t);

        hs.sort(a);
        Arrays.sort(b);

        assertArrayEquals(b, a);
    }


    @Test
    void benchmarkEmpiricalValidation() {
        long seed = 42L;
        String[] dist = {"random", "sorted", "reversed", "nearly"};
        int[] sizes = {100, 1_000, 10_000, 100_000};

        System.out.println("\n=== HeapSort Empirical Validation ===");
        System.out.println("dist\tn\tmillis\tcomparisons\tswaps\taccesses");

        warmup();

        for (String d : dist) {
            for (int n : sizes) {
                int[] a = gen(n, d, seed);
                PerformanceTracker t = new PerformanceTracker();
                HeapSort hs = new HeapSort(t);

                long t0 = System.nanoTime();
                hs.sort(a);
                long t1 = System.nanoTime();

                assertTrue(HeapSort.isSorted(a), "not sorted: dist=" + d + ", n=" + n);

                long ms = (t1 - t0) / 1_000_000L;
                System.out.printf("%s\t%d\t%d\t%d\t%d\t%d%n",
                        d, n, ms, t.getComparisons(), t.getSwaps(), t.getAccesses());
            }
        }
    }

    private void warmup() {
        int[] w = gen(20_000, "random", 1L);
        PerformanceTracker t = new PerformanceTracker();
        HeapSort hs = new HeapSort(t);
        hs.sort(w);
    }
}
