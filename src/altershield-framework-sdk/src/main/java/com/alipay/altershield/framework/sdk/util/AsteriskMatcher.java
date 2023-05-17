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

import com.alipay.altershield.framework.common.util.CommonUtil;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author liushengdong
 * @version $Id: AsteriskMatcher.java, v 0.1 2019年01月24日 14:49 liushengdong Exp $
 */
public class AsteriskMatcher {

    private TreeNode root;
    private String separatorChars = ".";
    private boolean isMatchAll = false;

    public AsteriskMatcher(Set<String> set) {
        if (set == null) {
            this.root = null;
            return;
        }

        if (set.contains("*")) {
            isMatchAll = true;
            return;
        }

        TreeNode root = new TreeNode("");
        for (String s : set) {
            // 格式不合法,目前只支持 com.alipay.*格式,不支持 com.alipay* , com.*.do , .*
            if (CommonUtil.isBlank(s) || s.length() <= 2) {
                continue;
            }
            // 包含* 且 (*的位置不是最后一位 或者 倒数数第二位不是.) 说明*的格式不合法
            if (s.contains("*") && (s.indexOf("*") != s.length() - 1
                    || s.lastIndexOf(separatorChars) != s.length() - 2)) {
                continue;
            }

            List<String> names = CommonUtil.splitAsList(s, separatorChars, true);
            TreeNode curr = root;
            for (String name : names) {
                // 只有不同名才需要put
                curr.putIfAbsent(name);
                curr = curr.getChildByName(name);
            }
            // END node
            curr.putIfAbsent(null);
        }
        this.root = root;
    }

    public boolean match(String str) {
        if (isMatchAll) {
            return true;
        }
        if (root == null || !root.hasChild()) {
            return false;
        } else {
            List<String> strs = CommonUtil.splitAsList(str, separatorChars, true);
            Iterator<String> iterator = strs.iterator();
            return matchByTree(root, iterator);
        }
    }

    private boolean matchByTree(TreeNode node, Iterator<String> iter) {
        // 被匹配的字符完结:存在END节点->true 不存在END节点->false
        if (!iter.hasNext()) {
            return node.getChildByName(null) != null;
        }

        // 被匹配字符没有完结:存在通配符*
        if (node.getChildByName("*") != null) {
            return true;
        }

        String curr = iter.next();
        TreeNode child = node.getChildByName(curr);

        if (child == null) {
            return false;
        } else {
            return matchByTree(child, iter);
        }
    }

    @Override
    public String toString() {
        return "AsteriskMatcher{" + "root=" + (root != null ? root.print() : null)
                + ", separatorChars='" + separatorChars + '\'' + '}';
    }
}
