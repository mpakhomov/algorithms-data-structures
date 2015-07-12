package com.mpakhomov.tree;

import java.util.*;

/**
 * Original version found here: http://stackoverflow.com/a/4973083/1817777
 */
class BTreePrinter  {

    public static <K extends Comparable<K>, V> void printNode(BinarySearchTree.BstEntry<K, V> root) {
        int maxLevel = BTreePrinter.maxLevel(root);

        printNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }

    private static <K extends Comparable<K>, V> void printNodeInternal(List<BinarySearchTree.BstEntry<K, V>> nodes, int level, int maxLevel) {
        if (nodes.isEmpty() || BTreePrinter.isAllElementsNull(nodes))
            return;

        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        BTreePrinter.printWhitespaces(firstSpaces);

        List<BinarySearchTree.BstEntry<K, V>> newNodes = new ArrayList<>();
        for (BinarySearchTree.BstEntry<K, V> node : nodes) {
            if (node != null) {
                System.out.print(node.value);
//                System.out.print(node.getValueAsString());
                newNodes.add(node.left);
                newNodes.add(node.right);
            } else {
                newNodes.add(null);
                newNodes.add(null);
                System.out.print(" ");
            }

            BTreePrinter.printWhitespaces(betweenSpaces);
        }
        System.out.println("");

        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < nodes.size(); j++) {
                BTreePrinter.printWhitespaces(firstSpaces - i);
                if (nodes.get(j) == null) {
                    BTreePrinter.printWhitespaces(endgeLines + endgeLines + i + 1);
                    continue;
                }

                if (nodes.get(j).left != null)
                    System.out.print("/");
                else
                    BTreePrinter.printWhitespaces(1);

                BTreePrinter.printWhitespaces(i + i - 1);

                if (nodes.get(j).right != null)
                    System.out.print("\\");
                else
                    BTreePrinter.printWhitespaces(1);

                BTreePrinter.printWhitespaces(endgeLines + endgeLines - i);
            }

            System.out.println("");
        }

        printNodeInternal(newNodes, level + 1, maxLevel);
    }

    private static void printWhitespaces(int count) {
        for (int i = 0; i < count; i++)
            System.out.print(" ");
    }

    private static <K extends Comparable<K>, V> int maxLevel(BinarySearchTree.BstEntry<K, V> node) {
        if (node == null)
            return 0;

        return Math.max(BTreePrinter.maxLevel(node.left), BTreePrinter.maxLevel(node.right)) + 1;
    }

    private static <K extends Comparable<K>, V> boolean isAllElementsNull(List<BinarySearchTree.BstEntry<K, V>> nodes) {
        for (Object object : nodes) {
            if (object != null)
                return false;
        }

        return true;
    }

    public static void main(String[] args) {
        RedBlackTree tree = new RedBlackTree();
//        tree.put(1, 1);
//        tree.put(2, 2);
//        tree.put(3, 3);
//        tree.put(4, 4);
//        tree.put(5, 5);
//        tree.put(6, 6);
//        tree.put(7, 7);
//        tree.put(8, 8);
        for (int i = 1; i <= 16; i++) {
            tree.put(i, i);
        }

        BTreePrinter.printNode(tree.getRoot());
    }

}