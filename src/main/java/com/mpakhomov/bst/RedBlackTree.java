package com.mpakhomov.bst;


/**
 * Based on Introduction to Algorithms, third edition
 * By Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest and Clifford Stein
 *
 * @author mpakhomov
 * @since: 7/6/2015
 */
public class RedBlackTree<K extends Comparable<K>, V> {

    private final static boolean BLACK = true;
    private final static boolean RED = false;

    private int size;
    private Entry<K, V> root;


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
    void insertIntoBst(Entry<K, V> entry) {
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
        insertIntoBst(entry);
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

    /**
     * Fix rbt properties after insertion of a new {@code entry}
     *
     * @param z red-black tree entry
     */
    public void rbInsertFixUp(Entry<K, V> z) {
        z.color = RED;

        // TODO: is it needed to check that z!= null && z != root
        while(/*z != null &&*/ z != root && z.parent.color == RED) {
            if (parentOf(z) == leftOf(parentOf(parentOf(z)))) { // TODO: is NPE possible without leftOf/parentOf?
                // z and its parent (p) is in the left subtree of its grandparent (g)

                // y is an uncle of z (its a right child of its grandparent)
                Entry<K, V> y = rightOf(parentOf(parentOf(z)));
                if (colorOf(y) == RED) {
                    // Case 1. recoloring
                    setColor(parentOf(z), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(z)), RED);
                    // move z two levels up in the tree
                    z = parentOf(parentOf(z));
                } else {
                    if (z == rightOf(parentOf(z))) {
                        // Case 2: z is a right child of its parent
                        z = parentOf(z);
                        rotateLeft(z);
                    }
                    // Case 3: z is a left child of its parent
                    setColor(parentOf(z), BLACK);
                    setColor(parentOf(parentOf(z)), RED);
                    rotateRight(parentOf(parentOf(z)));
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
                        // Case 2: z is a left child of its parent
                        z = parentOf(z);
                        rotateRight(z);
                    }
                    // Case 3: z is a left child of its parent
                    setColor(parentOf(z), BLACK);
                    setColor(parentOf(parentOf(z)), RED);
                    rotateLeft(parentOf(parentOf(z)));
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


    public static void main(String[] args) {
        // rbt 1, 2, 3, 4, 5, 6, 7
        RedBlackTree.Entry<Integer, Integer> root = new RedBlackTree.Entry<>(4, 4, BLACK);
        RedBlackTree.Entry<Integer, Integer> two = new RedBlackTree.Entry<>(2, 2, BLACK);
        RedBlackTree.Entry<Integer, Integer> six = new RedBlackTree.Entry<>(6, 6, BLACK);
        RedBlackTree.Entry<Integer, Integer> one = new RedBlackTree.Entry<>(1, 1, RED);
        RedBlackTree.Entry<Integer, Integer> three = new RedBlackTree.Entry<>(3, 3, RED);
        RedBlackTree.Entry<Integer, Integer> five = new RedBlackTree.Entry<>(5, 5, RED);
        RedBlackTree.Entry<Integer, Integer> seven = new RedBlackTree.Entry<>(7, 7, RED);

        RedBlackTree<Integer, Integer> tree = new RedBlackTree<>();
        tree.insertIntoBst(root);
        tree.insertIntoBst(two);
        tree.insertIntoBst(six);
        tree.insertIntoBst(one);
        tree.insertIntoBst(three);
        tree.insertIntoBst(five);
        tree.insertIntoBst(seven);

        printInOrder(tree.getRoot());

        tree.rotateLeft(six);

        printInOrder(tree.getRoot());

        tree.rotateRight(seven);

        printInOrder(tree.getRoot());

        testTreeFromTheBook();
        testInsertion();
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
        tree.insertIntoBst(root);
        tree.insertIntoBst(n14);
        tree.insertIntoBst(n2);
        tree.insertIntoBst(n1);
        tree.insertIntoBst(n7);
        tree.insertIntoBst(n5);
        tree.insertIntoBst(n8);
        tree.insertIntoBst(n15);

        System.out.println("");
        printInOrder(root);
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
