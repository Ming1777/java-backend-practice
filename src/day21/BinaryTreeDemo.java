package day21;

public class BinaryTreeDemo {

    static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
        }
    }

    public static void main(String[] args) {
        //       10
        //      /  \
        //     20   30
        //    /
        //   40
        Node root = new Node(10);
        root.left = new Node(20);
        root.right = new Node(30);
        root.left.left = new Node(40);

        System.out.println("根节点：" + root.value);
        System.out.println("40 的父节点：" + root.left.value);
        System.out.println("根的右孩子：" + root.right.value);
        System.out.print("前序遍历：");
        preorder(root);
        System.out.println();
    }

    // 前序遍历：访问当前节点，再遍历整个左子树，最后遍历整个右子树。
    static void preorder(Node node) {
        if (node == null) {
            return; // 没有节点时结束这一次调用。
        }
        System.out.print(node.value + " ");
        preorder(node.left);
        preorder(node.right);
    }
}
