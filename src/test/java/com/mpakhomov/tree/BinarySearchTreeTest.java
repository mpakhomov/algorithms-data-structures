package com.mpakhomov.tree;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

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

    BinarySearchTreeDeprecated treeCorrect;
    BinarySearchTreeDeprecated treeIncorrect;


    @Before
    public void setUp() {
        treeCorrect = new BinarySearchTreeDeprecated();
        treeCorrect.add(8);
        treeCorrect.add(3);
        treeCorrect.add(10);
        treeCorrect.add(1);
        treeCorrect.add(6);
        treeCorrect.add(4);
        treeCorrect.add(7);
        treeCorrect.add(14);
        treeCorrect.add(13);

        treeIncorrect = new BinarySearchTreeDeprecated();
        treeIncorrect.add(10);
        treeIncorrect.add(5);
        treeIncorrect.add(15);
        treeIncorrect.add(20);
        treeIncorrect.getRoot().getRight().setLeft(new BinarySearchTreeDeprecated.TreeNode(6));
    }

    @Test
    public void testSize() {
        assertThat(treeCorrect.getSize(), is(equalTo(9)));
    }

    @Test
    public void testFindFound() {
        BinarySearchTreeDeprecated.TreeNode node = treeCorrect.find(13);
        assertThat(node, is(notNullValue()));
        assertThat(node.getData(), is(equalTo(13)));
    }

    @Test
    public void testFindNotFound() {
        BinarySearchTreeDeprecated.TreeNode node = treeCorrect.find(12);
        assertThat(node, is(nullValue()));
    }

    @Test
    public void testAddWithFind() {
        BinarySearchTreeDeprecated.TreeNode node = treeCorrect.find(13);
        // add 0 to the left of 13. this should make a tree invalid BST tree
        node.setLeft(new BinarySearchTreeDeprecated.TreeNode(0));
        assertThat("This should NOT be a correct BST!", BinarySearchTreeDeprecated.isBST(treeCorrect.getRoot()),
                is(equalTo(false)));
        assertThat("This should NOT be a correct BST!", BinarySearchTreeDeprecated.isBSTInOrder(treeCorrect.getRoot()),
                is(equalTo(false)));

    }

    @Test
    public void testCorrectBST() {
        assertThat("This should be a correct BST!", BinarySearchTreeDeprecated.isBST(treeCorrect.getRoot()),
                is(equalTo(true)));
        assertThat("This should be a correct BST!", BinarySearchTreeDeprecated.isBSTInOrder(treeCorrect.getRoot()),
                is(equalTo(true)));
    }

    @Test
    public void testIncorrectBST() {
        assertThat("This should NOT be a correct BST!", BinarySearchTreeDeprecated.isBST(treeIncorrect.getRoot()),
                is(equalTo(false)));
        assertThat("This should NOT be a correct BST!", BinarySearchTreeDeprecated.isBSTInOrder(treeIncorrect.getRoot()),
                is(equalTo(false)));
        BinarySearchTreeDeprecated.TraverseInOrder(treeCorrect.getRoot());
    }

    @Test
    public void testInOrder() {
        BinarySearchTreeDeprecated bst = new BinarySearchTreeDeprecated();
        bst.add(40);
        bst.add(78);
        bst.add(25);
        bst.add(10);
        bst.add(32);
        List<Integer> nodes = BinarySearchTreeDeprecated.TraverseInOrder(bst.getRoot());
        assertThat(nodes, contains(10, 25, 32, 40, 78));
    }

    @Test
    public void testPreOrder() {
        BinarySearchTreeDeprecated bst = new BinarySearchTreeDeprecated();
        bst.add(40);
        bst.add(78);
        bst.add(25);
        bst.add(10);
        bst.add(32);
        List<Integer> nodes = BinarySearchTreeDeprecated.traversePreOrder(bst.getRoot());
        assertThat(nodes, contains(40, 25, 10, 32, 78));
    }

    @Test
    public void testPostOrder() {
        BinarySearchTreeDeprecated bst = new BinarySearchTreeDeprecated();
        bst.add(40);
        bst.add(78);
        bst.add(25);
        bst.add(10);
        bst.add(32);
        List<Integer> nodes = BinarySearchTreeDeprecated.traversePostOrder(bst.getRoot());
        assertThat(nodes, contains(10, 32, 25, 78, 40));
    }

}
