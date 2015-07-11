package com.mpakhomov.bst;


import java.util.*;

/**
 * Based on Introduction to Algorithms, third edition
 * By Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest and Clifford Stein
 *
 * @author mpakhomov
 * @since: 7/6/2015
 */
public class RedBlackTree<K extends Comparable<K>, V> {

    public final static boolean BLACK = true;
    public final static boolean RED = false;

    private int size;
    private Entry<K, V> root;

    // TODO:
    // Implement iterative in-order tree walk (traversion).
    // Hint: An easy solution uses a stack as an auxiliary data structure.
    // A more complicated, but elegant, solution uses no stack but assumes that we can test two pointers for equality
    // TODO:
    // Implement: breadth-first (level-order) tree traversal


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


    public int getSize() {
        return size;
    }

    public Entry<K, V> getRoot() {
        return root;
    }

    /**
     * Insert an entry to Binary Search Tree (BST). It's the first step of {@link #put(Comparable, Object)}
     * At this step we insert a node to RBT as it were an ordinary Binary Search Tree, we ignore coloring, balancing etc.
     * All RBT invariants are to be fixed later on.
     *
     * @param entry an entry to ne inserted
     */
    void insertToBst(Entry<K, V> entry) {
        if (root == null) {
            root = entry;
            size++;
            return;
        }

        Entry<K, V> curr = root;
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
     * Insert an entry into Red Black Tree
     *
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     *              *
     */
    public void put(K key, V value) {
        Entry<K, V> entry = new Entry<K, V>(key, value, RED);
        insertToBst(entry);
        rbInsertFixUp(entry);
    }

    // utility methods to avoid NPE when p is null
    private static <K,V> Entry<K,V> parentOf(Entry<K,V> p) {
        return (p == null ? null: p.parent);
    }

    private static <K,V> Entry<K,V> leftOf(Entry<K,V> p) {
        return (p == null) ? null: p.left;
    }

    private static <K,V> Entry<K,V> rightOf(Entry<K,V> p) {
        return (p == null) ? null: p.right;
    }

    private static <K,V> boolean colorOf(Entry<K,V> p) {
        return (p == null) ? BLACK: p.color;
    }

    private static <K,V> void setColor(Entry<K,V> p, boolean c) {
        if (p != null) {
            p.color = c;
        }
    }

    private static <K,V> K keyOf(Entry<K,V> p) {
        return (p == null) ? null : p.key;
    }

    Entry<K, V> treeSearch(K key) {
        Entry<K, V> x = root;
        while (x != null && key.compareTo(x.key) != 0) {
            if (key.compareTo(x.key) < 0) {
                // search in left subtree
                x = x.left;
            } else {
                // right
                x = x.right;
            }
        }
        return x;
    }

    /**
     * Fix rbt properties after insertion of a new {@code entry}
     *
     * @param z red-black tree entry
     */
    public void rbInsertFixUp(Entry<K, V> z) {
        z.color = RED;

        // in the book there is no z != root check, because if z is root, then its parent is
        // a black sentinel (fake) element Nil. In pseudo-code z.p.color == RED doesn't cause
        // any exceptions, but in real java code NPE will be thrown
        while(z != root && z.parent.color == RED) {
            if (parentOf(z) == leftOf(parentOf(parentOf(z)))) {
                // z and its parent (p) is in the left subtree of its grandparent (g)

                // y is an uncle of z (it's a right child of its grandparent)
                Entry<K, V> y = rightOf(parentOf(parentOf(z)));
                if (colorOf(y) == RED) {
                    // Case 1. recoloring
                    setColor(parentOf(z), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(z)), RED);
                    // move z two levels up in the tree. in other words: it moves a violation of '2 red in a row'
                    // property two levels up in the tree
                    z = parentOf(parentOf(z));
                } else {
                    if (z == rightOf(parentOf(z))) {
                        // Case 2: z is a right child of its parent. Transform it to case 3
                        z = parentOf(z);
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
                Entry<K, V> y = leftOf(parentOf(parentOf(z)));
                if (colorOf(y) == RED) {
                    // Case 1. recoloring
                    setColor(parentOf(z), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(z)), RED);
                    // move z two levels up in the tree
                    z = parentOf(parentOf(z));
                } else {
                    if (z == leftOf(parentOf(z))) {
                        // Case 2: z is a left child of its parent. Transform it to case 3.
                        z = parentOf(z);
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

        root.color = BLACK;
    }

    /**
     * Left rotation is used to swap a parent node x with its <em>right</em> child y, so that
     * y becomes a new parent of x and x becomes y's right child. It's an operation symmetric to
     * {@link #rotateRight(Entry)}
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
    void rotateLeft(Entry<K, V> x) {
        Entry<K, V> y = x.right; // set y, we want to exchange x and its right child y
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
     * {@link #rotateLeft(Entry)}
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
    void rotateRight(Entry<K, V> x) {
        Entry<K, V> y = x.left; // set y
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
     * Replace subtree u with subtree v. From the book: it replaces the subtree rooted at node u with
     * the subtree rooted at node v, node u's parent becomes node v's parent, and u's
     * parent ends up having v as its appropriate child
     *
     * @param u subtree which is to be raplaced
     * @param v a replacement for subtree u
     */
    void transplant(Entry<K, V> u, Entry<K, V> v) {
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
    static <K, V> Entry<K, V> successor(Entry<K, V> x) {
        if (x == null) {
            return null;
        }

        // if x's right subtree is not empty, that the successor is the minimum element in that subtree
        if (x.right != null) {
            return treeMinimum(x.right);
        }

        // otherwise go up the tree until find x's ancestor which is a left child of its parent
        // in other words: go up the tree until we make a right turn
        Entry<K, V> y = x.parent;
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
    static <K, V> Entry<K, V> predecessor(Entry<K, V> x) {
        if (x == null) {
            return null;
        }

        // if x's left subtree is not empty, that the predecessor is the maximum element in that subtree
        if (x.left != null) {
            return treeMaximum(x.left);
        }

        // otherwise go up the tree until find x's ancestor which is a right child of its parent
        // in other words: go up the tree until we make a left turn
        Entry<K, V> y = x.parent;
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
    static <K, V> Entry<K, V> treeMinimum(Entry<K, V> x) {
        if (x == null) {
            throw new IllegalArgumentException("x is null");
        }
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
    static <K, V> Entry<K, V> treeMaximum(Entry<K, V> x) {
        if (x == null) {
            throw new IllegalArgumentException("x is null");
        }
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
    static <K, V> void traverseByLevelsIterative(Entry<K, V> start) {
        if (start == null) {
            return;
        }

        int level = 0;
        Queue<Entry<K, V>> nextLevel = new ArrayDeque<>();
        nextLevel.add(start);

        while (!nextLevel.isEmpty()) {
            final Queue<Entry<K,V>> curLevel = new ArrayDeque<>(nextLevel);
            nextLevel.clear();
            level++;
            System.out.print("Level " + level + ":");
            for (final Entry<K, V> node : curLevel) {
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
    static <K, V> void traverseByLevelsRecursive(Entry<K, V> start) {
        if (start == null) {
            return;
        }
        traverseByLevelsRecursiveHelper(Arrays.asList(start), 0);
    }

    // a helper function for {@link #traverseByLevelsRecursive(Entry)}
    static <K, V> void traverseByLevelsRecursiveHelper(final List<Entry<K, V>> nodes, final int level) {
        final List<Entry<K,V>> nextLevel = new ArrayList<>();
        System.out.print("Level " + level + ":");
        for (Entry<K, V> node : nodes) {
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


    public static void main(String[] args) {
//        testTreeFromTheBook();
//        testInsertion();
//        testAnotherInsertion();
        testThirdInsertion();
    }

    static void testTreeFromTheBook() {
        RedBlackTree.Entry<Integer, Integer> root = new RedBlackTree.Entry<>(11, 11, BLACK);
        RedBlackTree.Entry<Integer, Integer> n14 = new RedBlackTree.Entry<>(14, 14, BLACK);
        RedBlackTree.Entry<Integer, Integer> n2 = new RedBlackTree.Entry<>(2, 2, RED);
        RedBlackTree.Entry<Integer, Integer> n1 = new RedBlackTree.Entry<>(1, 1, BLACK);
        RedBlackTree.Entry<Integer, Integer> n7 = new RedBlackTree.Entry<>(7, 7, BLACK);
        RedBlackTree.Entry<Integer, Integer> n5 = new RedBlackTree.Entry<>(5, 5, RED);
        RedBlackTree.Entry<Integer, Integer> n8 = new RedBlackTree.Entry<>(8, 8, RED);
        RedBlackTree.Entry<Integer, Integer> n15 = new RedBlackTree.Entry<>(15, 15, RED);

        RedBlackTree tree = new RedBlackTree();
        tree.insertToBst(root);
        tree.insertToBst(n14);
        tree.insertToBst(n2);
        tree.insertToBst(n1);
        tree.insertToBst(n7);
        tree.insertToBst(n5);
        tree.insertToBst(n8);
        tree.insertToBst(n15);

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
        Entry<Integer, Integer> x = tree.treeSearch(1);
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
