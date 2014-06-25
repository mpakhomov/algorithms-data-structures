package com.mpakhomov;

import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Created with IntelliJ IDEA.
 * User: mike
 * Date: 12.08.13
 * Time: 20:54
 * To change this template use File | Settings | File Templates.
 */
public class PriorityQueueExtractMax {

    public static void main(String[] args) {
        PriorityQueue<Integer> queue = new PriorityQueue<Integer>(10, Collections.reverseOrder());
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        queue.offer(4);
//...

        Integer val = null;
        while( (val = queue.poll()) != null) {
            System.out.println(val);
        }
    }
}
