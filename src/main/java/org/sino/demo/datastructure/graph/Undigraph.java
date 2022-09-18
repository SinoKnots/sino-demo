package org.sino.demo.datastructure.graph;

import lombok.Getter;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 无向图
 * @author hzj
 * @since 2022-09-18
 */
@Getter
public class Undigraph {

    /**
     * 顶点集
     */
    private final Set<String> vertexSet;

    /**
     * 邻接表
     */
    private final Map<String, Map<String, Integer>> adjMap;

    /**
     * 创建无向图对象
     * @param logicalList 逻辑连接
     * @param physicalList 物理连接
     */
    public Undigraph(List<BaseLogicalConnection> logicalList, List<BasePhysicalLink> physicalList) {
        this.vertexSet = new HashSet<>();
        this.adjMap = new HashMap<>();

        if(!CollectionUtils.isEmpty(logicalList)) {
            logicalList.forEach(logical -> {
                vertexSet.add(logical.getSrcNeServiceKey());
                vertexSet.add(logical.getSinkNeServiceKey());
                Integer cost = float2Integer(logical.getCost());
                putLink2adj(logical.getSrcNeServiceKey(), logical.getSinkNeServiceKey(), cost);
                putLink2adj(logical.getSinkNeServiceKey(), logical.getSrcNeServiceKey(), cost);
            });
        }

        if(!CollectionUtils.isEmpty(physicalList)) {
            physicalList.forEach(physical -> {
                vertexSet.add(physical.getSrcNeServiceKey());
                vertexSet.add(physical.getSinkNeServiceKey());
                Integer cost = float2Integer(physical.getCost());
                putLink2adj(physical.getSrcNeServiceKey(), physical.getSinkNeServiceKey(), cost);
                putLink2adj(physical.getSinkNeServiceKey(), physical.getSrcNeServiceKey(), cost);
            });
        }
    }

    private void putLink2adj(String src, String sink, Integer cost) {
        if(!this.adjMap.containsKey(src)) {
            Map<String, Integer> newAdj = new HashMap<>();
            newAdj.put(sink, cost);
            this.adjMap.put(src, newAdj);
            return;
        }

        Map<String, Integer> subAdjMap = this.adjMap.get(src);
        if(subAdjMap.containsKey(sink)) {
            return;
        }

        subAdjMap.put(sink, cost);
        this.adjMap.put(src, subAdjMap);
    }

    private Integer float2Integer(Float cost) {
        float fl = cost * 100;
        return (int) fl;
    }
}
