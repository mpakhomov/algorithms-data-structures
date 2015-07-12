package com.mpakhomov.tree;

import com.sun.org.apache.xpath.internal.*;

import java.util.*;

/**
 * Based on Introduction to Algorithms, third edition
 * By Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest and Clifford Stein
 *
 * @author: mpakhomov
 * created: 7/12/15
 */
public class BinarySearchTree<K extends Comparable<K>, V> {

    protected int size;
    protected BstEntry<K, V> root;

    // Binary Search Tree Entry
    public static class BstEntry<K, V> {
        protected K key;
        protected V value;
        protected BstEntry<K, V> left;
        protected BstEntry<K, V> right;
        protected BstEntry<K, V> parent;

        public BstEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public BstEntry(K key, V value, BstEntry<K, V> parent) {
            this(key, value);
            this.parent = parent;
        }

        /**
         * To be overridden in subclasses. For example, in Red Black Tree we might want
         * to print value as {@code "<value>:<COLOR>"}
         *
         * @return string representation of the value
         */
        protected String getValueAsString() {
            return value.toString();
        }
    }

    /**
     * get size of the tree
     *
     * @return size of the tree
     */
    public int getSize() {
        return size;
    }

    /**
     * get root of the tree
     *
     * @return root of the tree
     */
    public BstEntry<K, V> getRoot() {
        return root;
    }

    /**
     * Insert an entry to Binary Search Tree (BST).
     * Runs in O(log(h)) time, where h is the height of the tree
     *
     * @param entry entry to be inserted in the tree
     */
    void insert(BstEntry<K, V> entry) {
        if (root == null) {
            root = entry;
            size++;
            return;
        }

        BstEntry<K, V> curr = root;
        while (true) {
            if (curr.key.compareTo(entry.key) < 0) {
                // search in the right subtree
                if (curr.right != null) {
                    curr = curr.right;
                } else {
                    // found
                    curr.right = entry;
                    entry.parent = curr;
                    size++;
                    break;
                }
            } else {
                // search in the left subtree
                if (curr.left != null) {
                    curr = curr.left;
                } else {
                    // found
                    curr.left = entry;
                    entry.parent = curr;
                    size++;
                    break;
                }
            }
        }
    }


    /**
     *  Search for an element in the BST.
     *  Runs in O(log(h)) time, where h is the size of the tree
     *
     * @param key key to find ine the tree
     * @return tree node if found, null otherwise
     */
    public BstEntry<K, V> search(K key) {
        BstEntry<K, V> x = root;
        while (x != null && key.compareTo(x.key) != 0) {
            if (key.compareTo(x.key) < 0) {
                // search in the left subtree
                x = x.left;
            } else {
                // right
                x = x.right;
            }
        }
        return x;
    }

    /**
     * Replace subtree u with subtree v. From the book: it replaces the subtree rooted at node u with
     * the subtree rooted at node v, node u's parent becomes node v's parent, and u's
     * parent ends up having v as its appropriate child
     *
     * @param u subtree which is to be replaced
     * @param v a replacement for subtree u
     */
    void transplant(BstEntry<K, V> u, BstEntry<K, V> v) {
        if (u == root) {
            root = v;
        } else if (u == u.parent.left){
            // u is a left child
            u.parent.left = v;
        } else {
            // u is a right child
            u.parent.right = v;
        }
        if (v != null) {
            v.parent = u.parent;
        }
    }

    /**
     * Find the successor for the given node {@code x}
     * @param x tree entry we search the successor for
     * @param <K>
     * @param <V>
     * @return returns the successor of the specified entry, or null if no such
     */
    static <K, V> BstEntry<K, V> successor(BstEntry<K, V> x) {
        if (x == null) {
            return null;
        }

        // if x's right subtree is not empty, that the successor is the minimum element in that subtree
        if (x.right != null) {
            return treeMinimum(x.right);
        }

        // otherwise go up the tree until find x's ancestor which is a left child of its parent
        // in other words: go up the tree until we make a right turn
        BstEntry<K, V> y = x.parent;
        while (y != null && x == y.right) {
            x = y;
            y = y.parent;
        }
        return y;
    }

    /**
     * Find the predecessor for the given node {@code x}
     * @param x tree entry we search the predecessor for
     * @param <K>
     * @param <V>
     * @return returns the predecessor of the specified entry, or null if no such
     */
    static <K, V> BstEntry<K, V> predecessor(BstEntry<K, V> x) {
        if (x == null) {
            return null;
        }

        // if x's left subtree is not empty, that the predecessor is the maximum element in that subtree
        if (x.left != null) {
            return treeMaximum(x.left);
        }

        // otherwise go up the tree until find x's ancestor which is a right child of its parent
        // in other words: go up the tree until we make a left turn
        BstEntry<K, V> y = x.parent;
        while (y != null && x == y.left) {
            x = y;
            y = y.parent;
        }
        return y;
    }

    /**
     * Find a minimum element in the subtree rooted at the given node {@code x}
     * We assume that x is not null
     *
     * @param x
     * @return
     */
    static <K, V> BstEntry<K, V> treeMinimum(BstEntry<K, V> x) {
        Objects.requireNonNull(x);
        while (x.left != null ) {
            x = x.left;
        }
        return x;
    }

    /**
     * Find a maximum element in the subtree rooted at the given node {@code x}
     * We assume that x is not null
     *
     * @param x
     * @return
     */
    static <K, V> BstEntry<K, V> treeMaximum(BstEntry<K, V> x) {
        Objects.requireNonNull(x);
        while (x.right != null ) {
            x = x.right;
        }
        return x;
    }

    /**
     * Breadth-first traversal (level by level). Iterative implementation
     *
     * @param start traverse BST starting at node {@start}
     * @param <K>
     * @param <V>
     */
    static <K, V> void traverseByLevelsIterative(BstEntry<K, V> start) {
        if (start == null) {
            return;
        }

        int level = 0;
        Queue<BstEntry<K, V>> nextLevel = new ArrayDeque<>();
        nextLevel.add(start);

        while (!nextLevel.isEmpty()) {
            final Queue<BstEntry<K,V>> curLevel = new ArrayDeque<>(nextLevel);
            nextLevel.clear();
            level++;
            System.out.print("Level " + level + ":");
            for (final BstEntry<K, V> node : curLevel) {
                System.out.print("\t" + node.value);
                if (node.left != null ) {
                    nextLevel.add(node.left);
                }
                if (node.right != null) {
                    nextLevel.add(node.right);
                }
            }
            System.out.print("\n");
        }
    }

    /**
     * Breadth-first traversal (level by level). Recursive implementation
     *
     * @param start traverse BST starting at node {@start}
     * @param <K>
     * @param <V>
     */
    static <K, V> void traverseByLevelsRecursive(BstEntry<K, V> start) {
        if (start == null) {
            return;
        }
        traverseByLevelsRecursiveHelper(Arrays.asList(start), 0);
    }

    // a helper function for {@link #traverseByLevelsRecursive(Entry)}
    static <K, V> void traverseByLevelsRecursiveHelper(final List<BstEntry<K, V>> nodes, final int level) {
        final List<BstEntry<K,V>> nextLevel = new ArrayList<>();
        System.out.print("Level " + level + ":");
        for (BstEntry<K, V> node : nodes) {
            System.out.print("\t" + node.value);
            if (node.left != null ) {
                nextLevel.add(node.left);
            }
            if (node.right != null) {
                nextLevel.add(node.right);
            }
        }
        System.out.print("\n");

        // recurse deeper only when we have more nodes to visit
        if (nextLevel.size() > 0) {
            traverseByLevelsRecursiveHelper(nextLevel, level + 1);
        }
    }


    /**
     * Validate if a binary tree is a valid BST.
     * Version 1: correct, but inefficient
     *
     * @param root root of the tree
     * @return
     */
    public static <K extends Comparable<K>, V> boolean isBst1(BstEntry<K, V> root) {
        if (root == null) {
            return true;
        }

        if (root.left != null) {
            // verify that root is greater than any element in its left subtree
            K k = treeMaximum(root.left).key;
            if (root.key.compareTo(k) < 0) {
                return false;
            }
        }

        if (root.right != null) {
            // verify that root is smaller than any element in its right subtree
            K k = treeMinimum(root.right).key;
            if (root.key.compareTo(k) > 0) {
                return false;
            }
        }

        // now recursively verify that left and right subtrees are correct
        return isBst1(root.left) && isBst1(root.right);
    }

    // utility methods to avoid NPE when p is null
    static <K,V> BstEntry<K,V> parentOf(BstEntry<K,V> p) {
        return (p == null ? null: p.parent);
    }

    static <K,V> BstEntry<K,V> leftOf(BstEntry<K,V> p) {
        return (p == null) ? null: p.left;
    }

    static <K,V> BstEntry<K,V> rightOf(BstEntry<K,V> p) {
        return (p == null) ? null: p.right;
    }

    static <K,V> K keyOf(BstEntry<K,V> p) {
        return (p == null) ? null : p.key;
    }
}
