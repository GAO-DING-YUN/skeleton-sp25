

import edu.princeton.cs.algs4.Stack;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private static class BSTNode<K, V> {
        K key;
        V value;
        BSTNode<K, V> left;
        BSTNode<K, V> right;

        public BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
            left = null;
            right = null;
        }
    }

    private BSTNode<K, V> root;
    private int size;

    /**
     * Associates the specified value with the specified key in this map.
     * If the map already contains the specified key, replaces the key's mapping
     * with the value specified.
     *
     * @param key
     * @param value
     */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        } else {
            root = putHelper(root, key, value);
        }
    }

    private BSTNode<K, V> putHelper(BSTNode<K, V> root, K key, V value) {
        if (root == null) {
            size++;
            root = new BSTNode<>(key, value);
        } else if (root.key.compareTo(key) < 0) {
            root.right = putHelper(root.right, key, value);
        } else if (root.key.compareTo(key) > 0) {
            root.left = putHelper(root.left, key, value);
        } else {
            root.value = value;
        }
        return root;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public V get(K key) {
        return getHelper(root, key);
    }

    private V getHelper(BSTNode<K, V> root, K key) {
        V returnValue;
        if (root == null) {
            return null;
        } else if (root.key.compareTo(key) > 0) {
            returnValue = getHelper(root.left, key);
        } else if (root.key.compareTo(key) < 0) {
            returnValue = getHelper(root.right, key);
        } else {
            returnValue = root.value;
        }
        return returnValue;
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        } else {
            return keyHelper(root, key);
        }
    }

        private boolean keyHelper(BSTNode<K, V> root, K key) {
            boolean returnValue;
            if (root == null) {
                return false;
            } else if (root.key.compareTo(key) > 0) {
                returnValue = keyHelper(root.left, key);
            } else if (root.key.compareTo(key) < 0) {
                returnValue = keyHelper(root.right, key);
            } else {
                return true;
            }
            return returnValue;
        }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    public void printInOder() {
        printHelper(root);
    }

    private void printHelper(BSTNode<K, V> Node) {
        if (Node == null) {
            return;
        } else {
            printHelper(Node.left);
            System.out.println(Node.key);
            printHelper(Node.right);
        }
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        Collect(root, set);
        return set;
    }

    private void Collect(BSTNode<K, V> root, Set<K> set) {
        if (root == null) {
            return;
        } else {
            Collect(root.left, set);
            set.add(root.key);
            Collect(root.right, set);
        }
    }

    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public V remove(K key) {
        if (key == null) {
            return null;
        }
        V[] Value = (V[]) new Object[1];
        root = removeHelper(root, key, Value);
        return Value[0];
    }

    private BSTNode<K, V> removeHelper(BSTNode<K, V> node, K key, V[] rValue) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = removeHelper(node.left, key, rValue);
        } else if (cmp > 0) {
            node.right = removeHelper(node.right, key, rValue);
        } else {
            size -= 1;
            rValue[0] = node.value;
            if (node.right == null && node.right == null) {
                return null;
            }

            if (node.right == null) {
                return node.left;
            }

            if (node.left == null) {
                return node.right;
            }

            BSTNode<K, V> small = findSmall(node.right);
            node.key = small.key;
            node.value = small.value;
            V[] temp = (V[]) new Object[1];
            node.right = removeHelper(node.right, small.key, temp);
        }
        return node;
    }

    private BSTNode<K, V> findSmall(BSTNode<K, V> node)  {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
         return new BSTIterator(root);
     }
     private class BSTIterator implements Iterator<K> {
         Stack<BSTNode<K, V>> stack;
         BSTNode<K, V> current;

        public BSTIterator(BSTNode<K, V> root) {
            stack = new Stack<>();
            current = root;
            while (current == null) {
                stack.push(current);
                current = current.left;
            }
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
             return !stack.isEmpty();
         }

        /** * Returns the next element in the iteration. * * @return the next element in the iteration */
        @Override
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            BSTNode<K, V> Node = stack.pop();
            K key = Node.key;
            current = Node.right;
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            return key;
        }
     }
}