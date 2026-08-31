package day19;

public class LinkedListDemo {

    static class Node {
        int value;
        Node next;

        Node(int value){
            this.value = value;
        }
    }

    public static void main(String[] args) {
        Node first = new Node(10);
        Node second = new Node(20);
        Node third = new Node(30);
        Node four = new Node(40);


        first.next = second;
        second.next = third;
        third.next = four;
        Node current = first;

        while (current != null) {
            System.out.println(current.value);
            current = current.next;
        }
    }
}


