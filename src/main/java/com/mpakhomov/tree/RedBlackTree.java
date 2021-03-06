package com.mpakhomov.tree;


import java.util.Objects;

/**
 * Based on Introduction to Algorithms, third edition
 * By Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest and Clifford Stein
 * aka CLRS book
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
        RbtNode<T> node = (RbtNode)search(key);
        if (node != null) {
            rbtDelete1((RbtNode) node);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Based on JDK's implementation of {@link java.util.TreeMap}
     *
     * @param z node to be deleted
     */
    void rbtDelete(RbtNode<T> z) {
        if (size == 1 && z == root) {
            // we are the only mode
            root = null;
            size--;
            return;
        }

        // copy successor's element to z and then make z point to successor.
        if (z.left != null && z.right != null) {
            RbtNode<T> y = (RbtNode<T>)successor(z);
            z.key = y.key;
            z = y;
        } // z has 2 children

        // Start fixup at replacement node, if it exists.
        RbtNode<T> replacement = (RbtNode<T>)(z.left != null ? z.left : z.right);

        if (replacement != null) {
            // Link replacement to parent
            rbTransplant(z, replacement);

            // Fix replacement
            if (z.color == BLACK) {
                rbDeleteFixUp(replacement);
            }
        } else {
            //  No children. Use self as phantom replacement and unlink.
            if (z.color == BLACK) {
                rbDeleteFixUp(z);
            }
            unlinkFromParentAndNullify(z);
        }
        size--;
    }


    /**
     * Based on CLRS and JDK's implementation of {@link java.util.TreeMap}
     *
     * z - node to be deleted
     * y - z's successor
     * x - replacement element. it can be either of the following:
     *  - y's right child (when z has 2 children, y can have only one right child)
     *  - y, when y has no children use y as a phantom replacement
     *  - x.left or x.right, when z has only one child
     *  - x, when z has no children
     *
     *  General idea:
     *  - when z has one child, replace it with that child
     *  - when z has 2 children, replace z with it's successor y. Replace y with its right child if one exist,
     *    otherwise use y as a phantom replacement
     *  - when z has no children, use z as a phantom replacement element
     *
     * @param z node to be deleted
     */
    void rbtDelete1(RbtNode<T> z) {
        if (size == 1 && z == root) {
            // we are the only mode
            root = null;
            size--;
            return;
        }

        RbtNode<T> y = null, x = null;
        boolean isPhantomReplacement = false;
        boolean replacementColor = z.color;

        if (z.left != null && z.right == null) {
            // z has only left child. replace z with z.left
            x = (RbtNode<T>) z.left;
            rbTransplant(z, (RbtNode) z.left);
        } else if (z.left == null && z.right != null) {
            // z has only right child. replace z with z.right
            x = (RbtNode<T>) z.right;
            rbTransplant(z, (RbtNode) z.right);
        } else  if (z.left != null && z.right != null) {
            // z has 2 children
            y = (RbtNode<T>) successor(z);
            // replace z with its successor y
            z.key = y.key;
            // replacement element is a right child, because:
            // if a node in a binary search tree has two children, then its successor has no left child
            if (y.left != null) throw new RuntimeException("Can't happen");
            x = (RbtNode<T>) y.right;
            if (x != null) {
                rbTransplant(y, x);
            } else {
                // use z's successor y as a phantom replacement element
                x = y;
                isPhantomReplacement = true;
            }
            replacementColor = y.color;
        } else {
            // z has no children. use z as a phantom replacement element
            x = z;
            isPhantomReplacement = true;
        }

        if (replacementColor == BLACK) {
            rbDeleteFixUp(x);
        }
        if (isPhantomReplacement) {
            unlinkFromParentAndNullify(x);
        }
        size--;
    }

    private void unlinkFromParentAndNullify(RbtNode<T> p) {
        if (p.parent != null) {
            if (p == p.parent.left) {
                p.parent.left = null;
            } else if (p == p.parent.right) {
                p.parent.right = null;
            }
            p.parent = null;
        }
    }

    /**
     * restore Red Black Tree properties after deletion
     *
     * @param x a child of replacement node y
     */
    void rbDeleteFixUp(RbtNode<T> x) {
        while (x != root && colorOf(x) == BLACK) {
            if (x == leftOf(parentOf(x))) {

                // x is the left child
                RbtNode<T> w = rightOf(parentOf(x));
                if (colorOf(w) == RED) {
                    setColor(w, BLACK);                 // case 1
                    setColor(parentOf(x), RED);         // case 1
                    rotateLeft(parentOf(x));            // case 1
                    w = rightOf(parentOf(x));           // case 1
                }

                if (colorOf(leftOf(w)) == BLACK && colorOf(rightOf(w)) == BLACK) {
                    setColor(w, RED);                   // case 2
                    x = parentOf(x);                    // case 2
                } else {
                    if (colorOf(rightOf(w)) == BLACK) {
                        setColor(leftOf(w), BLACK);     // case 3
                        setColor(w, RED);               // case 3
                        rotateRight(w);                 // case 3
                        w = rightOf(parentOf(x));       // case 3
                    } // end of if (colorOf(rightOf(w)) == BLACK) {
                    setColor(w, colorOf(parentOf(x)));
                    setColor(parentOf(x), BLACK);       // case 4
                    setColor(rightOf(w), BLACK);        // case 4
                    rotateLeft(parentOf(x));            // case 4
                    x = (RbtNode<T>) root;              // case 4
                }

            } else { // else for if (x == leftOf(parentOf(x))) {
                // x is the right child

                RbtNode<T> w = leftOf(parentOf(x));
                if (colorOf(w) == RED) {
                    setColor(w, BLACK);                 // case 1
                    setColor(parentOf(x), RED);         // case 1
                    rotateRight(parentOf(x));           // case 1
                    w = leftOf(parentOf(x));            // case 1
                }

                if (colorOf(leftOf(w)) == BLACK && colorOf(rightOf(w)) == BLACK) {
                    setColor(w, RED);                   // case 2
                    x = parentOf(x);                    // case 2
                } else {
                    if (colorOf(leftOf(w)) == BLACK) {
                        setColor(rightOf(w), BLACK);    // case 3
                        setColor(w, RED);               // case 3
                        rotateLeft(w);                  // case 3
                        w = leftOf(parentOf(x));        // case 3
                    } // end of if (colorOf(leftOf(w)) == BLACK) {
                    setColor(w, colorOf(parentOf(x)));
                    setColor(parentOf(x), BLACK);       // case 4
                    setColor(leftOf(w), BLACK);         // case 4
                    rotateRight(parentOf(x));           // case 4
                    x = (RbtNode<T>) root;              // case 4
                }

            } // end of if (x == leftOf(parentOf(x))) {

        }
        setColor(x, BLACK);
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
        Objects.requireNonNull(x);
        BstNode<T> y = x.right; // set y, we want to exchange x and its right child y
        Objects.requireNonNull(y);
//        Objects.requireNonNull(y.key);
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
        Objects.requireNonNull(x);
        BstNode<T> y = x.left; // set y
        Objects.requireNonNull(y);
//        Objects.requireNonNull(y.key);
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
        Objects.requireNonNull(v);
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
        v.parent = u.parent;
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

    static <T extends Comparable<T>> T keyOf(RbtNode<T> p) {
        return (p == null) ? null : (T) p.key;
    }
}
