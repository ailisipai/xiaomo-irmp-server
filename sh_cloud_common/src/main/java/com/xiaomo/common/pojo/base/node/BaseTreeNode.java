
package com.xiaomo.common.pojo.base.node;

import java.util.List;

/**
 * 树节点接口
 *
 * @author xiaomo
 * @date 2021/4/5 14:07
 */
public interface BaseTreeNode {


    /**
     * 获取节点id
     *
     * @return 节点id
     * @author xiaomo
     * @date 2021/7/9 18:36
     */
    Long getId();

    /**
     * 获取节点父id
     *
     * @return 节点父id
     * @author xiaomo
     * @date 2021/7/9 18:36
     */
    Long getPid();

    /**
     * 设置children
     *
     * @param children 子节点集合
     * @author xiaomo
     * @date 2021/7/9 18:36
     */
    void setChildren(List children);
}
