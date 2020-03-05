# 二叉树的基础知识

1. 三种递归遍历
2. 三种非递归遍历
3. 层序遍历

```java
import java.util.Stack;
import java.util.LinkedList;
import java.util.Queue;

public class BinaryTree{
    //递归遍历
    //先序
    public static void preOrder(Node head){
        if(head != null){
            System.out.print(head.value + " ");
            preOrder(head.left);
            preOrder(head.right);
        }
    }
    //后序
    public static void postOrder(Node head){
        if(head != null){
            postOrder(head.left);
            postOrder(head.right);
            System.out.print(head.value + " ");
        }
    }
    //中序
    public static void inOrder(Node head){
        if(head != null){
            inOrder(head.left);
            System.out.print(head.value + " ");
            inOrder(head.right);
        }
    }

    //非递归遍历
    //先序 (CLR)
    /**
     * 记忆要领：设计一个栈,将根节点压入栈中,
     * @param head
     */
    public static void preOrderUnRecrsion(Node head){
        if(head != null){
            Stack<Node> stack = new Stack<>();
            stack.add(head);
            while(!stack.isEmpty()){
                head = stack.pop();
                System.out.print(head.value + " ");
                if(head.right != null){
                    stack.push(head.right);
                }
                if(head.left != null){
                    stack.push(head.left);
                }
            }
        }
    }

    //中序(LCR)
    /**
     * 记忆要领：设计一个栈,将根节点压入栈中
     * @param head
     */
    public static void inOrderUnRecrsion(Node head){
        if(head != null){
            Stack<Node> stack = new Stack<>();
            while(!stack.isEmpty() || head != null){
                if(head != null){
                    stack.push(head);
                    head = head.left;
                } else {
                    head = stack.pop();
                    System.out.print(head.value + " ");
                    head = head.right;
                }
            }
        }
    }

    //后序(LRC)
    public static void postOrderUnRecrsion(Node head){
        if(head != null){
            Stack<Node> s1 = new Stack<>();
            Stack<Node> s2 = new Stack<>();
            s1.push(head);
            while(!s1.isEmpty()){
                head = s1.pop();
                s2.push(head);
                if(head.left != null){
                    s1.push(head.left);
                }
                if(head.right != null){
                    s1.push(head.right);
                }
            }
            while(!s2.isEmpty()){
                System.out.print(s2.pop().value + " ");
            }
        }
    }
    


    //层序遍历，即宽度优先遍历
    public static void layerOrder(Node head){
        if(head != null){
            Queue<Node> queue = new LinkedList<>();
            queue.add(head);
            while(!queue.isEmpty()){
                Node n = queue.poll();
                System.out.print(n.value + " ");
                if(n.left != null){
                    queue.add(n.left);
                }
                if(n.right != null){
                    queue.add(n.right);
                }
            }
        }
    }


    //for test
    /**
     * 构建二叉树
     *         1
     *      2      3
     *   4    5  6    7
     */
    public static Node createBinaryTree(){
        Node nodeA = new Node(1);
        Node nodeB = new Node(2);
        Node nodeC = new Node(3);
        Node nodeD = new Node(4);
        Node nodeE = new Node(5);
        Node nodeF = new Node(6);
        Node nodeG = new Node(7);
        
        nodeA.left = nodeB;
        nodeA.right = nodeC;
        nodeB.left = nodeD;
        nodeB.right = nodeE;
        nodeC.left = nodeF;
        nodeC.right = nodeG;

        return nodeA;
    }
    
    //for test
    public static void main(String[] args) {
        Node head = createBinaryTree();

        System.out.print("递归遍历,先序：");
        preOrder(head);
        System.out.println();
        System.out.print("递归遍历,中序：");
        inOrder(head);
        System.out.println();
        System.out.print("递归遍历,后序：");
        postOrder(head);
        System.out.println();
        System.out.print("非递归遍历,先序：");
        preOrderUnRecrsion(head);
        System.out.println();
        System.out.print("非递归遍历,中序：");
        inOrderUnRecrsion(head);
        System.out.println();
        System.out.print("非递归遍历,后序：");
        postOrderUnRecrsion(head);
        System.out.println();
        System.out.print("层序遍历：");
        layerOrder(head);
        System.out.println();
    }
}

class Node{
    public int value;
    public Node left;
    public Node right;

    public Node(int data){
        this.value = data;
    }
}
```