package org.sino.demo.datastructure.graph.algorithms;

import org.sino.demo.datastructure.graph.Undigraph;
import org.sino.demo.datastructure.graph.VisitedVertex;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ShortestPathAlgorithm {

    /**
     * 计算最短路径
     *
     * @param undigraph   无向图
     * @param startVertex 起始顶点
     * @param endVertex   终点顶点
     * @param byWeight    是否使用权值计算
     * @return 到其他顶点及其最短路径
     */
    public static List<String> calcShortestPath(Undigraph undigraph, String startVertex, String endVertex, boolean byWeight) {
        if (byWeight) {
            return calcShortestPathByDijkstra(undigraph, startVertex, endVertex);
        }
        return calcShortestPathByBfs(undigraph, startVertex, endVertex);
    }

    private static List<String> calcShortestPathByDijkstra(Undigraph undigraph, String startVertex, String endVertex) {
        Set<String> vertexSet = undigraph.getVertexSet();
        if (!vertexSet.contains(startVertex) || !vertexSet.contains(endVertex)) {
            return new ArrayList<>();
        }
        //邻接边
        Map<String, Map<String, Integer>> adjMap = undigraph.getAdjMap();
        // 已计算最短路径的顶点明细信息
        Map<String, VisitedVertex> calcVertexMap = new HashMap<>();
        // 待计算距离集
        Map<String, VisitedVertex> disVertexMap = initDisInfo(startVertex, vertexSet, adjMap);

        LinkedList<String> preQueue = new LinkedList<>();
        preQueue.add(startVertex);
        while(!preQueue.isEmpty()) {
            String preVertex = preQueue.poll();
            // 获取当前顶点到出发顶点的距离，并把当前顶点从距离集中删除点
            VisitedVertex preVisitedVertex = disVertexMap.get(preVertex);
            calcVertexMap.put(preVertex, preVisitedVertex);
            disVertexMap.remove(preVertex);

            // 重新计算当前顶点到其他顶点的距离
            calcShortestDistance(preVisitedVertex, adjMap, disVertexMap);
            //从待计算距离集中获取最短的一个顶点
            VisitedVertex minVisitedVertex = getMin4DisMap(disVertexMap);
            if(null == minVisitedVertex) {
                break;
            }
            preQueue.add(minVisitedVertex.getVertexName());
        }
        if(null != calcVertexMap.get(endVertex)) {
            return calcVertexMap.get(endVertex).getPath();
        }
        return new ArrayList<>();
    }

    private static void calcShortestDistance(VisitedVertex preVisitedVertex, Map<String, Map<String, Integer>> adjMap, Map<String, VisitedVertex> disVertexMap) {
        String preVertex = preVisitedVertex.getVertexName();
        int preWeightSum = preVisitedVertex.getWeightSum();
        Map<String, Integer> adjVertex = adjMap.get(preVertex);
        adjVertex.forEach((key, weight) -> {
            if(disVertexMap.containsKey(key)) {
                VisitedVertex visitedVertex = disVertexMap.get(key);
                int weightSum = visitedVertex.getWeightSum();
                if(preWeightSum == 0 || weightSum == -1 || (weight != -1 && weight + preWeightSum < weightSum)) {
                    visitedVertex.visited(key, weight, preVisitedVertex);
                    disVertexMap.put(key, visitedVertex);
                }
            }
        });
    }

    private static VisitedVertex getMin4DisMap(Map<String, VisitedVertex> disMap) {
        AtomicInteger min = new AtomicInteger(Integer.MAX_VALUE);
        AtomicReference<String> minKey = new AtomicReference<>("");
        disMap.forEach((key, visitedVertex) -> {
            int weightSum = visitedVertex.getWeightSum();
            if(weightSum != -1 && weightSum < min.get()) {
                min.set(weightSum);
                minKey.set(key);
            }
        });
        if(!"".equals(minKey.get())) {
            return disMap.get(minKey.get());
        }
        return null;
    }

    private static Map<String, VisitedVertex> initDisInfo(String startVertex,
            Set<String> vertexSet, Map<String, Map<String, Integer>> adjMap) {
        Map<String, VisitedVertex> disMap = new HashMap<>();
        Map<String, Integer> adjVertexMap = adjMap.get(startVertex);
        // 初始化距离信息
        vertexSet.forEach(vertex -> {
            int weight = -1;
            if(adjVertexMap.containsKey(vertex)) {
                weight = adjVertexMap.get(vertex);
            } else if (vertex.equals(startVertex)) {
                weight = 0;
            }
            visitedVertex(vertex, weight, startVertex, disMap);
        });
        return disMap;
    }

    private static List<String> calcShortestPathByBfs(Undigraph undigraph, String startVertex, String endVertex) {
        Set<String> vertexSet = undigraph.getVertexSet();
        if (!vertexSet.contains(startVertex) || !vertexSet.contains(endVertex)) {
            return new ArrayList<>();
        }
        //邻接边
        Map<String, Map<String, Integer>> adjMap = undigraph.getAdjMap();
        // 已访问的顶点集
        Set<String> visitedVertexSet = new HashSet<>();
        // 已访问的顶点明细信息
        Map<String, VisitedVertex> visitedVertexMap = new HashMap<>();

        LinkedList<String> preQueue = new LinkedList<>();
        preQueue.add(startVertex);
        // 出发顶点的访问
        visitedVertex(startVertex, 0, startVertex, visitedVertexMap);

        while (!preQueue.isEmpty()) {
            String preVertex = preQueue.poll();
            if(visitedVertexSet.contains(preVertex)) {
                continue;
            }
            visitedVertexSet.add(preVertex);

            Map<String, Integer> adjVertexMap = adjMap.get(preVertex);
            if (CollectionUtils.isEmpty(adjVertexMap)) {
                continue;
            }

            boolean isEnd = false;
            for (String currentVertex : adjVertexMap.keySet()) {
                if(visitedVertexSet.contains(currentVertex)) {
                    continue;
                }
                Integer weight = adjVertexMap.get(currentVertex);
                visitedVertex(currentVertex, weight, preVertex, visitedVertexMap);

                if(currentVertex.equals(endVertex)) {
                    isEnd = true;
                    break;
                }

                preQueue.add(currentVertex);
            }

            if(isEnd) {
                break;
            }
        }
        return visitedVertexMap.get(endVertex).getPath();
    }

    private static void visitedVertex(String currentVertex, Integer weight, String preVertex, Map<String, VisitedVertex> visitedVertexMap){
        VisitedVertex preVisitedVertex;
        if(visitedVertexMap.containsKey(preVertex)) {
            preVisitedVertex = visitedVertexMap.get(preVertex);
        } else {
            preVisitedVertex = new VisitedVertex();
        }
        VisitedVertex currentVisitedVertex = new VisitedVertex();
        currentVisitedVertex.visited(currentVertex, weight, preVisitedVertex);
        visitedVertexMap.put(currentVertex, currentVisitedVertex);
    }
}
