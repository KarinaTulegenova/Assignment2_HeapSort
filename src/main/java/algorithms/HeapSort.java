package algorithms;

import metrics.PerformanceTracker;

public final class HeapSort {
    private final PerformanceTracker tracker;

    public HeapSort(PerformanceTracker tracker) {
        if (tracker == null) throw new IllegalArgumentException("tracker is null");
        this.tracker = tracker;
    }

    public void sort(int[] a) {
        if (a == null) throw new IllegalArgumentException("array is null");
        int size = a.length;
        if (size < 2) return;

        buildMaxHeap(a, size);

        for (int end = size - 1; end > 0; end--) {
            swap(a, 0, end);
            siftDownFrom(a, 0, end);
        }
    }

    private void buildMaxHeap(int[] a, int size) {
        for (int i = (size >>> 1) - 1; i >= 0; i--) {
            siftDownFrom(a, i, size);
        }
    }

    private void siftDownFrom(int[] a, int i, int size) {
        int v = read(a, i);
        while (true) {
            int left = (i << 1) + 1;
            if (left >= size) break;

            int c = left;
            int cv = read(a, c);

            int right = left + 1;
            if (right < size) {
                int rv = read(a, right);
                if (cmp(rv, cv) > 0) {
                    c = right;
                    cv = rv;
                }
            }

            if (cmp(cv, v) <= 0) break;

            write(a, i, cv);
            i = c;
        }
        write(a, i, v);
    }

    private int cmp(int u, int v) {
        tracker.incComparisons();
        return Integer.compare(u, v);
    }

    private void swap(int[] a, int i, int j) {
        int t = read(a, i);
        write(a, i, read(a, j));
        write(a, j, t);
        tracker.incSwaps();
    }

    private int read(int[] a, int i) {
        tracker.addAccesses(1);
        return a[i];
    }

    private void write(int[] a, int i, int v) {
        tracker.addAccesses(1);
        a[i] = v;
    }

    public static boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++) {
            if (a[i - 1] > a[i]) return false;
        }
        return true;
    }
}
