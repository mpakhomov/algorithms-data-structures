package com.mpakhomov.tree;


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

    // TODO:
    // Implement iterative in-order tree walk (traversion).
    // Hint: An easy solution uses a stack as an auxiliary data structure.
    // A more complicated, but elegant, solution uses no stack but assumes that we can test two pointers for equality
    // TODO:
    // Implement: breadth-first (level-order) tree traversal

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
     * Fix rbt properties after insertion of a new {@code entry}
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


    public static void main(String[] args) {
//        testTreeFromTheBook();
//        testInsertion();
//        testAnotherInsertion();
        testThirdInsertion();
    }

    static void testTreeFromTheBook() {
        RedBlackTree tree = new RedBlackTree();
        tree.put(11);
        tree.put(14);
        tree.put(2);
        tree.put(1);
        tree.put(7);
        tree.put(5);
        tree.put(8);
        tree.put(15);

        System.out.println("");
        printInOrder(tree.getRoot());
    }

    static void testThirdInsertion() {

        RedBlackTree tree = new RedBlackTree();
        tree.put(1);
        tree.put(2);
        tree.put(3);
        tree.put(4);
        tree.put(5);
        tree.put(6);
        tree.put(7);
        tree.put(8);

        System.out.println("");
        printInOrder(tree.getRoot());
        System.out.println("");
        System.out.println(treeMinimum(tree.getRoot()).key);
        System.out.println(treeMaximum(tree.getRoot()).key);
        System.out.println(successor(tree.getRoot()).key);
        BstNode<Integer> x = tree.search(1);
        System.out.println(keyOf(successor(x)));
        System.out.println(keyOf(predecessor(x)));
        x = tree.getRoot();
        System.out.println(keyOf(predecessor(x)));
        traverseByLevelsIterative(tree.getRoot());
        traverseByLevelsRecursive(tree.getRoot());
    }

    static void testInsertion() {

        RedBlackTree tree = new RedBlackTree();
        tree.put(11);
        tree.put(14);
        tree.put(2);
        tree.put(1);
        tree.put(7);
        tree.put(5);
        tree.put(8);
        tree.put(15);
        tree.put(4);

        System.out.println("");
        printInOrder(tree.getRoot());
    }

    static void testAnotherInsertion() {

        RedBlackTree tree = new RedBlackTree();
        tree.put(7);
        tree.put(3);
        tree.put(18);
        tree.put(10);
        tree.put(22);
        tree.put(88);
        tree.put(11);
        tree.put(26);
        tree.put(15);

        System.out.println("");
        printInOrder(tree.getRoot());
    }

    static private void printInOrder(BstNode node) {
        if (node == null) {
            return;
        }
        if (node == null ){
            return;
        }
        printInOrder(node.left);
        System.out.print(node.key + ", ");
        printInOrder(node.right);
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

}
