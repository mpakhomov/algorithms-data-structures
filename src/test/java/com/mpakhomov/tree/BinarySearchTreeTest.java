package com.mpakhomov.tree;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;


/**
 * @author mpakhomov
 */
public class BinarySearchTreeTest {

/**
 * correctBst
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
    BinarySearchTree<Integer> correctBst;
    private void buildCorrectBst() {
        correctBst = new BinarySearchTree<>();
        correctBst.insert(8);
        correctBst.insert(3);
        correctBst.insert(10);
        correctBst.insert(1);
        correctBst.insert(6);
        correctBst.insert(4);
        correctBst.insert(7);
        correctBst.insert(14);
        correctBst.insert(13);
        correctBst.insert(9);
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
    }


    @Before
    public void setUp() {
        buildCorrectBst();
        buildIncorrectBst();
        buildIncorrectBst2();
    }

    @Test
    public void testSize() {
        assertThat(correctBst.getSize(), is(equalTo(10)));
    }

    @Test
    public void testSuccessfulSearch13() {
        // test that 13 is found
        BstNode<Integer> node = correctBst.search(13);
        assertThat(node, is(notNullValue()));
        assertThat(node.key, is(equalTo(13)));
    }

    @Test
    public void testUnsuccessfulSearch12() {
        // test that 12 is not found
        BstNode<Integer> node = correctBst.search(12);
        assertThat(node, is(nullValue()));
    }

    @Test
    public void validateBst() {
        boolean isValid = BinarySearchTree.isBst1(correctBst.getRoot());
        assertThat(isValid, is(equalTo(true)));
    }

//    @Test
//    public void testAddWithFind() {
//        BinarySearchTreeDeprecated.TreeNode node = correctBst.find(13);
//        // add 0 to the left of 13. this should make a tree invalid BST tree
//        node.setLeft(new BinarySearchTreeDeprecated.TreeNode(0));
//        assertThat("This should NOT be a correct BST!", BinarySearchTreeDeprecated.isBST(correctBst.getRoot()),
//                is(equalTo(false)));
//        assertThat("This should NOT be a correct BST!", BinarySearchTreeDeprecated.isBSTInOrder(correctBst.getRoot()),
//                is(equalTo(false)));
//
//    }
//
//    @Test
//    public void testCorrectBST() {
//        assertThat("This should be a correct BST!", BinarySearchTreeDeprecated.isBST(correctBst.getRoot()),
//                is(equalTo(true)));
//        assertThat("This should be a correct BST!", BinarySearchTreeDeprecated.isBSTInOrder(correctBst.getRoot()),
//                is(equalTo(true)));
//    }
//
//    @Test
//    public void testIncorrectBST() {
//        assertThat("This should NOT be a correct BST!", BinarySearchTreeDeprecated.isBST(incorrectBst1.getRoot()),
//                is(equalTo(false)));
//        assertThat("This should NOT be a correct BST!", BinarySearchTreeDeprecated.isBSTInOrder(incorrectBst1.getRoot()),
//                is(equalTo(false)));
//        BinarySearchTreeDeprecated.TraverseInOrder(correctBst.getRoot());
//    }
//
//    @Test
//    public void testInOrder() {
//        BinarySearchTreeDeprecated bst = new BinarySearchTreeDeprecated();
//        bst.add(40);
//        bst.add(78);
//        bst.add(25);
//        bst.add(10);
//        bst.add(32);
//        List<Integer> nodes = BinarySearchTreeDeprecated.TraverseInOrder(bst.getRoot());
//        assertThat(nodes, contains(10, 25, 32, 40, 78));
//    }
//
//    @Test
//    public void testPreOrder() {
//        BinarySearchTreeDeprecated bst = new BinarySearchTreeDeprecated();
//        bst.add(40);
//        bst.add(78);
//        bst.add(25);
//        bst.add(10);
//        bst.add(32);
//        List<Integer> nodes = BinarySearchTreeDeprecated.traversePreOrder(bst.getRoot());
//        assertThat(nodes, contains(40, 25, 10, 32, 78));
//    }
//
//    @Test
//    public void testPostOrder() {
//        BinarySearchTreeDeprecated bst = new BinarySearchTreeDeprecated();
//        bst.add(40);
//        bst.add(78);
//        bst.add(25);
//        bst.add(10);
//        bst.add(32);
//        List<Integer> nodes = BinarySearchTreeDeprecated.traversePostOrder(bst.getRoot());
//        assertThat(nodes, contains(10, 32, 25, 78, 40));
//    }

}
