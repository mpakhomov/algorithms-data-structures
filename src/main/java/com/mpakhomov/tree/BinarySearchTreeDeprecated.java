package com.mpakhomov.tree;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class BinarySearchTreeDeprecated {

    private TreeNode root;
    int size;

    public static class TreeNode {

        private int data;
        private TreeNode parent;
        private TreeNode left;
        private TreeNode right;

        public TreeNode(int data) {
            this.data = data;
            parent = left = right = null;
        }

        public void setLeft(TreeNode node) {
            this.left = node;
            node.parent = this;
        }

        public void setRight(TreeNode node) {
            this.right = node;
            node.parent = this;

        }

        public int getData() {
            return data;
        }

        public TreeNode getParent() {
            return parent;
        }

        public TreeNode getLeft() {
            return left;
        }

        public TreeNode getRight() {
            return right;
        }
    }

    public void add(int data) {
        TreeNode node = new TreeNode(data);

        if (root == null) {
            root = node;
            size++;
            return;
        }

        TreeNode parent, curr;
        parent = curr = root;

        while (curr != null) {
            parent = curr;
            if (curr.data < node.data) {
                // add to the right subtree
                curr = curr.right;
            } else {
                // add to the left subtree
                curr = curr.left;
            }
        }

        // add new node to the tree
        if (parent.data > node.data) {
            // add to the left
            parent.setLeft(node);
        } else {
            // add to the right
            parent.setRight(node);
        }

        size++;
    }

    public TreeNode find(int data) {
        if (root == null) {
            return null;
        }

        TreeNode curr = root;
        while (curr != null) {
            if (curr.getData() == data) {
                return curr;
            }
            if (data < curr.getData()) {
                curr = curr.getLeft();
            } else {
                curr = curr.getRight();
            }
        }
        return null;
    }



    public int getSize() {
        return size;
    }

    public TreeNode getRoot() {
        return root;
    }

    static public boolean isBST(TreeNode root) {
        return isBSTHelper(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    static private boolean isBSTHelper(TreeNode node, int lo, int hi) {
        if (node == null)
            return true;

        if (node.getData() < lo || node.getData() > hi)
            return false;

        return (isBSTHelper(node.left, lo, node.getData()) && isBSTHelper(node.right, node.getData(), hi));
    }

    static boolean isBSTInOrder(TreeNode root) {
        return isBSTInOrderHelper(root, Integer.MIN_VALUE);
    }

    static boolean isBSTInOrderHelper(TreeNode node, int prev) {
        if (node == null) {
            return true;
        }

        if (isBSTInOrderHelper(node.left, prev)) {
            if (node.getData() > prev) {
                return isBSTInOrderHelper(node.right, node.getData());
            } else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    static public List<Integer> TraverseInOrder(TreeNode node) {
        List<Integer> nodes = new ArrayList<>();
        return TraverseInOrderRecursive(node, nodes);
    }

    static private List<Integer> TraverseInOrderRecursive(TreeNode node, List<Integer> nodes) {
        if (node == null) {
            return nodes;
        }
        TraverseInOrderRecursive(node.left, nodes);
        nodes.add(node.data);
        TraverseInOrderRecursive(node.right, nodes);
        return nodes;
    }

    static public List<Integer> traversePreOrder(TreeNode node) {
        List<Integer> nodes = new ArrayList<>();
        return traversePreOrderRecursive(node, nodes);
    }

    static private List<Integer> traversePreOrderRecursive(TreeNode node, List<Integer> nodes) {
        if (node == null) {
            return nodes;
        }
        nodes.add(node.data);
        traversePreOrderRecursive(node.left, nodes);
        traversePreOrderRecursive(node.right, nodes);
        return nodes;
    }

    static public List<Integer> traversePostOrder(TreeNode node) {
        List<Integer> nodes = new ArrayList<>();
        return traversePostOrderRecusrive(node, nodes);
    }

    static private List<Integer> traversePostOrderRecusrive(TreeNode node, List<Integer> nodes) {
        if (node == null) {
            return nodes;
        }
        traversePostOrderRecusrive(node.left, nodes);
        traversePostOrderRecusrive(node.right, nodes);
        nodes.add(node.data);
        return nodes;
    }
}
