package com.mpakhomov.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Based on Introduction to Algorithms, third edition
 * By Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest and Clifford Stein
 *
 * @author: mpakhomov
 * @param <T> the type of keys maintained by this tree
 * created: 7/12/15
 *
 */
public class BinarySearchTree<T extends Comparable<T>> {

    protected int size;
    protected BstNode<T> root;

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
    public BstNode<T> getRoot() {
        return root;
    }

    /**
     * Insert a key to BST. Runs in O(logN) time
     *
     * @param key key to insert in the tree
     *
     */
    public void insert(T key) {
        BstNode<T> rbtEntry = new BstNode<T>(key);
        insert(rbtEntry);
    }



    /**
     * Insert an node to Binary Search Tree (BST).
     * Runs in O(log(h)) time, where h is the height of the tree
     *
     * @param node node to be inserted in the tree
     */
    void insert(BstNode<T> node) {
        if (root == null) {
            root = node;
            size++;
            return;
        }

        BstNode<T> curr = root;
        while (true) {
            if (curr.key.compareTo(node.key) < 0) {
                // search in the right subtree
                if (curr.right != null) {
                    curr = curr.right;
                } else {
                    // found
                    curr.right = node;
                    node.parent = curr;
                    size++;
                    break;
                }
            } else {
                // search in the left subtree
                if (curr.left != null) {
                    curr = curr.left;
                } else {
                    // found
                    curr.left = node;
                    node.parent = curr;
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
    public BstNode<T> search(T key) {
        BstNode<T> x = root;
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
    void transplant(BstNode<T> u, BstNode<T> v) {
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
     * @param <T> the type of keys maintained by this tree
     * @return returns the successor of the specified entry, or null if no such
     */
    static <T extends Comparable<T>> BstNode<T> successor(BstNode<T> x) {
        if (x == null) {
            return null;
        }

        // if x's right subtree is not empty, that the successor is the minimum element in that subtree
        if (x.right != null) {
            return treeMinimum(x.right);
        }

        // otherwise go up the tree until find x's ancestor which is a left child of its parent
        // in other words: go up the tree until we make a right turn
        BstNode<T> y = x.parent;
        while (y != null && x == y.right) {
            x = y;
            y = y.parent;
        }
        return y;
    }

    /**
     * Find the predecessor for the given node {@code x}
     * @param x tree entry we search the predecessor for
     * @param <T> the type of keys maintained by this tree
     * @return returns the predecessor of the specified entry, or null if no such
     */
    static <T extends Comparable<T>> BstNode<T> predecessor(BstNode<T> x) {
        if (x == null) {
            return null;
        }

        // if x's left subtree is not empty, that the predecessor is the maximum element in that subtree
        if (x.left != null) {
            return treeMaximum(x.left);
        }

        // otherwise go up the tree until find x's ancestor which is a right child of its parent
        // in other words: go up the tree until we make a left turn
        BstNode<T> y = x.parent;
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
     * @param x node to start from
     * @param <T> the type of keys maintained by this tree
     * @return
     */
    static <T extends Comparable<T>> BstNode<T> treeMinimum(BstNode<T> x) {
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
     * @param x node to start from
     * @param <T> the type of keys maintained by this tree
     * @return
     */
    static <T extends Comparable<T>> BstNode<T> treeMaximum(BstNode<T> x) {
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
     * @param <T>
     */
    static <T extends Comparable<T>> void traverseByLevelsIterative(BstNode<T> start) {
        if (start == null) {
            return;
        }

        int level = 0;
        List<BstNode<T>> nextLevel = new ArrayList<>();
        nextLevel.add(start);

        while (!nextLevel.isEmpty()) {
            final List<BstNode<T>> curLevel = new ArrayList<>(nextLevel);
            nextLevel.clear();
            level++;
            System.out.print("Level " + level + ":");
            for (final BstNode<T> node : curLevel) {
                System.out.print("\t" + node.key);
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
     * @param <T> the type of keys maintained by this tree
     */
    static <T extends Comparable<T>> void traverseByLevelsRecursive(BstNode<T> start) {
        if (start == null) {
            return;
        }
        traverseByLevelsRecursiveHelper(Arrays.asList(start), 0);
    }

    // a helper function for {@link #traverseByLevelsRecursive(Entry)}
    static <T extends Comparable<T>> void traverseByLevelsRecursiveHelper(final List<BstNode<T>> nodes, final int level) {
        final List<BstNode<T>> nextLevel = new ArrayList<>();
        System.out.print("Level " + level + ":");
        for (BstNode<T> node : nodes) {
            System.out.print("\t" + node.key);
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
    public static <T extends Comparable<T>> boolean isBst1(BstNode<T> root) {
        if (root == null) {
            return true;
        }

        if (root.left != null) {
            // verify that root is greater than any element in its left subtree
            T key = treeMaximum(root.left).key;
            if (root.key.compareTo(key) < 0) {
                return false;
            }
        }

        if (root.right != null) {
            // verify that root is smaller than any element in its right subtree
            T key = treeMinimum(root.right).key;
            if (root.key.compareTo(key) > 0) {
                return false;
            }
        }

        // now recursively verify that left and right subtrees are valid BST
        return isBst1(root.left) && isBst1(root.right);
    }

    // utility methods to avoid NPE when p is null
    static <T extends Comparable<T>> BstNode<T> parentOf(BstNode<T> p) {
        return (p == null ? null: p.parent);
    }

    static<T extends Comparable<T>> BstNode<T> leftOf(BstNode<T> p) {
        return (p == null) ? null: p.left;
    }

    static <T extends Comparable<T>> BstNode<T> rightOf(BstNode<T> p) {
        return (p == null) ? null: p.right;
    }

    static <T extends Comparable<T>> T keyOf(BstNode<T> p) {
        return (p == null) ? null : p.key;
    }

}
