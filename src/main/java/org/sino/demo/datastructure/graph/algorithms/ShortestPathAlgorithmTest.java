package org.sino.demo.datastructure.graph.algorithms;

import org.sino.demo.datastructure.graph.BaseLogicalConnection;
import org.sino.demo.datastructure.graph.Undigraph;
import org.sino.demo.datastructure.graph.VisitedVertex;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;

import java.util.*;

public class ShortestPathAlgorithmTest {

    public static void main(String[] args) {
        Undigraph undigraph = buildGraph();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("calc 20w");
        for(int i = 0; i < 200000; i ++) {
            List<String> aePath = ShortestPathAlgorithm.calcShortestPath(undigraph, "A", "E", true);
            System.out.println("A->E:" + Arrays.toString(aePath.toArray()));
        }
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

    private static Undigraph buildGraph(){
        List<BaseLogicalConnection> logicalList = new ArrayList<>();
        // A -> B
        BaseLogicalConnection ab = new BaseLogicalConnection();
        ab.setSrcNeServiceKey("A");
        ab.setSinkNeServiceKey("B");
        ab.setCost(5F);
        logicalList.add(ab);

        // A->C
        BaseLogicalConnection ac = new BaseLogicalConnection();
        ac.setSrcNeServiceKey("A");
        ac.setSinkNeServiceKey("C");
        ac.setCost(7F);
        logicalList.add(ac);

        // A->G
        BaseLogicalConnection ag = new BaseLogicalConnection();
        ag.setSrcNeServiceKey("A");
        ag.setSinkNeServiceKey("G");
        ag.setCost(2F);
        logicalList.add(ag);

        // B->D
        BaseLogicalConnection bd = new BaseLogicalConnection();
        bd.setSrcNeServiceKey("B");
        bd.setSinkNeServiceKey("D");
        bd.setCost(9F);
        logicalList.add(bd);

        // D->F
        BaseLogicalConnection df = new BaseLogicalConnection();
        df.setSrcNeServiceKey("D");
        df.setSinkNeServiceKey("F");
        df.setCost(4F);
        logicalList.add(df);

        // F->E
        BaseLogicalConnection fe = new BaseLogicalConnection();
        fe.setSrcNeServiceKey("F");
        fe.setSinkNeServiceKey("E");
        fe.setCost(5F);
        logicalList.add(fe);

        // C->E
        BaseLogicalConnection ce = new BaseLogicalConnection();
        ce.setSrcNeServiceKey("C");
        ce.setSinkNeServiceKey("E");
        ce.setCost(8F);
        logicalList.add(ce);

        // G->E
        BaseLogicalConnection ge = new BaseLogicalConnection();
        ge.setSrcNeServiceKey("G");
        ge.setSinkNeServiceKey("E");
        ge.setCost(4F);
        logicalList.add(ge);
        return new Undigraph(logicalList, null);
    }
}
