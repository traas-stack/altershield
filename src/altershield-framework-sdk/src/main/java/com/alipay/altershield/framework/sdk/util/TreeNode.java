/*
 * MIT License
 *
 * Copyright (c) [2023]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.alipay.altershield.framework.sdk.util;

import java.util.*;

/**
 *
 * @author liushengdong
 * @version $Id: TreeNode.java, v 0.1 2017年08月10日 下午12:01 liushengdong Exp $
 */
public class TreeNode {

    final String name;
    Map<String, TreeNode> children;
    private String namePrefix = "";

    /**
     * Constructor.
     *
     * @param name the name
     */
    public TreeNode(String name) {
        this.name = name;
        this.children = null;
    }

    /**
     * Constructor.
     *
     * @param name  the name
     * @param names the names
     */
    public TreeNode(String name, String... names) {
        this.name = name;
        Map<String, TreeNode> map = new LinkedHashMap<String, TreeNode>();
        for (String n : names) {
            map.put(n, new TreeNode(n));
        }
        this.children = map;
    }

    /**
     * Constructor.
     *
     * @param name  the name
     * @param names the names
     */
    public TreeNode(String name, TreeNode... names) {
        this.name = name;
        Map<String, TreeNode> map = new LinkedHashMap<String, TreeNode>();
        for (TreeNode treeNode : names) {
            map.put(treeNode.getName(), treeNode);
        }
        this.children = map;
    }


    /**
     * Constructor.
     *
     * @param name     the name
     * @param children the children
     */
    public TreeNode(String name, Map<String, TreeNode> children) {
        this.name = name;
        this.children = children;
    }

    /**
     * Sets set name prefix.
     *
     * @param namePrefix the name prefix
     * @return the set name prefix
     */
    public TreeNode setNamePrefix(String namePrefix) {
        this.namePrefix = namePrefix;
        return this;
    }

    /**
     * Append name prefix tree node.
     *
     * @param str the str
     * @return the tree node
     */
    public TreeNode appendNamePrefix(String str) {
        this.namePrefix += str;
        return this;
    }


    private TreeNode addChild(String name) {
        makeSureChildrenNotNull();
        this.children.put(name, new TreeNode(name));
        return this;
    }

    private TreeNode addChild(TreeNode child) {
        makeSureChildrenNotNull();
        this.children.put(child.getName(), child);
        return this;
    }

    /**
     * Put if absent tree node.
     *
     * @param name the name
     * @return the tree node
     */
    public TreeNode putIfAbsent(String name) {
        makeSureChildrenNotNull();
        TreeNode pre = this.children.get(name);
        if (pre == null) {
            addChild(name);
            return null;
        }
        return pre;
    }

    /**
     * Gets get child by name.
     *
     * @param name the name
     * @return the get child by name
     */
    public TreeNode getChildByName(String name) {
        return this.children.get(name);
    }

    /**
     * Has child boolean.
     *
     * @return the boolean
     */
    public boolean hasChild() {
        return this.children != null && !this.children.isEmpty();
    }

    /**
     * Getter method for property <tt>name</tt>.
     *
     * @return property value of name
     */
    public String getName() {
        return name;
    }

    private void makeSureChildrenNotNull() {
        if (this.children == null) {
            this.children = new LinkedHashMap<String, TreeNode>();
        }
    }

    /**
     * Print string.
     *
     * @return the string
     */
    public String print() {
        StringBuilder sb = new StringBuilder();
        print(sb, "", true);
        return sb.toString();
    }

    private void print(StringBuilder sb, String prefix, boolean isTail) {
        // 让存在换行符的字符展示也友好
        String namePrefixAfter =
                namePrefix.replace("\n", "\n" + prefix + (isTail ? "    " : "│   "));

        sb.append(prefix + (isTail ? "└── " : "├── ") + namePrefixAfter + name).append("\n");
        if (children != null) {
            // 遍历所有子节点
            List<TreeNode> list = new ArrayList<TreeNode>();
            Set<String> keySet = children.keySet();
            for (String key : keySet) {
                list.add(children.get(key));
            }
            for (int i = 0; i < list.size() - 1; i++) {
                // 嵌套调用子节点的print
                list.get(i).print(sb, prefix + (isTail ? "    " : "│   "), false);
            }
            if (list.size() > 0) {
                list.get(list.size() - 1).print(sb, prefix + (isTail ? "    " : "│   "), true);
            }
        }
    }

    /**
     * Pre string.
     *
     * @param isTail the is tail
     * @return the string
     */
    public static String pre(boolean isTail) {
        return (isTail ? "└── " : "├── ");
    }
}
