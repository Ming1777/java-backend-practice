package day19;

public class LinkedListDemo {

    static class Node {
        int value;
        Node next;

        Node(int value){
            this.value = value;
        }
    }

    static Node reverse(Node head) {
        Node prev = null;
        Node current = head;
        while (current != null){
            Node next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        return prev;
    }
    public static void main(String[] args) {
        Node first = new Node(10);
        Node second = new Node(20);
        Node third = new Node(30);
        Node four = new Node(40);

        first.next = second;
        second.next = third;
        third.next = four;

// 把正向链表交给方法反转，拿到新的头节点
        Node current = reverse(first);

        while (current != null) {
            System.out.println(current.value);
            current = current.next;
        }
    }
}


