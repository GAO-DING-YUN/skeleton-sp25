import java.util.List;
import java.util.ArrayList; // import the ArrayList class


public class LinkedListDeque61B<T> implements Deque61B<T>{
    private int size = 0;
    private Node sentinel = new Node();

    private class Node {
        public T item;
        public Node prev;
        public Node next;
    }

    @Override
    public void addFirst(T x) {
       Node add = new Node();
       add.item = x;
       size += 1;
       add.prev = sentinel;
       add.next = sentinel.next;
       sentinel.next.prev = add;
       sentinel.next = add;
    }

    @Override
    public void addLast(T x) {
        Node add = new Node();
        add.item = x;
        size += 1;
        add.prev = sentinel.prev;
        add.next = sentinel;
        sentinel.prev.next = add;
        sentinel.prev = add;
    }

    @Override
    public List<T> toList() {
        List<T> returnlist = new ArrayList<>();
        Node head = sentinel.next;
        while (head != sentinel) {
            returnlist.add(head.item);
            head = head.next;
        }
        return returnlist;
    }

    @Override
    public boolean isEmpty() {
        return sentinel == sentinel.next;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        Node first = sentinel.next;
        sentinel.next = first.next;
        first.next.prev = sentinel;
        return first.item;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        Node last = sentinel.prev;
        sentinel.prev = last.prev;
        last.prev.next = sentinel;
        return last.item;
    }

    @Override
    public T get(int index) {
        if (size < index || index < 1) {
            return null;
        }
        Node sign = sentinel.next;
        while (index > 1) {
            sign = sign.next;
            index -= 1;
        }
        return sign.item;
    }

    @Override
    public T getRecursive(int index) {
        if (size < index || index < 1) {
            return null;
        }  else {
            return togetRecursive(sentinel.next, index);
        }
    }

    private T togetRecursive(Node now, int index){
        if (index == 1){
            return now.item;
        } else {
            return togetRecursive(now.next, index - 1);
        }
    }

    public LinkedListDeque61B(){
       sentinel.next = sentinel;
       sentinel.prev = sentinel;
    }
}
