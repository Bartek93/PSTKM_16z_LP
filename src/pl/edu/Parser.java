package pl.edu;

import java.util.List;
import java.util.Map;

import edu.asu.emit.algorithm.graph.Path;
import edu.asu.emit.algorithm.graph.Graph;
import edu.asu.emit.algorithm.graph.shortestpaths.YenTopKShortestPathsAlg;
import edu.asu.emit.algorithm.utils.Pair;

public class Parser {
	
	

	public static Integer[] parse(String dataFileName, int srcNode,
			int dstNode, int numberOfPaths) {

		Graph graph = new Graph(dataFileName);
		System.out.println("Vertices: " + graph.getVertexList());
		YenTopKShortestPathsAlg yenAlg = new YenTopKShortestPathsAlg(graph);
		List<Path> shortest_paths_list = yenAlg.getShortestPaths(
				graph.getVertex(srcNode), graph.getVertex(dstNode),
				numberOfPaths);
		System.out.println("Source node: " + srcNode + ", destination node: " + dstNode);
		System.out.println("Shortest paths:" + shortest_paths_list);

		// Object x = shortest_paths_list.toArray();
		// System.out.println(yenAlg.getResultList().size());

//		if (shortest_paths_list.size() < numberOfPaths) {
//			throw new ExceptionInInitializerError();
//		}
		
		System.out.println("Initial load: ");
		Map<Pair<Integer, Integer>, Integer> edges = graph.getEdges();
		for(Pair<Integer, Integer> pair : edges.keySet()) {
			System.out.println("Edge " + pair.first() + " - " + pair.second() + ": " + edges.get(pair));
		}

		String optimalVertexList = shortest_paths_list.get(numberOfPaths - 1)
				.getVertexList().toString();

		System.out.println("Optimal path from: " + srcNode + " to: " + dstNode + "  - "
				+ optimalVertexList);

		String[] tokens = optimalVertexList.split(":");
		String[] optimalVertexTab = tokens[0].replaceAll("\\[", "").replaceAll("\\]", "")
				.replaceAll(" ", "").split(",");

		Integer[] results = new Integer[optimalVertexTab.length];

		for (int i = 0; i < optimalVertexTab.length; i++) {
			try {
				results[i] = Integer.parseInt(optimalVertexTab[i]);
				 System.out.print(results[i].toString());
				 System.out.println();
			} catch (NumberFormatException nfe) {
				System.err.println(nfe.toString());
			}
		}
		return results;
	}

}
