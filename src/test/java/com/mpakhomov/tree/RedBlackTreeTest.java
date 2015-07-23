package com.mpakhomov.tree;

import org.junit.*;
import java.util.*;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;




/**
 * @author: mpakhomov
 */
public class RedBlackTreeTest {

    /**
     * tree from the MIT algorithms book by
     * Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest and Clifford Stein

       Before Insertion

                11:B
                / \
               /   \
              /     \
             /       \
           2:R       14:B
           / \         \
          /   \         \
       1:B    7:B       15:R
       / \
     5:R  8:R

       After insertion:

                7:B
                / \
               /   \
              /     \
             /       \
           2:R       11:R
           / \      / \
          /   \    /   \
       1:B   5:B 8:B   14:B
            /           \
          4:R           15:R
    */
    private RedBlackTree<Integer> treeFromTheBook;
    private List<List<String>> treeFromTheBookLol;
    @Before
    public void setUp() {
        buildRbtFromClr();
        buildTree2();
        buildTree3();
    }

    private void buildRbtFromClr() {
        treeFromTheBook = new RedBlackTree<>();
        treeFromTheBook.put(11);
        treeFromTheBook.put(14);
        treeFromTheBook.put(2);
        treeFromTheBook.put(1);
        treeFromTheBook.put(7);
        treeFromTheBook.put(5);
        treeFromTheBook.put(8);
        treeFromTheBook.put(15);
        treeFromTheBookLol = new ArrayList<>();
        treeFromTheBookLol.add(Arrays.asList("7:B"));
        treeFromTheBookLol.add(Arrays.asList("2:R", "11:R"));
        treeFromTheBookLol.add(Arrays.asList("1:B", "5:B", "8:B", "14:B"));
        treeFromTheBookLol.add(Arrays.asList("4:R", "15:R"));
    }

    /**
     * tree2 before insertion
     *

             7:B
             / \
            /   \
           /     \
          /       \
        3:B       18:R
                   / \
                  /   \
                 10:B   22:B
                 / \     \
               8:R 11:R  26:R

    After insertion

           10:B
          / \
         /   \
        /     \
       /       \
       7:R       18:R
      / \        / \
     /   \      /   \
   3:B   8:B  11:B   22:B
                \      \
                15:R    26:R

*/

    private RedBlackTree<Integer> tree2;
    private List<List<String>> tree2Lol;
    private void buildTree2() {
        tree2 = new RedBlackTree();
        tree2.put(7);
        tree2.put(3);
        tree2.put(18);
        tree2.put(10);
        tree2.put(22);
        tree2.put(8);
        tree2.put(11);
        tree2.put(26);
//        tree2.put(15);
        tree2Lol = new ArrayList<>();
        tree2Lol.add(Arrays.asList("10:B"));
        tree2Lol.add(Arrays.asList("7:R", "18:R"));
        tree2Lol.add(Arrays.asList("3:B", "8:B", "11:B", "22:B"));
        tree2Lol.add(Arrays.asList("15:R", "26:R"));
    }

/**
*
*   tree 3 before insertion

            2:B
            / \
           /   \
          /     \
         /       \
       1:B       4:R
                 / \
                /   \
              3:B   6:B
                    / \
                  5:R 7:R

        tree3 after insertion

                    4:B
                    / \
                   /   \
                  /     \
                 /       \
                2:R       6:R
               / \        / \
             /    \      /   \
            1:B   3:B   5:B  7:B
                                \
                                8:R


 */
    private RedBlackTree<Integer> tree3;
    private List<List<String>> tree3Lol;
    private void buildTree3() {
        tree3 = new RedBlackTree<>();
        tree3.put(1);
        tree3.put(2);
        tree3.put(3);
        tree3.put(4);
        tree3.put(5);
        tree3.put(6);
        tree3.put(7);
//        tree3.put(8);
        tree3Lol = new ArrayList<>();
        tree3Lol.add(Arrays.asList("4:B"));
        tree3Lol.add(Arrays.asList("2:R", "6:R"));
        tree3Lol.add(Arrays.asList("1:B", "3:B", "5:B", "7:B"));
        tree3Lol.add(Arrays.asList("8:R"));
    }


    @Test
    public void testInsertionTreeFromTheBook() {
        treeFromTheBook.put(4);
        assertThat(treeFromTheBook.getSize(), equalTo(9));
        List<Integer> actual = BinarySearchTree.traverseInOrderIterative(treeFromTheBook.getRoot());
        assertThat(actual, contains(1, 2, 4, 5, 7, 8, 11, 14, 15));
        List<List<String>> levels = BinarySearchTree.traverseByLevelsAsString(treeFromTheBook.getRoot());
        assertThat(levels, contains(treeFromTheBookLol.toArray()));
    }

    @Test
    public void testInsertionTree2() {
        tree2.put(15);
        assertThat(tree2.getSize(), equalTo(9));
        List<Integer> actual = BinarySearchTree.traverseInOrderIterative(tree2.getRoot());
        assertThat(actual, contains(3, 7, 8, 10, 11, 15, 18, 22, 26));
        List<List<String>> levels = BinarySearchTree.traverseByLevelsAsString(tree2.getRoot());
        assertThat(levels, contains(tree2Lol.toArray()));
    }

    @Test
    public void testInsertionTree3() {
        tree3.put(8);
        assertThat(tree3.getSize(), equalTo(8));
        List<Integer> actual = BinarySearchTree.traverseInOrderIterative(tree3.getRoot());
        assertThat(actual, contains(1, 2, 3, 4, 5, 6, 7, 8));
        List<List<String>> levels = BinarySearchTree.traverseByLevelsAsString(tree3.getRoot());
        assertThat(levels, contains(tree3Lol.toArray()));
    }

    @Test
    public void testDeleteFromEmptyTree() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.delete(1);
    }

    @Test(expected = NullPointerException.class)
    public void testDeleteNullFromTree() {
        Integer nullInteger = null;
        tree2.delete(nullInteger);
    }
}
