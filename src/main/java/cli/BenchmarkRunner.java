package cli;

import algorithms.HeapSort;
import metrics.PerformanceTracker;
import java.io.*;
import java.util.*;

public class BenchmarkRunner {
    private static int[] gen(int n, String dist, long seed) {
        Random r = new Random(seed);
        int[] a = new int[n];
        switch (dist) {
            case "sorted":
                for (int i = 0; i < n; i++) a[i] = i; break;
            case "reversed":
                for (int i = 0; i < n; i++) a[i] = n - i; break;
            case "nearly":
                for (int i = 0; i < n; i++) a[i] = i;
                for (int k = 0; k < Math.max(1, n/100); k++) {
                    int i1 = r.nextInt(n), i2 = r.nextInt(n);
                    int t = a[i1]; a[i1] = a[i2]; a[i2] = t;
                }
                break;
            default:
                for (int i = 0; i < n; i++) a[i] = r.nextInt();
        }
        return a;
    }

    public static void main(String[] args) throws Exception {
        List<Integer> sizes = Arrays.asList(100, 1000, 10_000, 100_000);
        String[] dists = {"random","sorted","reversed","nearly"};
        String out = "metrics.csv";
        if (args.length > 0) out = args[0];

        PerformanceTracker tracker = new PerformanceTracker();
        try (PrintWriter pw = new PrintWriter(new FileWriter(out))) {
            pw.println(tracker.toCsvHeader());
            for (String dist : dists) {
                for (int n : sizes) {
                    int[] a = gen(n, dist, 123);
                    tracker.reset();
                    HeapSort hs = new HeapSort(tracker);
                    long t0 = System.nanoTime();
                    hs.sort(a);
                    long t1 = System.nanoTime();
                    pw.println(tracker.toCsvLine(n, dist, t1 - t0));
                }
            }
        }
        System.out.println("Saved CSV to " + out);
    }
}
