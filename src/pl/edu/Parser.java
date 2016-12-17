package pl.edu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.edu.pojo.Demand;
import pl.edu.pojo.Edge;
import edu.asu.emit.algorithm.graph.Path;
import edu.asu.emit.algorithm.graph.Graph;
import edu.asu.emit.algorithm.graph.abstraction.BaseVertex;
import edu.asu.emit.algorithm.graph.shortestpaths.YenTopKShortestPathsAlg;
import edu.asu.emit.algorithm.utils.Pair;

public class Parser {

	public static Map<Demand, List<Path>> parse(String graphFileName,
			String demandsFileName, int numberOfPaths) {

		Map<Demand, List<Path>> map = new HashMap<Demand, List<Path>>();
		List<Demand> demands = new ArrayList<Demand>();
		
		// get demands
		try {
			// 1. read the file and put the content in the buffer
			FileReader input = new FileReader(demandsFileName);
			BufferedReader bufRead = new BufferedReader(input);

			String line = bufRead.readLine();
			int count = 1;
			while (line != null) {
				String[] strList = line.trim().split("\\s");
				demands.add(new Demand(count, Integer.parseInt(strList[0]), Integer
						.parseInt(strList[1]), Integer.parseInt(strList[2])));
				line = bufRead.readLine();
				count++;
			}
			bufRead.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// load graph
		Graph graph = new Graph(graphFileName);
		System.out.println("\nVertices: " + graph.getVertexList());
		
		// get intial loads and fill list of edges
		Map<Pair<Integer, Integer>, Integer> edgesMap = graph.getEdges();
		List<Edge> edges = new ArrayList<Edge>();
		int count = 1;
		for (Pair<Integer, Integer> pair : edgesMap.keySet()) {
			Edge edge = new Edge(count, pair.first(), pair.second(), edgesMap.get(pair));
			edges.add(edge);
			count++;
		}

		// get list of paths for every demand
		for (Demand d : demands) {
			YenTopKShortestPathsAlg yenAlg = new YenTopKShortestPathsAlg(graph);
			List<Path> shortest_paths_list = yenAlg.getShortestPaths(
					graph.getVertex(d.getSrcNode()),
					graph.getVertex(d.getDstNode()), numberOfPaths);
			System.out.println("\nSource node: " + d.getSrcNode()
					+ ", destination node: " + d.getDstNode());
			System.out.println("Shortest paths:" + shortest_paths_list);
			map.put(d, shortest_paths_list);
		}
		
		return map;
	}

	public static void dumpToFile(Map<Demand, List<Path>> demandPathsMap, String fileName) {

		try {
			PrintWriter writer = new PrintWriter("output/" + fileName, "UTF-8");
			int count = 1;
			for (Demand d : demandPathsMap.keySet()) {
				
				writer.println("Demand: " + count);
				
				// demand
				writer.println(d.getSrcNode() + " " + d.getDstNode() + " " + d.getValue());
				
				// number of paths
				List<Path> paths = demandPathsMap.get(d);				
				writer.println(paths.size());

				
				for (Path p : paths) {
					// number of edges in path
					writer.print(paths.indexOf(p) + 1 + " ");
					// edges ids
					for (BaseVertex v : p.getVertexList()) {
						writer.print(v.getId() + " ");
					}
					writer.println();
				}
				writer.println();
				count++;
			}

			writer.close();
		} catch (IOException e) {
			System.out.println("IOException: " + e.toString());
		}

	}

}
