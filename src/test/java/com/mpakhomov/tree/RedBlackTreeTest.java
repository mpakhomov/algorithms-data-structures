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
    RedBlackTree<Integer> treeFromTheBook;
    List<List<String>> treeFromTheBookLol;
    @Before
    public void setUp() {
        buildRbtFromClr();
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
        BTreePrinter.printNode(treeFromTheBook.getRoot());
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
}
