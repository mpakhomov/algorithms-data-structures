package com.mpakhomov.tree;


/**
 * Based on Introduction to Algorithms, third edition
 * By Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest and Clifford Stein
 *
 * @author mpakhomov
 * @since: 7/6/2015
 */
public class RedBlackTree<K extends Comparable<K>, V> extends BinarySearchTree {

    public final static boolean BLACK = true;
    public final static boolean RED = false;


    // TODO:
    // Implement iterative in-order tree walk (traversion).
    // Hint: An easy solution uses a stack as an auxiliary data structure.
    // A more complicated, but elegant, solution uses no stack but assumes that we can test two pointers for equality
    // TODO:
    // Implement: breadth-first (level-order) tree traversal


    // Red Black Tree Entry
    public static class RbtEntry<K, V> extends BstEntry {
        private boolean color = BLACK;

        public RbtEntry(K key, V value, boolean color) {
            super(key, value);
            this.color = color;
        }

        @Override
        protected String getValueAsString() {
            return value + ":" + (color == BLACK ? "B" : "R");
        }
    }


    /**
     * Insert an entry into Red Black Tree. Runs in O(logN) time
     *
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     *              *
     */
    public void put(K key, V value) {
        RbtEntry<K, V> rbtEntry = new RbtEntry<K, V>(key, value, RED);
        // insert a node to RBT as it were an ordinary Binary Search Tree
        insert(rbtEntry);
        rbInsertFixUp(rbtEntry);
    }

    // utility methods to avoid NPE when p is null. Also, they cast {@link BstEntry} to {@code RbtEntry},
    // so that I don't have to write boilerplate code in the algorithms implementation. I want to keep
    // my implementation as clean as it's possible
    static <K,V> boolean colorOf(BstEntry<K,V> p) {
        return (p == null) ? BLACK: ((RbtEntry)p).color;
    }

    static <K,V> void setColor(BstEntry<K,V> p, boolean c) {
        if (p != null) {
            ((RbtEntry)p).color = c;
        }
    }


    /**
     * Fix rbt properties after insertion of a new {@code entry}
     *
     * @param z red-black tree entry
     */
    public void rbInsertFixUp(RbtEntry<K, V> z) {
        z.color = RED;

        // in the book there is no z != root check, because if z is root, then its parent is
        // a black sentinel (fake) element Nil. In pseudo-code z.p.color == RED doesn't cause
        // any exceptions, but in real java code NPE will be thrown
        while(z != root && colorOf(parentOf(z)) == RED) {
            if (parentOf(z) == leftOf(parentOf(parentOf(z)))) {
                // z and its parent (p) is in the left subtree of its grandparent (g)

                // y is an uncle of z (it's a right child of its grandparent)
                BstEntry<K, V> y = rightOf(parentOf(parentOf(z)));
                if (colorOf(y) == RED) {
                    // Case 1. recoloring
                    setColor(parentOf(z), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(z)), RED);
                    // move z two levels up in the tree. in other words: it moves a violation of '2 red in a row'
                    // property two levels up in the tree
                    z = (RbtEntry) parentOf(parentOf(z));
                } else {
                    if (z == rightOf(parentOf(z))) {
                        // Case 2: z is a right child of its parent. Transform it to case 3
                        z = (RbtEntry) parentOf(z);
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
                BstEntry<K, V> y = leftOf(parentOf(parentOf(z)));
                if (colorOf(y) == RED) {
                    // Case 1. recoloring
                    setColor(parentOf(z), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(z)), RED);
                    // move z two levels up in the tree
                    z = (RbtEntry) parentOf(parentOf(z));
                } else {
                    if (z == leftOf(parentOf(z))) {
                        // Case 2: z is a left child of its parent. Transform it to case 3.
                        z = (RbtEntry)parentOf(z);
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
     * {@link #rotateRight(BstEntry)}
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
    void rotateLeft(BinarySearchTree.BstEntry<K, V> x) {
        BinarySearchTree.BstEntry<K, V> y = x.right; // set y, we want to exchange x and its right child y
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
     * {@link #rotateLeft(BinarySearchTree.BstEntry)}
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
    void rotateRight(BstEntry<K, V> x) {
        BstEntry<K, V> y = x.left; // set y
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
        RbtEntry<Integer, Integer> root = new RbtEntry<>(11, 11, BLACK);
        RbtEntry<Integer, Integer> n14 = new RbtEntry<>(14, 14, BLACK);
        RbtEntry<Integer, Integer> n2 = new RbtEntry<>(2, 2, RED);
        RbtEntry<Integer, Integer> n1 = new RbtEntry<>(1, 1, BLACK);
        RbtEntry<Integer, Integer> n7 = new RbtEntry<>(7, 7, BLACK);
        RbtEntry<Integer, Integer> n5 = new RbtEntry<>(5, 5, RED);
        RbtEntry<Integer, Integer> n8 = new RbtEntry<>(8, 8, RED);
        RbtEntry<Integer, Integer> n15 = new RbtEntry<>(15, 15, RED);

        RedBlackTree tree = new RedBlackTree();
        tree.insert(root);
        tree.insert(n14);
        tree.insert(n2);
        tree.insert(n1);
        tree.insert(n7);
        tree.insert(n5);
        tree.insert(n8);
        tree.insert(n15);

        System.out.println("");
        printInOrder(root);
    }

    static void testThirdInsertion() {

        RedBlackTree tree = new RedBlackTree();
        tree.put(1, 1);
        tree.put(2, 2);
        tree.put(3, 3);
        tree.put(4, 4);
        tree.put(5, 5);
        tree.put(6, 6);
        tree.put(7, 7);
        tree.put(8, 8);

        System.out.println("");
        printInOrder(tree.getRoot());
        System.out.println("");
        System.out.println(treeMinimum(tree.getRoot()).key);
        System.out.println(treeMaximum(tree.getRoot()).key);
        System.out.println(successor(tree.getRoot()).key);
        BinarySearchTree.BstEntry<Integer, Integer> x = tree.search(1);
        System.out.println(keyOf(successor(x)));
        System.out.println(keyOf(predecessor(x)));
        x = tree.getRoot();
        System.out.println(keyOf(predecessor(x)));
        traverseByLevelsIterative(tree.getRoot());
        traverseByLevelsRecursive(tree.getRoot());
    }

    static void testInsertion() {

        RedBlackTree tree = new RedBlackTree();
        tree.put(11, 11);
        tree.put(14, 14);
        tree.put(2, 2);
        tree.put(1, 1);
        tree.put(7, 7);
        tree.put(5, 5);
        tree.put(8, 8);
        tree.put(15, 15);
        tree.put(4, 4);

        System.out.println("");
        printInOrder(tree.getRoot());
    }

    static void testAnotherInsertion() {

        RedBlackTree tree = new RedBlackTree();
        tree.put(7, 7);
        tree.put(3, 3);
        tree.put(18, 18);
        tree.put(10, 10);
        tree.put(22, 22);
        tree.put(8, 8);
        tree.put(11, 11);
        tree.put(26, 26);
        tree.put(15, 15);

        System.out.println("");
        printInOrder(tree.getRoot());
    }

    static private void printInOrder(BstEntry node) {
        if (node == null) {
            return;
        }
        if (node == null ){
            return;
        }
        printInOrder(node.left);
        System.out.print(node.value + ", ");
        printInOrder(node.right);
    }





}
