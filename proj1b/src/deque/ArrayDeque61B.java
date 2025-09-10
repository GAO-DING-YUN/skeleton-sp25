package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import  java.lang.Math;

public class ArrayDeque61B<T> implements Deque61B<T> {
    private T[] items;
    private int size;
    int nextLast = 1;
    int nextFirst;
    int arrSize = 8;

    @Override
    public void addFirst(T x) {
        if (size == arrSize) {
            resize(arrSize * 2);
        }
        size += 1;
        items[nextFirst] = x;
        nextFirst = Math.floorMod(nextFirst - 1, arrSize);
    }

    @Override
    public void addLast(T x) {
        if (size == arrSize) {
            resize(arrSize * 2);
        }
        size += 1;
        items[nextLast] = x;
        nextLast = Math.floorMod(nextLast + 1, arrSize);
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        int current = Math.floorMod(nextFirst + 1, arrSize);
        for (int i = 0; i < size; i++) {
            returnList.add(items[current]);
            current = Math.floorMod(current + 1, arrSize);
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()){
            return null;
        }
        size -= 1;
        T returnValue = items[Math.floorMod(nextFirst + 1, arrSize)];
        nextFirst = Math.floorMod(nextFirst + 1, arrSize);
        if (arrSize > 8 && size < arrSize / 4) {
            resize(arrSize / 2);
        }
        return returnValue;
    }

    @Override
    public T removeLast() {
        if (isEmpty()){
            return null;
        }
        size -= 1;
        T returnValue = items[Math.floorMod(nextLast - 1, arrSize)];
        nextLast = Math.floorMod(nextLast - 1, arrSize);
        if (arrSize > 8 && size < arrSize / 4) {
            resize(arrSize / 2);
        }
        return returnValue;
    }

    @Override
    public T get(int index) {
        int adjustedIndex;
        if (index < 0) {
            adjustedIndex = size + index;
        } else {
            adjustedIndex = index;
        }
        if (adjustedIndex < 0 || adjustedIndex >= size) {
            return null;
        }
        int pos = Math.floorMod(nextFirst + 1 + adjustedIndex, arrSize);
        return items[pos];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }

    public void resize(int newSize) {
        T[] newItems = (T[]) new Object[newSize];
        int current = Math.floorMod(nextFirst + 1, arrSize);
        for (int i = 0; i < size; i++) {
            newItems[i] = items[current];
            current = Math.floorMod(current + 1, arrSize);
        }
        items = newItems;
        arrSize = newSize;
        nextFirst = arrSize - 1;
        nextLast = size;
    }

    public ArrayDeque61B() {
        items = (T[]) new Object[arrSize];
    }

    @Override
    public Iterator<T> iterator() {
        return new Arrayiterator();
    }

    public class Arrayiterator implements Iterator<T> {
        private int wizPos;
        private int cnt;

        public Arrayiterator() {
            wizPos = Math.floorMod(nextFirst + 1, arrSize);
            cnt = 0;
        }

        @Override
        public boolean hasNext() {
            return cnt < size;
        }

        @Override
        public T next() {
            T returnValue = items[wizPos];
            wizPos = Math.floorMod(wizPos + 1, arrSize);
            cnt += 1;
            return returnValue;
        }
    }

    @Override
    public boolean equals(Object o){
        if (o == this) {
            return true;
        } else if (!(o instanceof ArrayDeque61B<?> obj)) {
            return false;
        } else if (this.size != obj.size) {
            return false;
        } else {
            for (int i = 0; i < size; i++) {
                if (get(i) != obj.get(i)) {
                    return false;
                }
            }
            return true;
        }
    }

    @Override
    public String toString() {
        boolean notFirst = false;
        StringBuilder ReturnString = new StringBuilder("[");
        for (T i : this) {
            if (notFirst) {
                ReturnString.append(", ");
            }
            notFirst = true;
            ReturnString.append(i);
        }
        ReturnString.append("]");
        return ReturnString.toString();
    }
}
