package com.mpakhomov.tree;


import java.util.Objects;

/**
 * Based on Introduction to Algorithms, third edition
 * By Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest and Clifford Stein
 *
 * @author mpakhomov
 * @since: 7/6/2015
 * @param <T> the type of keys maintained by this tree
 */
public class RedBlackTree<T extends Comparable<T>> extends BinarySearchTree {

    public final static boolean BLACK = true;
    public final static boolean RED = false;

    /**
     * Insert an entry to Red Black Tree. Runs in O(logN) time
     *
     * @param key key to insert in the tree
     */
    public void put(T key) {
        RbtNode<T> rbtEntry = new RbtNode<T>(key, RED);
        // insert a node to RBT as it were an ordinary Binary Search Tree
        insert(rbtEntry);
        rbInsertFixUp(rbtEntry);
    }




    /**
     * Fix rbt properties after insertion of a new {@code node}
     *
     * @param z red-black tree entry
     */
    public void rbInsertFixUp(RbtNode<T> z) {
        z.color = RED;

        // in the book there is no z != root check, because if z is root, then its parent is
        // a black sentinel (fake) element Nil. In pseudo-code z.p.color == RED doesn't cause
        // any exceptions, but in real java code NPE will be thrown
        while(z != root && colorOf(parentOf(z)) == RED) {
            if (parentOf(z) == leftOf(parentOf(parentOf(z)))) {
                // z and its parent (p) is in the left subtree of its grandparent (g)

                // y is an uncle of z (it's a right child of its grandparent)
                BstNode<T> y = rightOf(parentOf(parentOf(z)));
                if (colorOf(y) == RED) {
                    // Case 1. recoloring
                    setColor(parentOf(z), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(z)), RED);
                    // move z two levels up in the tree. in other words: it moves a violation of '2 red in a row'
                    // property two levels up in the tree
                    z = (RbtNode) parentOf(parentOf(z));
                } else {
                    if (z == rightOf(parentOf(z))) {
                        // Case 2: z is a right child of its parent. Transform it to case 3
                        z = (RbtNode) parentOf(z);
                        rotateLeft(z);
                    }
                    // Case 3: z is a left child of its parent.
                    setColor(parentOf(z), BLACK);
                    setColor(parentOf(parentOf(z)), RED);
                    rotateRight(parentOf(parentOf(z)));
                    // Case 3 finally fixes all violations of RBT invariants
                }

            } else  {
                // z and its parent (p) is in the right subtree of its grandparent (g)

                // y is an uncle of z (it's a left child of its grandparent)
                BstNode<T> y = leftOf(parentOf(parentOf(z)));
                if (colorOf(y) == RED) {
                    // Case 1. recoloring
                    setColor(parentOf(z), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(z)), RED);
                    // move z two levels up in the tree
                    z = (RbtNode) parentOf(parentOf(z));
                } else {
                    if (z == leftOf(parentOf(z))) {
                        // Case 2: z is a left child of its parent. Transform it to case 3.
                        z = (RbtNode)parentOf(z);
                        rotateRight(z);
                    }
                    // Case 3: z is a right child of its parent
                    setColor(parentOf(z), BLACK);
                    setColor(parentOf(parentOf(z)), RED);
                    rotateLeft(parentOf(parentOf(z)));
                    // Case 3 finally fixes all violations of RBT invariants
                }

            } // if (parentOf(z) == leftOf(parentOf(parentOf(z)))) {

        } // while

        setColor(root, BLACK);
    }


    public boolean rbtDelete(T key) {
        //return super.delete(key);
        RbtNode<T> node = (RbtNode)search(key);
        if (node != null) {
            rbtDelete((RbtNode)node);
            return true;
        } else {
            return false;
        }
    }

    public void rbtDelete(RbtNode<T> z) {
        Objects.requireNonNull(z);
        RbtNode<T> y = z, x = null;
        boolean originalColorOfY = y.color;

        if (z.left == null) {
            x = (RbtNode<T>)z.right;
            rbTransplant(z, (RbtNode<T>)z.right);
        } else if (z.right == null) {
            x = (RbtNode<T>)z.left;
            rbTransplant(z, (RbtNode<T>)z.left);
        } else {
            // find the successor of z
            y = (RbtNode<T>)treeMinimum(z.right);
            originalColorOfY = y.color;
            x = (RbtNode<T>)y.right;
            if (parentOf(y) == z) {
                // Line below is from the book. In my implementation it's not needed,
                // because x.parent always points to y, unless x is not null
                //x.parent = y;
            } else {
                rbTransplant(y, rightOf(y));
                y.right = z.right;
                y.right.parent = y;
            }
            rbTransplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }

        if (originalColorOfY == BLACK) {
            rbDeleteFixUp(x);
        }
        size--;
    }

    void rbDeleteFixUp(RbtNode<T> x) {

    }


    /**
     * Left rotation is used to swap a parent node x with its <em>right</em> child y, so that
     * y becomes a new parent of x and x becomes y's right child. It's an operation symmetric to
     * {@link #rotateRight(BstNode)}
     * Left rotation preserves BST properties, i.e modified tree is still a Binary Search Tree
     *
     *       x                       y
     *     /  \                     / \
     *    a    y        =>        x    g
     *        / \                / \
     *       b   g             a    b
     *
     * Keys comparison: a < x < y < b < g
     *
     * @param x     a tree node (parent) to run the rotation on
     */
    void rotateLeft(BstNode<T> x) {
        BstNode<T> y = x.right; // set y, we want to exchange x and its right child y
        x.right = y.left; // turn y's left subtree into x's right subtree
        if (y.left != null) {
            y.left.parent = x;
        }
        y.parent = x.parent; // link x's parent to y
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        }
        y.left = x; // put x on y's left
        x.parent = y;
    }

    /**
     * Right rotation is used to swap a parent node x with its <em>left</em> child y, so that
     * y becomes a new parent of x and x becomes y's right child. It's an operation symmmetric to
     * {@link #rotateLeft(BstNode)}
     * Right rotation preserves BST properties, i.e modified tree is still a Binary Search Tree
     *
     *        x                 y
     *       / \              /  \
     *     y    g     =>    a     x
     *    / \                    / \
     *  a    b                  b   g
     *
     *  Keys comparison: a < y < b < x < g
     *
     * @param x     a tree node (parent) to run the rotation on
     */
    void rotateRight(BstNode<T> x) {
        BstNode<T> y = x.left; // set y
        x.left = y.right; // turn y's right subtree into x's left subtree
        if (y.right != null) {
            y.right.parent = x;
        }
        y.parent = x.parent; // link x's parent to y
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        }
        y.right = x; // put x on y's right
        x.parent = y;
    }

    /**
     * Modified version of {@link BinarySearchTree#transplant(BstNode, BstNode)}
     *
     * <p> u should be non null
     * v can be null
     *
     * @param u subtree which is to be replaced
     * @param v a replacement for subtree u
     */
    void rbTransplant(RbtNode<T> u, RbtNode<T> v) {
        Objects.requireNonNull(u);
        // both options are valid
        if (u.parent == null) {
//      if (u == root) {
            root = v;
        } else if (u == u.parent.left) {
            // u is a left child
            u.parent.left = v;
        } else {
            // u is a right child
            u.parent.right = v;
        }
        // in the book this assignment is unconditional, because they use an artificial sentinel Nil element
        // i don't use Nil, so I have to check for null to avoid NPE
        if (v != null) {
            v.parent = u.parent;
        }
    }



    // utility methods to avoid NPE when p is null. Also, they cast {@link BstEntry} to {@code RbtEntry},
    // so that I don't have to write boilerplate code in the algorithms implementation. I want to keep
    // RBT algorithms implementation as clean as it's possible
    static <T extends Comparable<T>> boolean colorOf(BstNode<T> p) {
        return (p == null) ? BLACK: ((RbtNode)p).color;
    }

    static <T extends Comparable<T>> void setColor(BstNode<T> p, boolean c) {
        if (p != null) {
            ((RbtNode)p).color = c;
        }
    }

    // utility methods to avoid NPE when p is null
    static <T extends Comparable<T>> RbtNode<T> parentOf(RbtNode<T> p) {
        return (p == null ? null : (RbtNode<T>) p.parent);
    }

    static <T extends Comparable<T>> RbtNode<T> leftOf(RbtNode<T> p) {
        return (p == null) ? null : (RbtNode<T>) p.left;
    }

    static <T extends Comparable<T>> RbtNode<T> rightOf(RbtNode<T> p) {
        return (p == null) ? null : (RbtNode<T>) p.right;
    }
}
