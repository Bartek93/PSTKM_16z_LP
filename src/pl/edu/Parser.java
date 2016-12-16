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
			while (line != null) {
				String[] strList = line.trim().split("\\s");
				demands.add(new Demand(Integer.parseInt(strList[0]), Integer
						.parseInt(strList[1]), Integer.parseInt(strList[2])));
				line = bufRead.readLine();
			}
			bufRead.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Graph graph = new Graph(graphFileName);
		System.out.println("Initial load: ");
		Map<Pair<Integer, Integer>, Integer> edges = graph.getEdges();
		for (Pair<Integer, Integer> pair : edges.keySet()) {
			System.out.println("Edge " + pair.first() + " - " + pair.second()
					+ ": " + edges.get(pair));
		}
		System.out.println("Vertices: " + graph.getVertexList());
		YenTopKShortestPathsAlg yenAlg = new YenTopKShortestPathsAlg(graph);

		for (Demand d : demands) {
			List<Path> shortest_paths_list = yenAlg.getShortestPaths(
					graph.getVertex(d.getSrcNode()),
					graph.getVertex(d.getDstNode()), numberOfPaths);
			System.out.println("Source node: " + d.getSrcNode()
					+ ", destination node: " + d.getDstNode());
			System.out.println("Shortest paths:" + shortest_paths_list);
			map.put(d, shortest_paths_list);
		}

		// Object x = shortest_paths_list.toArray();
		// System.out.println(yenAlg.getResultList().size());

		// if (shortest_paths_list.size() < numberOfPaths) {
		// throw new ExceptionInInitializerError();
		// }

		// String optimalVertexList = shortest_paths_list.get(numberOfPaths - 1)
		// .getVertexList().toString();
		//
		// System.out.println("Optimal path from: " + srcNode + " to: " +
		// dstNode
		// + "  - " + optimalVertexList);

		// String[] tokens = optimalVertexList.split(":");
		// String[] optimalVertexTab = tokens[0].replaceAll("\\[", "")
		// .replaceAll("\\]", "").replaceAll(" ", "").split(",");
		//
		// Integer[] results = new Integer[optimalVertexTab.length];
		//
		// for (int i = 0; i < optimalVertexTab.length; i++) {
		// try {
		// results[i] = Integer.parseInt(optimalVertexTab[i]);
		// System.out.print(results[i].toString());
		// System.out.println();
		// } catch (NumberFormatException nfe) {
		// System.err.println(nfe.toString());
		// }
		// }

		return map;
	}

	public static void dumpToFile(Map<Demand, List<Path>> demandPathsMap,
			String fileName) {

		try {
			PrintWriter writer = new PrintWriter("output/" + fileName, "UTF-8");
			int count = 1;
			for (Demand d : demandPathsMap.keySet()) {
				writer.println("Demand: " + count);
				// demand
				writer.println(d.getSrcNode() + " " + d.getDstNode() + " "
						+ d.getValue());
				List<Path> paths = demandPathsMap.get(d);
				// number of paths
				writer.println(paths.size());

				for (Path p : paths) {
					writer.print(paths.indexOf(p) + 1 + " ");
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

		}

	}

}
