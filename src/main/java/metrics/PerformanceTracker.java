package metrics;

public class PerformanceTracker {
    private long comparisons;
    private long swaps;
    private long accesses;
    private long allocations;

    public void incComparisons() { comparisons++; }
    public void addComparisons(long x) { comparisons += x; }
    public void incSwaps() { swaps++; }
    public void addAccesses(long x) { accesses += x; }
    public void incAllocations() { allocations++; }

    public long getComparisons() { return comparisons; }
    public long getSwaps() { return swaps; }
    public long getAccesses() { return accesses; }
    public long getAllocations() { return allocations; }

    public void reset() { comparisons = swaps = accesses = allocations = 0; }

    public String toCsvHeader() {
        return "n,distribution,comparisons,swaps,accesses,allocations,nanos";
    }

    public String toCsvLine(int n, String dist, long nanos) {
        return n + "," + dist + "," + comparisons + "," + swaps + "," + accesses + "," + allocations + "," + nanos;
    }
}
