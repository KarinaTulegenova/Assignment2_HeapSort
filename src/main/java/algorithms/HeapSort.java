package algorithms;

import metrics.PerformanceTracker;

@SuppressWarnings("ClassCanBeRecord")
public class HeapSort {
    private final PerformanceTracker tracker;

    public HeapSort(PerformanceTracker tracker) {
        this.tracker = tracker;
    }

    public void sort(int[] a) {
        if (a == null) throw new IllegalArgumentException("array is null");
        if (a.length < 2) return;

        buildMaxHeap(a);
        for (int end = a.length - 1; end > 0; end--) {
            swap(a, 0, end);
            siftDownFrom(a, 0, end);
        }
    }

    private void buildMaxHeap(int[] a) {
        for (int i = (a.length >>> 1) - 1; i >= 0; i--) {
            siftDownFrom(a, i, a.length);
        }
    }

    private void siftDownFrom(int[] a, int i, int size) {
        while (true) {
            int left = (i << 1) + 1;
            if (left >= size) return;

            int right = left + 1;
            int largest = left;
            if (right < size && cmp(read(a, right), read(a, left)) > 0) largest = right;

            if (cmp(read(a, largest), read(a, i)) <= 0) return;

            swap(a, i, largest);
            i = largest;
        }
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
