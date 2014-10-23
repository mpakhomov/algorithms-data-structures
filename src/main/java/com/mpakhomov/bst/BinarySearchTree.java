package com.mpakhomov.bst;

public class BinarySearchTree {

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

    static public void printInOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        printInOrder(node.left);
        System.out.print(node.data + ", ");
        printInOrder(node.right);
    }
}
