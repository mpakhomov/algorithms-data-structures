package com.mpakhomov.tree;

import com.mpakhomov.seq.Sequence;

import java.util.*;

/**
 * Based on Introduction to Algorithms, third edition
 * By Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest and Clifford Stein
 *
 * @param <T> the type of keys maintained by this tree
 * @since: 7/12/15
 * @author: mpakhomov
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
     * Search for an element in the BST.
     * Runs in O(log(h)) time, where h is the size of the tree
     *
     * @param key key to find ine the tree
     * @return tree node if found, null otherwise
     */
    public BstNode<T> search(T key) {
        if (key == null) {
            return null;
        }

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
     * Delete a node from the tree associated with the given key
     * @param key key to be deleted
     * @return true on success, false otherwise
     */
    public boolean delete(T key) {
        BstNode<T> node = this.search(key);
        if (node != null) {
            delete(node);
            return false;
        } else {
            return false;
        }
    }

    /**
     * Delete a node from the tree.
     *
     * <p>Important: if a node in a binary search tree has two children, then its successor has
      * no left child and its predecessor has no right child
     *
     * @param node node to be deleted. It's assumed that node is non null. If it's null then NPE is thrown
     */
    public void delete(BstNode<T> node) {
        Objects.requireNonNull(node);
        if (node.left == null) {
            transplant(node, node.right);
        } else if (node.right == null) {
            transplant(node, node.left);
        } else {
            // y is the successor of node (we search it in node's right subtree)
            BstNode<T> y = treeMinimum(node.right);
            if (y.parent != node) {
                // it's guaranteed that y.left is null. replace y with its right subtree
                transplant(y, y.right);
                y.right = node.right;
                y.right.parent = y;
            }
            // finally, replace node with its successor y
            transplant(node, y);
            y.left = node.left;
            y.left.parent = y;
        }
        size--;
    }

    /**
     * Replace subtree u with subtree v. From the book: it replaces the subtree rooted at node u with
     * the subtree rooted at node v, node u's parent becomes node v's parent, and u's
     * parent ends up having v as its appropriate child
     *
     * <p> u should be non null
     * v can be null
     *
     * @param u subtree which is to be replaced
     * @param v a replacement for subtree u
     */
    void transplant(BstNode<T> u, BstNode<T> v) {
        Objects.requireNonNull(u);

        // Both variants are OK
        //if (u.parent == null) {
        if (u == root) {
            root = v;
        } else if (u == u.parent.left) {
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
     *
     * @param x   tree entry we search the successor for
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
     *
     * @param x   tree entry we search the predecessor for
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
     * @param x   node to start from
     * @param <T> the type of keys maintained by this tree
     * @return
     */
    static <T extends Comparable<T>> BstNode<T> treeMinimum(BstNode<T> x) {
        Objects.requireNonNull(x);
        while (x.left != null) {
            x = x.left;
        }
        return x;
    }

    /**
     * Find a maximum element in the subtree rooted at the given node {@code x}
     * We assume that x is not null
     *
     * @param x   node to start from
     * @param <T> the type of keys maintained by this tree
     * @return
     */
    static <T extends Comparable<T>> BstNode<T> treeMaximum(BstNode<T> x) {
        Objects.requireNonNull(x);
        while (x.right != null) {
            x = x.right;
        }
        return x;
    }

    /**
     * Breadth-first traversal (level by level). Iterative implementation
     *
     * @param root traverse BST starting at node {@code root}
     * @param <T>   the type of keys maintained by this tree
     * @return a list of list containing keys. list.get(0) returns level 0 (root),
     *         list.get(1) returns all nodes at level 1 and so on
     */
    static <T extends Comparable<T>> List<List<T>> traverseByLevelsIterative(BstNode<T> root) {
        List<List<T>> lol = new ArrayList<>();
        if (root == null) {
            return lol;
        }

        final List<BstNode<T>> nextLevel = new ArrayList<>();
        nextLevel.add(root);

        while (!nextLevel.isEmpty()) {
            final List<BstNode<T>> curLevel = new ArrayList<>(nextLevel);
            final List<T> curLevelKeys = new ArrayList<>();
            nextLevel.clear();
            for (final BstNode<T> node : curLevel) {
                curLevelKeys.add(node.key);
                if (node.left != null) {
                    nextLevel.add(node.left);
                }
                if (node.right != null) {
                    nextLevel.add(node.right);
                }
            }
            lol.add(curLevelKeys);
        }
        return lol;
    }

    /**
     * Traverse a tree level by level and build a list of lists that contains string representation of keys.
     * Each sub-list is a list of strings.
     *
     * If {@link BstNode#getKeyAsString()} is not overridden
     *
     * <pre>{@code
     *          1
     *        /  \
     *       2    3
     * }
     * </pre>
     *
     * the function returns List[["1", "2, 3"]]
     *
     * @param root traverse BST starting at node {@code root}
     * @param <T>   the type of keys maintained by this tree
     * @return a list of list of strings that contains a string representation of keys see {@link BstNode#getKeyAsString()}.
     *         list.get(0) returns level 0 (root), list.get(1) returns all nodes at level 1 and so on
     */
    static <T extends Comparable<T>> List<List<String>> traverseByLevelsAsString(BstNode<T> root) {
        List<List<String>> lol = new ArrayList<>();
        if (root == null) {
            return lol;
        }

        final List<BstNode<T>> nextLevel = new ArrayList<>();
        nextLevel.add(root);

        while (!nextLevel.isEmpty()) {
            final List<BstNode<T>> curLevel = new ArrayList<>(nextLevel);
            final List<String> curLevelKeys = new ArrayList<>();
            nextLevel.clear();
            for (final BstNode<T> node : curLevel) {
                curLevelKeys.add(node.getKeyAsString());
                if (node.left != null) {
                    nextLevel.add(node.left);
                }
                if (node.right != null) {
                    nextLevel.add(node.right);
                }
            }
            lol.add(curLevelKeys);
        }
        return lol;
    }

    /**
     * Breadth-first traversal (level by level). Recursive implementation
     *
     * @param root traverse BST starting at node {@code root}
     * @param <T>   the type of keys maintained by this tree
     * @return a list of list containing keys. list.get(0) returns level 0 (root),
     *         list.get(1) returns all nodes at level 1 and so on
     */
    static <T extends Comparable<T>> List<List<T>> traverseByLevelsRecursive(BstNode<T> root) {
        List<List<T>> lol = new ArrayList<>();
        if (root == null) {
            return lol;
        }
        traverseByLevelsRecursiveHelper(Arrays.asList(root), lol);
        return lol;
    }

    // a helper function for {@link #traverseByLevelsRecursive(Entry)}
    private static <T extends Comparable<T>> void traverseByLevelsRecursiveHelper(final List<BstNode<T>> curLevel,
                                                                          List<List<T>> acc) {
        final List<BstNode<T>> nextLevel = new ArrayList<>();
        final List<T> curLevelKeys = new ArrayList<>();
        for (BstNode<T> node : curLevel) {
            curLevelKeys.add(node.key);
            if (node.left != null) {
                nextLevel.add(node.left);
            }
            if (node.right != null) {
                nextLevel.add(node.right);
            }
        }
        acc.add(curLevelKeys);

        // recurse deeper only when we have more nodes to visit
        if (nextLevel.size() > 0) {
            traverseByLevelsRecursiveHelper(nextLevel, acc);
        }
    }


    /**
     * Validate if the given binary tree is a valid BST.
     * Version 1: correct, but inefficient. Runs in O(N2)
     *
     * @param root root of the tree
     * @return true if the tree is a valid BST, returns false otherwise
     */
    public static <T extends Comparable<T>> boolean isValidBst1(BstNode<T> root) {
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
        return isValidBst1(root.left) && isValidBst1(root.right);
    }

    /**
     * Validate if the given binary tree is a valid BST.
     * Version 2: correct???. It's an efficient algorithm. It uses in-order traversal
     * and visits each node only once. Runs in O(N)
     *
     * @param root root of the tree
     * @return true if the tree is a valid BST, returns false otherwise
     */
    public static <T extends Comparable<T>> boolean isValidBst2(BstNode<T> root) {
        return isValidBst2Helper(root, null);
    }

    private static <T extends Comparable<T>> boolean isValidBst2Helper(BstNode<T> node, BstNode<T> prev) {
        if (node == null)
            return true;

        if (!isValidBst2Helper(node.left, prev))
            return false;

        if (prev != null) {
            if (node.key.compareTo(prev.key) < 0) {
                return false;
            }
        }

        if (!isValidBst2Helper(node.right, node)) {
            return false;
        }
        return true;
    }

    /**
     * Validate if the given binary tree is a valid BST.
     * Version 3: correct. It's an efficient algorithm. It uses in-order traversal
     * and visits each node only once. Runs in O(N)
     * One drawback of this algorithm is that it requires to specify MIN_VALUE, MAX_VALUE for
     * type T. It's not possible to write truly generic implementation of this algorithm in Java,
     * because it's impossible to find out what is the MIN_VALUE and MAX_VALUE of generic type T
     *
     * @param root     root of the tree
     * @param minValue min value of type {@code T}. Examples: for integer it's {@link java.lang.Integer#MIN_VALUE}
     * @param maxValue max value of type {@code T}. Examples: for integer it's {@link java.lang.Integer#MAX_VALUE}
     * @return true if the tree is a valid BST, returns false otherwise
     */
    public static <T extends Comparable<T>> boolean isValidBst3(BstNode<T> root, final T minValue, final T maxValue) {
        return isValidBst3Helper(root, minValue, maxValue);
    }

    private static <T extends Comparable<T>> boolean isValidBst3Helper(BstNode<T> node, T min, T max) {
        if (node == null) {
            return true;
        }

        // node.key should be in range (min, max)
        if (node.key.compareTo(min) < 0 || node.key.compareTo(max) > 0) {
            return false;
        }

        // left should be in range (min, node.key), right right should be in range (node.key, max)
        return isValidBst3Helper(node.left, min, node.key) && isValidBst3Helper(node.right, node.key, max);
    }

    /**
     * Validate if the given binary tree is a valid BST.
     * Version 4: correct. It uses in-order traversal and auxiliary list to store results of the traversal,
     * visits each node only once. Runs in O(N) and uses O(N) memory (auxiliary list)
     *
     * @param root     root of the tree
     * @return true if the tree is a valid BST, returns false otherwise
     */
    public static <T extends Comparable<T>> boolean isValidBst4(BstNode<T> root) {
        List<T> inOrderTraversalResults = traverseInOrderIterative(root);
        return Sequence.isSorted(inOrderTraversalResults);
    }

    /**
     * In-order traversal, recursive version
     * @param root start traversal at node {@code root}
     * @return a list of visited keys
     */
    public static  <T extends Comparable<T>> List<T> traverseInOrderRecursive(BstNode<T> root) {
        final List<T> nodes = new ArrayList<>();
        return traverseInOrderRecursiveHelper(root, nodes);
    }

    private static  <T extends Comparable<T>> List<T> traverseInOrderRecursiveHelper(BstNode<T> node,
                                                                                     List<T> nodes) {
        if (node == null) {
            return nodes;
        }
        traverseInOrderRecursiveHelper(node.left, nodes);
        nodes.add(node.key);
        traverseInOrderRecursiveHelper(node.right, nodes);
        return nodes;
    }

    /**
     * In-order traversal, iterative version
     * @param root start traversal at node {@code root}
     * @return a list of visited keys
     */
    public static  <T extends Comparable<T>> List<T> traverseInOrderIterative(BstNode<T> root) {
        final List<T> nodes = new ArrayList<>();
        final Deque<BstNode<T>> stack = new ArrayDeque<>();

        BstNode<T> cur = root;
        while (!stack.isEmpty() || cur != null) {
            if (cur != null) {
                stack.push(cur);
                cur = cur.left;
            } else {
                cur = stack.pop();
                nodes.add(cur.key);
                cur = cur.right;
            }
        }
        return nodes;
    }


    /**
     * Pre-order traversal, recursive version
     * @param root start traversal at node {@code root}
     * @return a list of visited keys
     */
    public static  <T extends Comparable<T>> List<T> traversePreOrderRecursive(BstNode<T> root) {
        final List<T> nodes = new ArrayList<>();
        return traversePreOrderRecursiveHelper(root, nodes);
    }

    private static  <T extends Comparable<T>> List<T> traversePreOrderRecursiveHelper(BstNode<T> node,
                                                                                      List<T> nodes) {
        if (node == null) {
            return nodes;
        }
        nodes.add(node.key);
        traversePreOrderRecursiveHelper(node.left, nodes);
        traversePreOrderRecursiveHelper(node.right, nodes);
        return nodes;
    }

    /**
     * Post-order traversal, recursive version
     * @param root start traversal at node {@code root}
     * @return a list of visited keys
     */
    public static  <T extends Comparable<T>> List<T> traversePostOrderRecursive(BstNode<T> root) {
        final List<T> nodes = new ArrayList<>();
        return traversePostOrderRecursiveHelper(root, nodes);
    }

    private static  <T extends Comparable<T>> List<T> traversePostOrderRecursiveHelper(BstNode<T> node,
                                                                                       List<T> nodes) {
        if (node == null) {
            return nodes;
        }
        traversePostOrderRecursiveHelper(node.left, nodes);
        traversePostOrderRecursiveHelper(node.right, nodes);
        nodes.add(node.key);
        return nodes;
    }



    // utility methods to avoid NPE when p is null
    static <T extends Comparable<T>> BstNode<T> parentOf(BstNode<T> p) {
        return (p == null ? null : p.parent);
    }

    static <T extends Comparable<T>> BstNode<T> leftOf(BstNode<T> p) {
        return (p == null) ? null : p.left;
    }

    static <T extends Comparable<T>> BstNode<T> rightOf(BstNode<T> p) {
        return (p == null) ? null : p.right;
    }

    static <T extends Comparable<T>> T keyOf(BstNode<T> p) {
        return (p == null) ? null : p.key;
    }

}
