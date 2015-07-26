package com.mpakhomov.tree;

/**
 *  A node of Red Black Tree (BST)
 *
 * @author mpakhomov
 * @since: 7/13/2015
 * @param <T> the type of keys
 */
public class RbtNode<T extends Comparable<T>> extends BstNode {
    boolean color = RedBlackTree.BLACK;

    public RbtNode(T key, boolean color) {
        super(key);
        this.color = color;
    }

    public RbtNode(T key, boolean color, RbtNode<T> parent) {
        super(key);
        this.color = color;
        this.parent = parent;
    }


    @Override
    public String toString() {
        return "key = " + key + ", left = " + (left != null ? left.key : "null") +
                ", right = " + (right != null ? right.key : "null") + ", parent = " +
                (parent != null ? parent.key : "null") +
                ", color = " + (color == RedBlackTree.BLACK ? "B" : "R");
    }

    @Override
    public String getKeyAsString() {
        return key + ":" + (color == RedBlackTree.BLACK ? "B" : "R");
    }
}
