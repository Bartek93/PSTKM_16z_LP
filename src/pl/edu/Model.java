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

			for (Demand d : demandPathsMap.keySet()) {
				demands[d.getId() - 1] = d.getValue();
			}

			int p_length = 0;
			for (Demand d : demandPathsMap.keySet()) {
				p_length = demandPathsMap.get(d).size();
				break;
			}
			List<Edge> E = cplexInput.getEdges();
			int initial_modules = getInitialModules(E);
			// definicja deldta_edp
			delta_edp = new int[E.size()][d_length][p_length];


			System.out.println("\n\n\nBuild delta");

			for (Edge e : E) {
				System.out.println("---------- Egde " + e.getIndex() + " ("
						+ e.getStartNode() + "  -> " + e.getEndNode()
						+ ") -----------");
				for (Demand d : demandPathsMap.keySet()) {
					System.out.println("-----  Demand " + d.getId() + " ("
							+ d.getSrcNode() + "  -> " + d.getDstNode()
							+ ") -----");
					List<PathWithEgdes> paths = demandPathsMap.get(d);
					for (PathWithEgdes p : paths) {
						if (checkInPath(e, p)) {
							System.out.println("Path: " + p.getIndex()
									+ " contains edge: " + e.getIndex());
							System.out.println("Path: " + p);
							System.out.println("Edge: " + e.getIndex());
							System.out.println("Demand: " + d.getId());
							System.out.println("Is in path: " + p.getIndex());

							delta_edp[e.getIndex() - 1][d.getId() - 1][p
									.getIndex() - 1] = 1;
						} else {
							delta_edp[e.getIndex() - 1][d.getId() - 1][p
									.getIndex() - 1] = 0;
							System.out.println("Path: " + p.getIndex()
									+ " NOT contain edge: " + e.getIndex());
						}

					}
				}
			}

			IloCplex cplex = new IloCplex();

			// x_dp
			IloIntVar[][] x_dp = new IloIntVar[d_length][p_length];
			for (Demand d : demandPathsMap.keySet()) {
				List<PathWithEgdes> paths = demandPathsMap.get(d);
				for (PathWithEgdes p : paths) {

					x_dp[d.getId() - 1][p.getIndex() - 1] = cplex.intVar(0,
							Integer.MAX_VALUE,
							"x_" + d.getId() + "-" + p.getIndex());
				}
			}

			// Definicja stalej u_e
			int[] u_e = new int[E.size()];
			for (Edge e : E) {
				if (u_e[e.getIndex() - 1] != 0)
					continue;
				u_e[e.getIndex() - 1] += e.getCurrentLoad();
				System.out.println("CurrentLoad of edge " + e.getIndex()
						+ " = " + u_e[e.getIndex() - 1]);
			}

			// Definicja zmiennej y_e, volumen zapotrzebowania d, h_d >= 0
			IloIntVar[] y_e = cplex.intVarArray(E.size(), 0, Integer.MAX_VALUE);

			// OGRANICZENIA
			IloLinearIntExpr[] VOLUME_D = new IloLinearIntExpr[demandPathsMap
					.keySet().size()];
			IloLinearIntExpr[] FLOWS = new IloLinearIntExpr[E.size()];

			// ograniczenie numer 1
			for (int d = 0; d < demands.length; d++) {
				VOLUME_D[d] = cplex.linearIntExpr();
				for (int p = 0; p < p_length; p++) {
					VOLUME_D[d].addTerm(1, x_dp[d][p]);
				}
				cplex.addEq(VOLUME_D[d], demands[d]);
			}

			// ograniczenie 2
			for (int e = 0; e < E.size(); e++) {
				// wyrazenie suma po d,p (delta * xdp) , dla kazdego E
				FLOWS[e] = cplex.linearIntExpr();

				for (Demand d : demandPathsMap.keySet()) {
					List<PathWithEgdes> paths = demandPathsMap.get(d);
					for (PathWithEgdes p : paths) {
						Integer delta = delta_edp[E.get(e).getIndex() - 1][d
								.getId() - 1][p.getIndex() - 1];
						IloIntVar x = x_dp[d.getId() - 1][p.getIndex() - 1];
						FLOWS[e].addTerm(delta, x);
					}
				}
				// lewa strona, obciazenie poczatkowe + wyrazenie FLOWS
				IloIntExpr lhs = cplex.sum(E.get(e).getCurrentLoad(), FLOWS[e]);

				IloLinearIntExpr rhs = cplex.linearIntExpr();
				rhs.addTerm(Config.MODULARITY, y_e[e]);

				cplex.addLe(lhs, rhs);
			}

			IloLinearIntExpr objective = cplex.linearIntExpr();

			for (int e = 0; e < E.size(); e++) {
				objective.addTerm(1, y_e[e]);
			}

			cplex.addMinimize(objective);

			if (cplex.solve()) {
				System.out.println("Solved");
				System.out.println(cplex.toString());
				double[] values = cplex.getValues(y_e);

				for (double d : values) {
					System.out.println("Y_e: " + d);
				}
				
				for(int i=0; i<demands.length; i++) {
					System.out.println("Demand " + (i+1) + ", value=" + demands[i]);
					double[] xdps = cplex.getValues(x_dp[i]);
					for (int y=0; y<xdps.length; y++) {
						System.out.println("x_dp[" + (i+1) + "][" + (y+1) + "]: " + xdps[y]);
					}
				}
                                System.out.println("\n******* Final results *******");
                                System.out.println("Initial_modules: " + initial_modules);
				System.out.println("Final modules:   " + (int)cplex.getObjValue());
                                System.out.println("Site-Surveys needed: " + 2*(int)(cplex.getObjValue() - initial_modules));
                                System.out.println("*****************************");
			}

		} catch (Exception ex) {
			System.out.println("IloException: " + ex);
		}
	}
	
	public int getInitialModules(List<Edge> edges) {
		int count = 0;
		for(Edge e : edges) {
			double dbl = (double) ((double) e.getCurrentLoad() / (double) Config.MODULARITY);
			count += (int) Math.ceil(dbl);
		}
		return count;
	}

	private boolean checkInPath(Edge e, PathWithEgdes p) {
		if (p.getEdges().contains(e))
			return true;
		return false;
	}
}
