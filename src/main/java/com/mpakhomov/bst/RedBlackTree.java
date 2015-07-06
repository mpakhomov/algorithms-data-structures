package com.mpakhomov.bst;

/**
 * @author mpakhomov
 * @since: 7/6/2015
 */
public class RedBlackTree<K extends Comparable<K>, V> {

    private final static boolean BLACK = true;
    private final static boolean RED = false;

    private static class Entry<K, V> {
        private K key;
        private V value;
        private boolean color = BLACK;
        private Entry<K, V> left;
        private Entry<K, V> right;
        private Entry<K, V> parent;

        public Entry() {
        }

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public Entry(K key, V value, Entry<K, V> parent) {
            this(key, value);
            this.parent = parent;
        }

        public Entry(K key, V value, boolean color) {
            this(key, value);
            this.color = color;
        }

        public Entry(K key, V value, Entry<K, V> parent, boolean color) {
            this(key, value, parent);
            this.color = color;
        }
    }

    public static void main(String[] args) {
        RedBlackTree.Entry<Integer, Integer> root = new RedBlackTree.Entry<Integer, Integer>(4, 4, BLACK);
        RedBlackTree.Entry<Integer, Integer> two = new RedBlackTree.Entry<Integer, Integer>(2, 2, BLACK);
        RedBlackTree.Entry<Integer, Integer> six = new RedBlackTree.Entry<Integer, Integer>(6, 6, BLACK);
        root.right = six;
        root.left = two;
        two.parent = root;
        six.parent = root;

        RedBlackTree.Entry<Integer, Integer> one = new RedBlackTree.Entry<Integer, Integer>(1, 1, RED);
        RedBlackTree.Entry<Integer, Integer> three = new RedBlackTree.Entry<Integer, Integer>(3, 3, RED);
        one.parent = two;
        three.parent = two;
        two.left = one;
        two.right = three;
//        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
//        for (int i = 1; i <= 5; i++) {
//            treeMap.put(i, i);
//        }
        printInOrder(root);
    }

    static private void printInOrder(RedBlackTree.Entry node) {
        if (node == null) {
            return;
        }
        if (node == null ){
            return;
        }
        printInOrder(node.left);
        System.out.print(node.value+", ");
        printInOrder(node.right);
    }


}
