package pl.edu.pojo;

import java.util.List;
import java.util.Map;

public class CplexInput {
	
	private Map<Demand, List<PathWithEgdes>> demandPathsMap; // map demands : list of paths that realizes this demand
	private List<Edge> edges; // all edges in graph
	
	public Map<Demand, List<PathWithEgdes>> getDemandPathsMap() {
		return demandPathsMap;
	}
	public void setDemandPathsMap(Map<Demand, List<PathWithEgdes>> demandPathsMap) {
		this.demandPathsMap = demandPathsMap;
	}
	public List<Edge> getEdges() {
		return edges;
	}
	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}
	
	public Edge getEdgeByNodePair(int src, int dst) {
		for(Edge e : edges) {
			if(e.getStartNode() == src && e.getEndNode() == dst) {
				return e;
			} else if (e.getEndNode() == src && e.getStartNode() == dst) {
				return e;
			}
		}
		return null;
	}
	@Override
	public String toString() {
		return "CplexInput [demandPathsMap=" + demandPathsMap + ", edges="
				+ edges + "]";
	}
	
	

}
