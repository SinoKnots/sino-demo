package org.sino.demo.datastructure.graph;

import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.util.buf.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 已访问的顶点
 * @author hzj
 * @since 2022-09-18
 */
@Getter
public class VisitedVertex {
    /**
     * 当前顶点名
     */
    private String vertexName;
    /**.
     * 前驱顶点名
     */
    private String preVertexName;
    /**
     * 从出发顶点到本顶点的最小权值
     */
    private Integer weightSum = 0;
    /**
     * 从出发顶点到本顶点的最短路径
     */
    private List<String> path = new ArrayList<>();

    public void visited(String vertexName, Integer weight, VisitedVertex preVisitedVertex){
        this.preVertexName = null == preVisitedVertex.getVertexName() ? vertexName : preVisitedVertex.getVertexName();
        this.vertexName = vertexName;
        this.weightSum = preVisitedVertex.getWeightSum() + weight;
        this.path = new ArrayList<>(preVisitedVertex.getPath());
        this.path.add(this.vertexName);
    }
}
