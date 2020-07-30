package com.nokia.example.leetcode.tree;

import com.nokia.example.leetcode.entity.TreeNode;

/**
 * @author by YingLong on 2020/7/29
 */
public class SymmetricTree {

    public boolean isSymmetric(TreeNode root) {
        return check(root, root);
    }

    public boolean check(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null || p.val != q.val) {
            return false;
        }
        return check(p.left, q.right) && check(p.right, q.left);
    }
}
