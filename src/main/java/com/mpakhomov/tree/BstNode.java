package com.mpakhomov.tree;

/**
 * A node of Binary Search Tree (BST)
 *
 * @author mpakhomov
 * @since: 7/13/2015
 * @param <T> the type of keys
 */
public class BstNode<T extends Comparable<T>> {
    T key;
    BstNode<T> left;
    BstNode<T> right;
    BstNode<T> parent;

    public BstNode(T key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "key = " + key + ", left = " + (left != null ? left.key : "null") +
                ", right = " + (right != null ? right.key : "null") + ", parent = " +
                (parent != null ? parent.key : "null");
    }

    /**
     * To be overridden in subclasses. For example, in Red Black Tree we might want
     * to print key as {@code "<value>:<COLOR>"}
     *
     * @return string representation of the value
     */
    public String getKeyAsString() {
        return key.toString();
    }
}
