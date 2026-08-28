package day18;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

public class StackQueueDemo {
    public static void main(String[] args) {
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(10);
        stack.push(20);
        stack.push(30);
        System.out.println("当前栈：" + stack);
        System.out.println("栈顶元素：" + stack.peek());

        int removed = stack.pop();
        System.out.println("弹出的元素：" + removed);
        System.out.println("弹出后的栈：" + stack);

        System.out.println("------------------------------");

        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(10);
        queue.offer(20);
        queue.offer(30);
        System.out.println("当前队列：" + queue);
        System.out.println("队头元素：" + queue.peek());

        int removedFromQueue = queue.poll();
        System.out.println("出队的元素：" + removedFromQueue);
        System.out.println("出队后的队列：" + queue);
    }
}
