package org.sino.demo.datastructure.graph;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class ShortestPathResult {
    private final List<String> currentPath;
    private final Map<String, List<String>> overbrimPath;

    public ShortestPathResult(String startVertex, String endVertex, Map<String, VisitedVertex> visitedVertexMap){
        this.overbrimPath = new HashMap<>();
        VisitedVertex endVisitedVertex = visitedVertexMap.get(endVertex);
        this.currentPath = endVisitedVertex.getPath();

        visitedVertexMap.forEach((end, visitedVertex) -> {
            String key = startVertex + "@" + end;
            List<String> overPath = visitedVertex.getPath();
            this.overbrimPath.put(key, overPath);
        });
    }
}
