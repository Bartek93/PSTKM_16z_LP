package pl.edu;

import java.util.List;
import java.util.Map;

import edu.asu.emit.algorithm.graph.Path;
import pl.edu.pojo.CplexInput;
import pl.edu.pojo.Demand;
import pl.edu.pojo.Edge;
import pl.edu.pojo.PathWithEgdes;
import ilog.concert.*;
import ilog.cplex.*;

public class Model {
	
	
	// params
	private int modularity;
	private int[] demands;
	private int[] initalLoad;
	private int[][][] delta_edp;
	
	// variables
	
	

	public Model() {
		
	}

	public void createModel(CplexInput cplexInput, int numberOfPaths) {

		try {
			Map<Demand, List<PathWithEgdes>> demandPathsMap = cplexInput
					.getDemandPathsMap();

			int d_length = demandPathsMap.keySet().size();
			demands = new int[d_length];
			
			for(Demand d : demandPathsMap.keySet()) {
				demands[d.getId()-1] = d.getValue();
			}
			
			
			
			
			int p_length = 0;
			for (Demand d : demandPathsMap.keySet()) {
				p_length = demandPathsMap.get(d).size();
				break;
			}
			List<Edge> E = cplexInput.getEdges();

			// definicja deldta_edp
			delta_edp = new int[E.size()][d_length][p_length];

			System.out.println("\n\n\nBuild delta");

			for (Edge e : E) {
                                System.out.println("---------- Egde " + e.getIndex() 
                                        + " (" + e.getStartNode() + "  -> " + e.getEndNode() +  ") -----------");
				for (Demand d : demandPathsMap.keySet()) {
                                        System.out.println("-----  Demand " + d.getId() 
                                                 + " (" + d.getSrcNode() + "  -> " + d.getDstNode() +  ") -----");
					List<PathWithEgdes> paths = demandPathsMap.get(d);
					for (PathWithEgdes p : paths) {
						if (checkInPath(e, p)) {
                                                        System.out.println("Path: " + p.getIndex() + " contains edge: " + e.getIndex());
							System.out.println("Path: " + p);
							System.out.println("Edge: " + e.getIndex());
							System.out.println("Demand: " + d.getId());
							System.out.println("Is in path: " + p.getIndex());
							delta_edp[e.getIndex() - 1][d.getId() - 1]
                                                                [p.getIndex() - 1] = 1;
						}
                                                else
                                                    System.out.println("Path: " + p.getIndex() + " NOT contain edge: " + e.getIndex());
					}
				}
			}

			IloCplex cplex = new IloCplex();

			// x_dp
			IloIntVar[][] x_dp = new IloIntVar[d_length][p_length];
			for (Demand d : demandPathsMap.keySet()) {
				List<PathWithEgdes> paths = demandPathsMap.get(d);
				for (PathWithEgdes p : paths) {
					
					x_dp[d.getId() - 1][p.getIndex()-1] = cplex.intVar(0,
							Integer.MAX_VALUE, "x_" + d.getId() + "-" + p.getIndex());
				}
			}
				
			// Definicja zmiennej h_d, volumen zapotrzebowania d, h_d >= 0
			IloIntVar y_e = cplex.intVar(0, Integer.MAX_VALUE, "y_e");


			// OGRANICZENIA
			IloLinearIntExpr[] VOLUME_D = new IloLinearIntExpr[demandPathsMap.keySet().size()];
			IloLinearIntExpr[] EDGE_CAPACITY = new IloLinearIntExpr[E.size()];
			
			
			// ograniczenie numer 1
			for(int d=0; d<demands.length; d++) {
				VOLUME_D[d] = cplex.linearIntExpr();
				for(int p=0; p<p_length; p++) {
					VOLUME_D[d].addTerm(1, x_dp[d][p]);
				}
				cplex.addEq(VOLUME_D[d], demands[d]);
			}
			
					
//			for(int e = 0; e <E.size();e++) {
//				EDGE_CAPACITY[e] = cplex.linearIntExpr();
//				
//				
//				
//				for(Demand d : demandPathsMap.keySet()) {
//					List<PathWithEgdes> paths = demandPathsMap.get(d);
//					for(PathWithEgdes p : paths) {
//						Integer delta = delta_edp[E.get(e).getIndex()-1][d.getId() - 1][p.getIndex() - 1];
//						IloIntVar x = x_dp[d.getId() - 1][p.getIndex() - 1];
//						
//					}
//				}
//			}
			
			
			
			
//			// ograniczenie na pojemnosc lacza
//			for(Edge e : E) {
//				EDGE_CAPACITY[] = cplex.linearIntExpr();
//				for(Demand d : demandPathsMap.keySet()) {
//					List<PathWithEgdes> paths = demandPathsMap.get(d);
//					for(PathWithEgdes p : paths) {
//						Integer delta = delta_edp[e.getIndex()-1][d.getId() - 1][p.getIndex() - 1];
//						IloIntVar x = x_dp[d.getId() - 1][p.getIndex() - 1];
//						
//					}
//				}
//			}
			
			
			cplex.addMinimize(y_e);
			
			
			if(cplex.solve()) {
				System.out.println("Solved");
			}
			
			/*
			 * TUTORIAL:
			 * https://www.youtube.com/watch?v=HMLLDp476ts&index=3&list
			 * =PL9xwgp-nwd-wwPhYaN3vUyduglg2m-xHO //cplex syntax exaple (it
			 * does nothing in project :D its only example): //min(0.5*x + y)
			 * //define vars: IloLineraNumExpr objective =
			 * cplex.lineraNumExpr(); objective.addTerm(0.5, x);
			 * objective.addTerm(1, y);
			 * 
			 * //define objective cplex.addMinimize(objective);
			 * 
			 * //define constraints cplex.addGe(cplex.sum(cplex.prod(60,
			 * x)),cplex.prod(60, y)), 300); // 60x+60y >= 300
			 * cplex.addLe(cplex.sum(cplex.prod(60, x)),cplex.prod(60, y)),
			 * 300); //60x+60y <= 300 cplex.addEq(cplex.sum(cplex.prod(60,
			 * x)),cplex.prod(60, y)), 300); //60x+60y = 300
			 */

		} catch (Exception ex) {
			System.out.println("IloException: " + ex);
		}
	}

	private boolean checkInPath(Edge e, PathWithEgdes p) {
		if (p.getEdges().contains(e))
			return true;
		return false;
	}
}
