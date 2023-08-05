# 二叉树的遍历


## DFS

### 递归实现
 ```java
// 定义二叉树节点
class TreeNode {
     int val;
     TreeNode left;
     TreeNode right;
     TreeNode(int val) {
         this.val = val;
     }
 }
 
// 二叉树遍历类
class BinaryTreeTraversal {
    // 前序遍历
    public void preOrderTraversal(TreeNode root) {
        if (root == null) {
            return;
        }
            System.out.print(root.val + " ");
        preOrderTraversal(root.left);
        preOrderTraversal(root.right);
    }
    // 中序遍历
    public void inOrderTraversal(TreeNode root) {
        if (root == null) {
            return;
        }
        inOrderTraversal(root.left);
        System.out.print(root.val + " ");
        inOrderTraversal(root.right);
    }
    // 后序遍历
    public void postOrderTraversal(TreeNode root) {
        if (root == null) {
            return;
        }
        postOrderTraversal(root.left);
        postOrderTraversal(root.right);
        System.out.print(root.val + " ");
    }
}
```

### 非递归实现-使用栈（Stack）

```java
 // 定义二叉树节点
 class TreeNode {
     int val;
     TreeNode left;
     TreeNode right;
     TreeNode(int val) {
         this.val = val;
     }
 }
 // 二叉树非递归遍历类
 class BinaryTreeTraversal {
    
    // 前序遍历
    public void preOrderTraversal(TreeNode root) {
        if (root == null) {
            return;
        }
         Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            System.out.print(node.val + " ");
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
    }
    // 中序遍历
    public void inOrderTraversal(TreeNode root) {
        if (root == null) {
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            System.out.print(curr.val + " ");
            curr = curr.right;
        }
    }
    // 后序遍历
    public void postOrderTraversal(TreeNode root) {
        if (root == null) {
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        Stack<TreeNode> result = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            result.push(node);
            if (node.left != null) {
                stack.push(node.left);
            }
            if (node.right != null) {
                stack.push(node.right);
            }
        }
        while (!result.isEmpty()) {
            System.out.print(result.pop().val + " ");
        }
    }
 }

 ```

## BFS

### 使用队列（Queue）

 ```java

class Node {
    int data;
    Node left, right;
     public Node(int item) {
        data = item;
        left = right = null;
    }
}
 class BinaryTree {

    // 层序遍历
    public void levelOrder(TreeNode root) {
        if (root == null){
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            System.out.print(current.data + " ");
            if (current.left != null) {
                queue.add(current.left);
            }
            if (current.right != null){
                queue.add(current.right);
            }
        }
    }
}

 ```