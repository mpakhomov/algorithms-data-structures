package com.mpakhomov.bst;

import org.junit.Before;
import org.junit.Test;
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

    BinarySearchTree treeCorrect;
    BinarySearchTree treeIncorrect;


    @Before
    public void setUp() {
        treeCorrect = new BinarySearchTree();
        treeCorrect.add(8);
        treeCorrect.add(3);
        treeCorrect.add(10);
        treeCorrect.add(1);
        treeCorrect.add(6);
        treeCorrect.add(4);
        treeCorrect.add(7);
        treeCorrect.add(14);
        treeCorrect.add(13);

        treeIncorrect = new BinarySearchTree();
        treeIncorrect.add(10);
        treeIncorrect.add(5);
        treeIncorrect.add(15);
        treeIncorrect.add(20);
        treeIncorrect.getRoot().getRight().setLeft(new BinarySearchTree.TreeNode(6));
    }

    @Test
    public void testSize() {
        assertThat(treeCorrect.getSize(), is(equalTo(9)));
    }

    @Test
    public void testFindFound() {
        BinarySearchTree.TreeNode node = treeCorrect.find(13);
        assertThat(node, is(notNullValue()));
        assertThat(node.getData(), is(equalTo(13)));
    }

    @Test
    public void testFindNotFound() {
        BinarySearchTree.TreeNode node = treeCorrect.find(12);
        assertThat(node, is(nullValue()));
    }

    @Test
    public void testAddWithFind() {
        BinarySearchTree.TreeNode node = treeCorrect.find(13);
        // add 0 to the left of 13. this should make a tree invalid BST tree
        node.setLeft(new BinarySearchTree.TreeNode(0));
        assertThat("This should NOT be a correct BST!", BinarySearchTree.isBST(treeCorrect.getRoot()),
                is(equalTo(false)));
        assertThat("This should NOT be a correct BST!", BinarySearchTree.isBSTInOrder(treeCorrect.getRoot()),
                is(equalTo(false)));

    }

    @Test
    public void testCorrectBST() {
        assertThat("This should be a correct BST!", BinarySearchTree.isBST(treeCorrect.getRoot()),
                is(equalTo(true)));
        assertThat("This should be a correct BST!", BinarySearchTree.isBSTInOrder(treeCorrect.getRoot()),
                is(equalTo(true)));
    }

    @Test
    public void testIncorrectBST() {
        assertThat("This should NOT be a correct BST!", BinarySearchTree.isBST(treeIncorrect.getRoot()),
                is(equalTo(false)));
        assertThat("This should NOT be a correct BST!", BinarySearchTree.isBSTInOrder(treeIncorrect.getRoot()),
                is(equalTo(false)));
        BinarySearchTree.printInOrder(treeCorrect.getRoot());
    }


}
