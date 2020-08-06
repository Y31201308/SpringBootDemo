package com.nokia.example.leetcode.tree;

import com.nokia.example.leetcode.entity.NTreeNode;
import com.nokia.example.leetcode.entity.TreeNode;

import java.util.*;

/**
 * @author by YingLong on 2020/7/27
 */
public class TreeTraversal {

    /**
     * 剑指 Offer 55 - I
     * 二叉树的深度
     */
    public int maxDepthV1(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return Math.max(maxDepthV1(root.left), maxDepthV1(root.right)) + 1;
    }

    /**
     * 剑指 Offer 55 - I
     * 二叉树的深度
     */
    public int maxDepthV2(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        int maxDepth = 0;
        queue.add(root);
        while (!queue.isEmpty()) {
            maxDepth++;
            int len = queue.size();
            for (int i = 0; i < len; i++) {
                TreeNode treeNode = queue.poll();
                if (treeNode.left != null) {
                    queue.add(treeNode.left);
                }
                if (treeNode.right != null) {
                    queue.add(treeNode.right);
                }
            }
        }
        return maxDepth;
    }

    /**
     * 543
     * 二叉树的直径
     */
    int diameter = 0;

    public int diameter(TreeNode root) {
        depth(root);
        return diameter;
    }

    public int depth(TreeNode treeNode) {
        if (treeNode == null) {
            return 0;
        }
        int left = depth(treeNode.left);
        int right = depth(treeNode.right);
        diameter = Math.max(left + right, diameter);
        return Math.max(left, right) + 1;
    }


    /**
     * 606
     * 根据二叉树创建字符串
     *
     * @param root
     * @return
     */
    public String tree2str(TreeNode root) {
        if (root == null) {
            return "";
        }
        String result = root.val + "";
        if (root.left == null && root.right == null) {
            return result;
        }
        result += "(" + tree2str(root.left) + ")";
        if (root.right != null) {
            result += "(" + tree2str(root.right) + ")";
        }
        return result;
    }

    /**
     * 993
     * 二叉树的堂兄弟节点
     *
     * @param root
     * @param x
     * @param y
     * @return
     */
    public static boolean isCousins(TreeNode root, int x, int y) {
        if (root == null) {
            return false;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int len = queue.size();
            int xIndex = -1, yIndex = -1;
            for (int i = 0; i < len; i++) {
                TreeNode node = queue.poll();
                if (node == null) {
                    continue;
                }
                if (node.val == x) {
                    xIndex = i;
                }
                if (node.val == y) {
                    yIndex = i;
                }
                queue.offer(node.left);
                queue.offer(node.right);
            }
            if (yIndex == -1 || xIndex == -1) {
                continue;
            }
            if (Math.abs(yIndex - xIndex) > 1 || Math.max(xIndex, yIndex) % 2 == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 559
     * N叉树的最大深度
     *
     * @param root
     * @return
     */
    public int maxDepth(NTreeNode root) {
        if (root == null) {
            return 0;
        }
        int maxDepth = 0;
        for (NTreeNode node : root.nodes) {
            maxDepth = Math.max(maxDepth(node), maxDepth);
        }
        return maxDepth + 1;
    }

    /**
     * 剑指 Offer 07
     * 重建二叉树
     * <p>
     * 前序遍历 preorder = [3,9,20,15,7]
     * 中序遍历 inorder = [9,3,15,20,7]
     *
     * @param preorder
     * @param inorder
     * @return
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null || preorder.length == 0 || preorder.length != inorder.length) {
            return null;
        }
        Map<Integer, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            indexMap.put(inorder[i], i);
        }
        return buildTrav(preorder, 0, preorder.length - 1, 0, indexMap);
    }

    public TreeNode buildTrav(int[] preorder, int preLeft, int preRight, int inLeft, Map<Integer, Integer> indexMap) {
        if (preLeft > preRight) {
            return null;
        }
        TreeNode root = new TreeNode(preorder[preLeft]);
        if (preLeft == preRight) {
            return root;
        }
        int rootIndex = indexMap.get(preorder[preLeft]);
        int leftNodes = rootIndex - inLeft;
        root.left = buildTrav(preorder, preLeft + 1, leftNodes + preLeft, inLeft, indexMap);
        root.right = buildTrav(preorder, preLeft + leftNodes + 1, preRight, rootIndex + 1, indexMap);
        return root;
    }

    /**
     * preorder = [3, 9, 8, 5, 4, 10, 20, 15, 7]
     * inorder = [4, 5, 8, 10, 9, 3, 15, 20, 7]
     */
    public TreeNode buildTreeV2(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null || preorder.length == 0 || preorder.length != inorder.length) {
            return null;
        }
        TreeNode root = new TreeNode(preorder[0]);
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        int inorderIndex = 0;
        for (int i = 1; i < preorder.length; i++) {
            int preorderVal = preorder[i];
            TreeNode node = stack.peek();
            if (node.val != inorder[inorderIndex]) {
                node.left = new TreeNode(preorderVal);
                stack.push(node.left);
            } else {
                if (!stack.isEmpty() && stack.peek().val == inorder[inorderIndex]) {
                    node = stack.pop();
                    inorderIndex++;
                }
                node.right = new TreeNode(preorderVal);
                stack.push(node.right);
            }
        }
        return root;
    }

    /**
     * 1145
     * 二叉树着色游戏
     *
     * @param root
     * @param n
     * @param x
     * @return
     */
    int leftNum = 0, rightNum = 0;

    public boolean btreeGameWinningMove(TreeNode root, int n, int x) {
        backorder1145(root, x);
        int half = n / 2;
        return leftNum > half || rightNum > half || leftNum + rightNum < half;
    }

    public int backorder1145(TreeNode root, int x) {
        if (root == null) {
            return 0;
        }
        int left = backorder1145(root.left, x);
        int right = backorder1145(root.right, x);
        if (root.val == x) {
            leftNum = left;
            rightNum = right;
        }
        return left + right + 1;
    }

    /**
     * 面试题 04.10
     * 检查子树
     *
     * @param t1
     * @param t2
     * @return
     */
    public boolean checkSubTree(TreeNode t1, TreeNode t2) {
        if (t1 == null) {
            return false;
        }
        return isSameTree(t1, t2) || checkSubTree(t1.left, t2) || checkSubTree(t1.right, t2);
    }

    public boolean isSameTree(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) {
            return true;
        }
        if (t1 == null || t2 == null) {
            return false;
        }
        if (t1.val != t2.val) {
            return false;
        }
        return isSameTree(t1.left, t2.left) && isSameTree(t1.right, t2.right);
    }

    /**
     * 222
     * 完全二叉树的节点个数
     *
     * @param root
     * @return
     */
    public int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = countNodes(root.left);
        int right = countNodes(root.right);
        return left + right + 1;
    }

    /**
     * 1448
     * 统计二叉树中好节点的数目
     *
     * @param root
     * @return
     */
    public int count = 0;

    public int goodNodes(TreeNode root) {
        traversal1448(root, Integer.MIN_VALUE);
        return count;
    }

    public void traversal1448(TreeNode root, int max) {
        if (root == null) {
            return;
        }
        if (root.val >= max) {
            max = root.val;
            count++;
        }
        traversal1448(root.left, max);
        traversal1448(root.right, max);
    }

}
