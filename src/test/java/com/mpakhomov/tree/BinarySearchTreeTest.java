package com.mpakhomov.tree;

import com.mpakhomov.seq.Sequence;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;


/**
 * @author mpakhomov
 */
public class BinarySearchTreeTest {

/**
 * correctBst1
 *
        8
       / \
      /   \
     /     \
    /       \
   3       10
  / \      / \
 /   \    /   \
 1   6   9    14
    / \     /
   4  7    13

 */
    BinarySearchTree<Integer> correctBst1;
    // LoL is a list of list - it's a results of breadth-first (level-order) traversal
    List<List<Integer>> correctBst1Lol;
    private void buildCorrectBst1() {
        correctBst1 = new BinarySearchTree<>();
        correctBst1.insert(8);
        correctBst1.insert(3);
        correctBst1.insert(10);
        correctBst1.insert(1);
        correctBst1.insert(6);
        correctBst1.insert(4);
        correctBst1.insert(7);
        correctBst1.insert(14);
        correctBst1.insert(13);
        correctBst1.insert(9);

        correctBst1Lol = new ArrayList<>();
        correctBst1Lol.add(Arrays.asList(8));
        correctBst1Lol.add(Arrays.asList(3, 10));
        correctBst1Lol.add(Arrays.asList(1, 6, 9, 14));
        correctBst1Lol.add(Arrays.asList(4, 7, 13));
    }

/**
 * correctBst2
 *
            40
           / \
          /   \
         /     \
        /       \
        25       78
      / \
     /   \
    10     32

 */
    BinarySearchTree<Integer> correctBst2;
    List<List<Integer>> correctBst2Lol;
    private void buildCorrectBst2() {
        correctBst2 = new BinarySearchTree<>();
        correctBst2.insert(40);
        correctBst2.insert(78);
        correctBst2.insert(25);
        correctBst2.insert(10);
        correctBst2.insert(32);
        correctBst2Lol = new ArrayList<>();
        correctBst2Lol.add(Arrays.asList(40));
        correctBst2Lol.add(Arrays.asList(25, 78));
        correctBst2Lol.add(Arrays.asList(10, 32));
    }

    /**
     * treeWithOneNode
     *        1
     *       /\
     *      /  \
     *    null null
     */
    BinarySearchTree<Integer> treeWithOneNode;
    List<List<Integer>> treeWithOneNodeLol;
    private void buildtreeWithOneNode() {
        treeWithOneNode = new BinarySearchTree<>();
        treeWithOneNode.insert(1);
        treeWithOneNodeLol = new ArrayList<>();
        treeWithOneNodeLol.add(Arrays.asList(1));
    }


/**
 * incorrectBst1
 *
   10
  / \
 /   \
 5   6
      \
      20
*/
    BinarySearchTree<Integer> incorrectBst1;
    private void buildIncorrectBst() {
        incorrectBst1 = new BinarySearchTree();
        incorrectBst1.insert(10);
        incorrectBst1.insert(5);
        incorrectBst1.insert(15);
        incorrectBst1.insert(20);
        // replace 15 with 6 to make the tree incorrect BST
        BstNode<Integer> root = incorrectBst1.getRoot();
        BstNode<Integer> n6 = new BstNode<>(6);
        BstNode<Integer> n20 = root.right.right;
        root.right = n6;
        n6.right = n20;
        n6.parent = root;
        n20.parent = n6;
    }

/**
 * incorrectBst2

         20
        / \
       /   \
      /     \
     /       \
    10       30
      \       \
       70      \
               40
              / \
             6  50

 */
    BinarySearchTree<Integer> incorrectBst2;
    private void buildIncorrectBst2() {
        incorrectBst2 = new BinarySearchTree();
        incorrectBst2.insert(20);
        incorrectBst2.insert(10);
        incorrectBst2.insert(30);
        incorrectBst2.insert(40);
        incorrectBst2.insert(50);
        // add 6 as a left child of 40
        BstNode<Integer> n40 = incorrectBst2.search(40);
        BstNode<Integer> n6 = new BstNode<>(6);
        n40.left = n6;
        n6.parent = n40;

        // add 70 as a right child of 10
        BstNode<Integer> n10 = incorrectBst2.search(10);
        BstNode<Integer> n70 = new BstNode<>(70);
        n10.right = n70;
        n70.parent = n10;
    }


    @Before
    public void setUp() {
        buildCorrectBst1();
        buildCorrectBst2();
        buildIncorrectBst();
        buildIncorrectBst2();
        buildtreeWithOneNode();
    }

    @Test
    public void testSize() {
        assertThat(correctBst1.getSize(), is(equalTo(10)));
    }

    @Test
    public void testInsertNode() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        tree.insert(new BstNode<>(2));
        tree.insert(new BstNode<>(1));
        tree.insert(new BstNode<>(3));
        BstNode<Integer> root = tree.getRoot();
        // verify root
        assertThat(root.parent, equalTo(null));

        // verify left child
        assertThat(root.left.key, equalTo(1));
        assertThat(root.left.parent, equalTo(root));
        assertThat(root.left.left, nullValue());
        assertThat(root.left.right, nullValue());

        // verify right child
        assertThat(root.right.key, equalTo(3));
        assertThat(root.right.parent, equalTo(root));
        assertThat(root.right.left, nullValue());
        assertThat(root.right.right, nullValue());
    }

    @Test
    public void testInsert() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        tree.insert(2);
        tree.insert(1);
        tree.insert(3);
        BstNode<Integer> root = tree.getRoot();
        // verify root
        assertThat(root.parent, equalTo(null));

        // verify left child
        assertThat(root.left.key, equalTo(1));
        assertThat(root.left.parent, equalTo(root));
        assertThat(root.left.left, nullValue());
        assertThat(root.left.right, nullValue());

        // verify right child
        assertThat(root.right.key, equalTo(3));
        assertThat(root.right.parent, equalTo(root));
        assertThat(root.right.left, nullValue());
        assertThat(root.right.right, nullValue());
    }

    @Test
    public void testSuccessfulSearch13() {
        // test that 13 is found
        BstNode<Integer> node = correctBst1.search(13);
        assertThat(node, is(notNullValue()));
        assertThat(node.key, is(equalTo(13)));
    }

    @Test
    public void testUnsuccessfulSearch12() {
        // test that 12 is not found
        BstNode<Integer> node = correctBst1.search(12);
        assertThat(node, is(nullValue()));
    }

    @Test
    public void validateBst1() {
        boolean isValid = BinarySearchTree.isValidBst1(correctBst1.getRoot());
        assertThat(isValid, is(equalTo(true)));

        isValid = BinarySearchTree.isValidBst1(correctBst2.getRoot());
        assertThat(isValid, is(equalTo(true)));

        isValid = BinarySearchTree.isValidBst1(treeWithOneNode.getRoot());
        assertThat(isValid, is(equalTo(true)));

        isValid = BinarySearchTree.isValidBst1(incorrectBst1.getRoot());
        assertThat(isValid, is(equalTo(false)));

        isValid = BinarySearchTree.isValidBst1(incorrectBst2.getRoot());
        assertThat(isValid, is(equalTo(false)));
    }

    @Test
    public void validateBst2() {
        boolean isValid = BinarySearchTree.isValidBst2(correctBst1.getRoot());
        assertThat(isValid, is(equalTo(true)));

        isValid = BinarySearchTree.isValidBst2(correctBst2.getRoot());
        assertThat(isValid, is(equalTo(true)));

        isValid = BinarySearchTree.isValidBst2(treeWithOneNode.getRoot());
        assertThat(isValid, is(equalTo(true)));

        isValid = BinarySearchTree.isValidBst2(incorrectBst1.getRoot());
        assertThat(isValid, is(equalTo(false)));

        isValid = BinarySearchTree.isValidBst2(incorrectBst2.getRoot());
        assertThat(isValid, is(equalTo(false)));
    }

    @Test
    public void validateBst3() {
        boolean isValid = BinarySearchTree.isValidBst3(correctBst1.getRoot(), Integer.MIN_VALUE, Integer.MAX_VALUE);
        assertThat(isValid, is(equalTo(true)));

        isValid = BinarySearchTree.isValidBst3(correctBst2.getRoot(), Integer.MIN_VALUE, Integer.MAX_VALUE);
        assertThat(isValid, is(equalTo(true)));

        isValid = BinarySearchTree.isValidBst3(treeWithOneNode.getRoot(), Integer.MIN_VALUE, Integer.MAX_VALUE);
        assertThat(isValid, is(equalTo(true)));

        isValid = BinarySearchTree.isValidBst3(incorrectBst1.getRoot(), Integer.MIN_VALUE, Integer.MAX_VALUE);
        assertThat(isValid, is(equalTo(false)));

        isValid = BinarySearchTree.isValidBst3(incorrectBst2.getRoot(), Integer.MIN_VALUE, Integer.MAX_VALUE);
        assertThat(isValid, is(equalTo(false)));
    }

    @Test
    public void validateBst4() {
        boolean isValid = BinarySearchTree.isValidBst4(correctBst1.getRoot());
        assertThat(isValid, is(equalTo(true)));

        isValid = BinarySearchTree.isValidBst4(correctBst2.getRoot());
        assertThat(isValid, is(equalTo(true)));

        isValid = BinarySearchTree.isValidBst4(treeWithOneNode.getRoot());
        assertThat(isValid, is(equalTo(true)));

        isValid = BinarySearchTree.isValidBst4(incorrectBst1.getRoot());
        assertThat(isValid, is(equalTo(false)));

        isValid = BinarySearchTree.isValidBst4(incorrectBst2.getRoot());
        assertThat(isValid, is(equalTo(false)));
    }

    @Test
    public void testInOrderRecursive() {
        List<Integer> nodes = BinarySearchTree.traverseInOrderRecursive(correctBst1.getRoot());
        assertThat(nodes, contains(1, 3, 4, 6, 7, 8, 9, 10, 13, 14));
        assertThat(Sequence.isSorted(nodes), is(equalTo(true)));
        assertThat(Sequence.isSorted(nodes.toArray(new Integer[]{})), is(equalTo(true)));
        assertThat(Sequence.isSorted(nodes.listIterator()), is(equalTo(true)));
    }

    @Test
    public void testInOrderIterative() {
        List<Integer> nodes = BinarySearchTree.traverseInOrderIterative(correctBst1.getRoot());
        assertThat(nodes, contains(1, 3, 4, 6, 7, 8, 9, 10, 13, 14));
        assertThat(Sequence.isSorted(nodes), is(equalTo(true)));
    }

    @Test
    public void testPreOrder() {
        List<Integer> nodes = BinarySearchTree.traversePreOrderRecursive(correctBst2.getRoot());
        assertThat(nodes, contains(40, 25, 10, 32, 78));
    }

    @Test
    public void testPostOrder() {
        List<Integer> nodes = BinarySearchTree.traversePostOrderRecursive(correctBst2.getRoot());
        assertThat(nodes, contains(10, 32, 25, 78, 40));
    }

    @Test
    public void testTraverseByLevelsRecursive() {
        List<List<Integer>> levels = BinarySearchTree.traverseByLevelsRecursive(correctBst1.getRoot());
        assertThat(levels, contains(correctBst1Lol.toArray()));

        levels = BinarySearchTree.traverseByLevelsRecursive(correctBst2.getRoot());
        assertThat(levels, contains(correctBst2Lol.toArray()));
    }

    @Test
    public void testTraverseByLevelsIterative() {
        List<List<Integer>> levels = BinarySearchTree.traverseByLevelsIterative(correctBst1.getRoot());
        assertThat(levels, contains(correctBst1Lol.toArray()));

        levels = BinarySearchTree.traverseByLevelsIterative(correctBst2.getRoot());
        assertThat(levels, contains(correctBst2Lol.toArray()));
    }

    @Test
    public void traverseByLevelsAsString() {
        List<List<String>> expected = convertToLolOfStrings(correctBst1Lol);
        List<List<String>> levels = BinarySearchTree.traverseByLevelsAsString(correctBst1.getRoot());
        assertThat(levels, contains(expected.toArray()));

        expected = convertToLolOfStrings(correctBst2Lol);
        levels = BinarySearchTree.traverseByLevelsAsString(correctBst2.getRoot());
        assertThat(levels, contains(expected.toArray()));
    }

    // LoL is a list of lists
    private <T> List<List<String>> convertToLolOfStrings(List<List<T>> lol) {
        List<List<String>> lolOfStrings = new ArrayList<>();
        for (List<T> level : lol) {
            List<String> keysAsStrings = new ArrayList<>();
            for (T element : level) {
                keysAsStrings.add(element.toString());
            }
            lolOfStrings.add(keysAsStrings);
        }
        return lolOfStrings;
    }

    @Test
    public void testTreeMinimum() {
        assertThat(BinarySearchTree.treeMinimum(treeWithOneNode.getRoot()).key, equalTo(1));

        // correctBst1

        // minimum of the subtree with root at 3
        assertThat(BinarySearchTree.treeMinimum(
            correctBst1.search(3)).key, equalTo(1));

        // minimum of the subtree with root at 8
        assertThat(BinarySearchTree.treeMinimum(
                        correctBst1.getRoot()).key, equalTo(1));

        assertThat(BinarySearchTree.treeMinimum(
                        correctBst1.search(13)).key, equalTo(13));

    }

    @Test
    public void testTreeMaximum() {
        assertThat(BinarySearchTree.treeMaximum(treeWithOneNode.getRoot()).key, equalTo(1));

        // correctBst1

        // minimum of the subtree with root at 3
        assertThat(BinarySearchTree.treeMaximum(
                correctBst1.search(3)).key, equalTo(7));

        // minimum of the subtree with root at 8
        assertThat(BinarySearchTree.treeMaximum(
                correctBst1.getRoot()).key, equalTo(14));

        assertThat(BinarySearchTree.treeMaximum(
                correctBst1.search(13)).key, equalTo(13));

    }

    @Test
    public void testPredecessor() {
        assertThat(BinarySearchTree.predecessor(treeWithOneNode.getRoot()), is(nullValue()));

        // correctBst1

        // predecessor of the node 3
        assertThat(BinarySearchTree.predecessor(
                correctBst1.search(3)).key, equalTo(1));

        assertThat(BinarySearchTree.predecessor(
                correctBst1.search(1)), is(nullValue()));

        assertThat(BinarySearchTree.predecessor(
                correctBst1.search(9)).key, equalTo(8));

        assertThat(BinarySearchTree.predecessor(
                correctBst1.search(10)).key, equalTo(9));

        // minimum of the subtree with root at 8
        assertThat(BinarySearchTree.predecessor(
                correctBst1.getRoot()).key, equalTo(7));

        assertThat(BinarySearchTree.predecessor(
                correctBst1.search(13)).key, equalTo(10));

        assertThat(BinarySearchTree.predecessor(
                correctBst1.search(14)).key, equalTo(13));

    }

    @Test
    public void testSuccessor() {
        assertThat(BinarySearchTree.successor(treeWithOneNode.getRoot()).key, equalTo(14));

        // correctBst1

        // predecessor of the node 3
        assertThat(BinarySearchTree.successor(
                correctBst1.search(3)).key, equalTo(1));

        assertThat(BinarySearchTree.successor(
                correctBst1.search(1)), equalTo(3));

        assertThat(BinarySearchTree.successor(
                correctBst1.search(9)).key, equalTo(10));

        assertThat(BinarySearchTree.successor(
                correctBst1.search(10)).key, equalTo(13));

        // minimum of the subtree with root at 8
        assertThat(BinarySearchTree.successor(
                correctBst1.getRoot()).key, equalTo(9));

        assertThat(BinarySearchTree.successor(
                correctBst1.search(13)).key, equalTo(14));

        assertThat(BinarySearchTree.successor(
                correctBst1.search(14)), is(nullValue()));
    }

    @Test
    public void testDelete() {
        correctBst1.delete(1);
        assertThat(correctBst1.getSize(), equalTo(9));

        correctBst1.delete(10);
        assertThat(correctBst1.getSize(), equalTo(8));
        List<List<String>> lol = new ArrayList<>();
        lol.add(Arrays.asList("8"));
        lol.add(Arrays.asList("3", "13"));
        lol.add(Arrays.asList("6", "9", "14"));
        lol.add(Arrays.asList("4", "7"));
        List<List<String>> levels = BinarySearchTree.traverseByLevelsAsString(correctBst1.getRoot());
        assertThat(levels, contains(lol.toArray()));
        assertThat(BinarySearchTree.traverseInOrderIterative(correctBst1.getRoot()),
                contains(3, 4, 6, 7, 8, 9, 13, 14));

        correctBst1.delete(3);
        assertThat(correctBst1.getSize(), equalTo(7));
        lol.clear();
        lol.add(Arrays.asList("8"));
        lol.add(Arrays.asList("6", "13"));
        lol.add(Arrays.asList("4", "7", "9", "14"));
        levels = BinarySearchTree.traverseByLevelsAsString(correctBst1.getRoot());
        assertThat(levels, contains(lol.toArray()));
        assertThat(BinarySearchTree.traverseInOrderIterative(correctBst1.getRoot()),
                contains(4, 6, 7, 8, 9, 13, 14));

        correctBst1.delete(8);
        correctBst1.delete(13);
        assertThat(correctBst1.getSize(), equalTo(5));
        lol.clear();
        lol.add(Arrays.asList("9"));
        lol.add(Arrays.asList("6", "14"));
        lol.add(Arrays.asList("4", "7"));
        levels = BinarySearchTree.traverseByLevelsAsString(correctBst1.getRoot());
        assertThat(levels, contains(lol.toArray()));
        assertThat(BinarySearchTree.traverseInOrderIterative(correctBst1.getRoot()),
                contains(4, 6, 7, 9, 14));
    }
}
