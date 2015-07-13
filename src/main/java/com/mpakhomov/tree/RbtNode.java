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

    @Override
    public String getKeyAsString() {
        return key + ":" + (color == RedBlackTree.BLACK ? "B" : "R");
    }
}
