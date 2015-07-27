package com.mpakhomov.tree;

import org.junit.*;
import java.util.*;
import java.util.stream.Collectors;

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
     * aka CLRS book

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
        buildRbtFromClrsBook();
        buildTree2();
        buildTree3();
    }

    private void buildRbtFromClrsBook() {
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
*   tree 3 before insertion.

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

        tree3 after insertion.

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

    /**
     * Node z (6) has 2 children (11, 40). z's successor y (11) is not a direct child of z.
     * y is a grandson of z
     *
                        4:B
                        / \
                       /   \
                      /     \
                     /       \
                   2:R       6:R
                   / \      / \
                  /   \    /   \
                1:B   3:B 5:B  15:B
                               / \
                             11:R 40:R
     */
    @Test
    public void deleteANodeWith2Children() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.put(1);
        tree.put(2);
        tree.put(3);
        tree.put(4);
        tree.put(6);
        tree.put(5);
        tree.put(15);
        tree.put(40);
        tree.put(11);
        tree.rbtDelete(6);
        List<List<String>> treeLol = new ArrayList<>();
        treeLol.add(Arrays.asList("4:B"));
        treeLol.add(Arrays.asList("2:R", "11:R"));
        treeLol.add(Arrays.asList("1:B", "3:B", "5:B", "15:B"));
        treeLol.add(Arrays.asList("40:R"));
        List<List<String>> levels = BinarySearchTree.traverseByLevelsAsString(tree.getRoot());
        assertThat(levels, contains(treeLol.toArray()));
        assertThat(tree.getSize(), equalTo(8));
    }

    /**
     * Node z (6) has only one child (40) and the child is on the right. y = 6, x = 40
     *           4:B
                 / \
                /   \
               2:B   6:B
              / \     \
            1:R 3:R   40:R
     */
    @Test
    public void deleteANodeWithOnlyOneChild() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.put(4);
        tree.put(2);
        tree.put(6);
        tree.put(1);
        tree.put(3);
        tree.put(40);
        tree.rbtDelete(6);
//        tree.rbtDelete(40);
    }

    /**
     * Node z (4) has 2 children (2, 6) and the child is on the right. y = 6, x = null
     *           4:B
                 / \
                /   \
              2:B   6:B
              / \
            1:R 3:R
     */
    @Test
    public void deleteANodeXisNullCase4() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.put(4);
        tree.put(2);
        tree.put(6);
        tree.put(1);
        tree.put(3);
        tree.rbtDelete(4);
        List<List<String>> treeLol = new ArrayList<>();
        treeLol.add(Arrays.asList("2:B"));
        treeLol.add(Arrays.asList("1:B", "6:B"));
        treeLol.add(Arrays.asList("3:R"));
        List<List<String>> levels = BinarySearchTree.traverseByLevelsAsString(tree.getRoot());
        assertThat(levels, contains(treeLol.toArray()));
        assertThat(tree.getSize(), equalTo(4));
    }

    /**
     * Node z (15) has 2 children (11, 40). z's successor y (40) is z's direct child. x = null
     *                  4:B
                        / \
                       /   \
                      /     \
                     /       \
                   2:R       6:R
                   / \      /  \
                  /   \    /    \
               1:B   3:B  5:B   15:B
                                 / \
                               11:R 40:R
     */
    @Test
    public void deleteANodeWith2ChildrenAndSuccessorIsDirectChild() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.put(1);
        tree.put(2);
        tree.put(3);
        tree.put(4);
        tree.put(6);
        tree.put(5);
        tree.put(15);
        tree.put(40);
        tree.put(11);
        tree.rbtDelete(15);
        List<List<String>> treeLol = new ArrayList<>();
        treeLol.add(Arrays.asList("4:B"));
        treeLol.add(Arrays.asList("2:R", "6:R"));
        treeLol.add(Arrays.asList("1:B", "3:B", "5:B", "40:B"));
        treeLol.add(Arrays.asList("11:R"));
        List<List<String>> levels = BinarySearchTree.traverseByLevelsAsString(tree.getRoot());
        assertThat(levels, contains(treeLol.toArray()));
        assertThat(tree.getSize(), equalTo(8));
    }


    /**
     * Node z (2) has 2 children (1, 3). z's successor y (3) is z's direct child. x = null
     *                  4:B
                        / \
                       /   \
                      /     \
                     /       \
                    2:R       6:R
                    / \      /  \
                   /   \    /    \
                1:B   3:B  5:B   15:B
                                 / \
                                11:R 40:R
     */

    @Test
    public void deletionCase2() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.put(1);
        tree.put(2);
        tree.put(3);
        tree.put(4);
        tree.put(6);
        tree.put(5);
        tree.put(15);
        tree.put(40);
        tree.put(11);
        tree.rbtDelete(2);
        List<List<String>> treeLol = new ArrayList<>();
        treeLol.add(Arrays.asList("4:B"));
        treeLol.add(Arrays.asList("3:B", "6:R"));
        treeLol.add(Arrays.asList("1:R", "5:B", "15:B"));
        treeLol.add(Arrays.asList("11:R", "40:R"));
        List<List<String>> levels = BinarySearchTree.traverseByLevelsAsString(tree.getRoot());
        assertThat(levels, contains(treeLol.toArray()));
        assertThat(tree.getSize(), equalTo(8));
    }

    /**
     * Node z (4) has 2 children (2, 7). z's successor y (5) is z's grandson.
     * x (6) is not null.
     *
                        4:B
                        / \
                       /   \
                      /     \
                     /       \
                   2:B       7:R
                   / \       / \
                  /   \     /   \
                1:R   3:R  5:B   10:B
                            \       \
                             6:R   20:R
     *
     * */
    @Test
    public void deleteANodeWith2ChildrenAndSuccessorIsNotDirectChildAndXIsNotNull() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.put(4);
        tree.put(2);
        tree.put(10);
        tree.put(1);
        tree.put(3);
        tree.put(5);
        tree.put(7);
        tree.put(20);
        tree.put(6);
        tree.rbtDelete(4);
        List<List<String>> treeLol = new ArrayList<>();
        treeLol.add(Arrays.asList("5:B"));
        treeLol.add(Arrays.asList("2:B", "7:R"));
        treeLol.add(Arrays.asList("1:R", "3:R", "6:B", "10:B"));
        treeLol.add(Arrays.asList("20:R"));
        List<List<String>> levels = BinarySearchTree.traverseByLevelsAsString(tree.getRoot());
        assertThat(levels, contains(treeLol.toArray()));
        assertThat(tree.getSize(), equalTo(8));
    }

    /**
     * Node z (7) has 2 children (5, 10). z's successor y (10) and it's a direct child of z (z = y.p)
     * x (20) is not null
     *
                    4:B
                    / \
                   /   \
                  /     \
                 /       \
                2:B       7:R
                / \       / \
               /   \     /   \
            1:R   3:R  5:B   10:B
                        \      \
                         6:R   20:R
     *
     * */
    @Test
    public void deleteANodeWith2ChildrenAndSuccessorIsDirectChildAndXIsNotNull() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.put(4);
        tree.put(2);
        tree.put(10);
        tree.put(1);
        tree.put(3);
        tree.put(5);
        tree.put(7);
        tree.put(20);
        tree.put(6);
        tree.rbtDelete(7);
        List<List<String>> treeLol = new ArrayList<>();
        treeLol.add(Arrays.asList("4:B"));
        treeLol.add(Arrays.asList("2:B", "10:R"));
        treeLol.add(Arrays.asList("1:R", "3:R", "5:B", "20:B"));
        treeLol.add(Arrays.asList("6:R"));
        List<List<String>> levels = BinarySearchTree.traverseByLevelsAsString(tree.getRoot());
        assertThat(levels, contains(treeLol.toArray()));
        assertThat(tree.getSize(), equalTo(8));
    }

    /**
     *  z = 5, z has 2 children (2, 8), y = 8. y is a son of z. x = null
     *
                    5:B
                    / \
                   /   \
                  /     \
                 /       \
               2:R       8:B
               / \
              /   \
            1:B   4:B
                   /
                3:R
     */
    @Test
    public void deleteRootElementCase1() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.put(5);
        tree.put(2);
        tree.put(8);
        tree.put(1);
        tree.put(4);
        tree.put(3);
        tree.rbtDelete(5);
    }

/**
 *   z = 2, z has 2 children (1, 3), y = 3. y is a son of z. x = null
 *
                4:B
                / \
               /   \
              /     \
             /       \
           2:R       6:R
          / \        / \
         /   \      /   \
       1:B   3:B   5:B  7:B
                         \
                         8:R
 */
    @Test
    public void deleteXisNullCase2() {
//        treeFromTheBook.rbtDelete(11);
        tree3.put(8);
        tree3.rbtDelete(2);
    }

/**
 * z = 18, 2 children (10, 22). y = 22, direct child. x = 26
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
                 8:R 11:R   26:R
*/
    @Test
    public void tree2Delete18() {
        tree2.rbtDelete(18);
    }

    @Test
    public void tree0to19Delete() {
        RedBlackTree tree = new RedBlackTree();
        for (int i = 0; i < 20; i++) {
            tree.put(i);
        }
        BTreePrinter.printNode(tree.getRoot());
        tree.rbtDelete(4);
        tree.rbtDelete(7);
        tree.rbtDelete(6);
        tree.rbtDelete(14);
        tree.rbtDelete(8);
        tree.rbtDelete(19);
        tree.rbtDelete(0);
        tree.rbtDelete(1);
        tree.rbtDelete(13);
        tree.rbtDelete(5);
        tree.rbtDelete(2);
        tree.rbtDelete(18);
        tree.rbtDelete(17);
        tree.rbtDelete(9);
        tree.rbtDelete(15);
        tree.rbtDelete(16);
        tree.rbtDelete(11);
        BTreePrinter.printNode(tree.getRoot());
    }

    @Test
    public void randomTreeDelete() {
        Random random = new Random();
        final int size = 100;
        List<Integer> randomIntegers = new ArrayList<>(size);
        RedBlackTree tree = new RedBlackTree();
        for (int i = 0; i < size; i++) {
            int randomInt = random.nextInt(10_000);
            randomIntegers.add(i);
            tree.put(i);
        }
        System.out.println(
            randomIntegers.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(" ")));

        Collections.shuffle(randomIntegers);
        ArrayDeque<Integer> queue = new ArrayDeque<>(size);
        queue.addAll(randomIntegers);
//        BTreePrinter.printNode(tree.getRoot());
        while (queue.size() >= 4) {
            int randomElement = queue.poll();
            System.out.println("delete " + randomElement);
            tree.rbtDelete(randomElement);
        }
        BTreePrinter.printNode(tree.getRoot());
    }


//    @Test
//    public void testDeleteFromEmptyTree() {
//        RedBlackTree<Integer> tree = new RedBlackTree<>();
//        tree.rbtDelete(1);
//    }
//
//    @Test(expected = NullPointerException.class)
//    public void testDeleteNullFromTree() {
//        Integer nullInteger = null;
//        tree2.rbtDelete(nullInteger);
//    }
}
