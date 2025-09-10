package deque;
import java.util.Comparator;
import java.util.Iterator;

public class Maximizer61B {
    /**
     * Returns the maximum element from the given iterable of comparables.
     * You may assume that the iterable contains no nulls.
     *
     * @param iterable  the Iterable of T
     * @return          the maximum element
     */
    public static <T extends Comparable<T>> T max(Iterable<T> iterable) {
        Iterator<T> current = iterable.iterator();
        if (!current.hasNext()) {
            return null;
        }
        T maxValue = current.next();
        while (current.hasNext()) {
            T compare = current.next();
            if (compare.compareTo(maxValue) > 0) {
                maxValue = compare;
            }
        }
        return maxValue;
    }

    /**
     * Returns the maximum element from the given iterable according to the specified comparator.
     * You may assume that the iterable contains no nulls.
     *
     * @param iterable  the Iterable of T
     * @param comp      the Comparator to compare elements
     * @return          the maximum element according to the comparator
     */
    public static <T> T max(Iterable<T> iterable, Comparator<T> comp) {
        Iterator<T> current = iterable.iterator();
        if (!current.hasNext()) {
            return null;
        }
        T maxValue = current.next();
        while (current.hasNext()) {
            T compare = current.next();
            if (comp.compare(compare, maxValue) > 0) {
                maxValue = compare;
            }
        }
        return maxValue;
    }

    public static void main(String[] args) {
        // The style checker will complain about this main method, feel free to delete.

         ArrayDeque61B<Integer> ad = new ArrayDeque61B<>();
         ad.addLast(5);
         ad.addLast(12);
         ad.addLast(17);
         ad.addLast(23);
         System.out.println(max(ad));
    }
}
