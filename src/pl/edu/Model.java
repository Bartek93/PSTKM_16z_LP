package pl.edu;

import java.util.List;
import java.util.Map;

import edu.asu.emit.algorithm.graph.Path;
import pl.edu.pojo.CplexInput;
import pl.edu.pojo.Demand;
import pl.edu.pojo.PathWithEgdes;
import ilog.concert.*;
import ilog.cplex.*;

public class Model {
	
	private List<Demand> demands;
	private List<Path> paths;
	
	
    public Model()
    {

    }

    public void createModel(CplexInput cplexInput, int numberOfPaths){
        
        try {
        	Map<Demand, List<PathWithEgdes>> demandPathsMap = cplexInput.getDemandPathsMap();
        	
            IloCplex cplex = new IloCplex();
            int d_length = demandPathsMap.keySet().size();
            int p_length = 0;
            for(Demand d : demandPathsMap.keySet()) {
            	p_length = demandPathsMap.get(d).size();
            	break;
            }
            
            //x_dp
            IloNumVar[][] x_dp = new IloNumVar[d_length][p_length]; 
            for(Demand d : demandPathsMap.keySet()) {
            	List<PathWithEgdes> paths = demandPathsMap.get(d);
            	for(PathWithEgdes p : paths) {
            		int pathId = paths.indexOf(p) + 1;
            		x_dp[d.getId()-1][pathId] = cplex.intVar(0, Integer.MAX_VALUE, "x_" + d.getId() + "-" + pathId);
            	}
            }
            
            /// Definicja zmiennej h_d, volumen zapotrzebowania d, h_d >= 0
            IloIntVar y_e = cplex.intVar(0, Integer.MAX_VALUE, "y_e");
            
            // definicja 
            
            
            /* TUTORIAL: https://www.youtube.com/watch?v=HMLLDp476ts&index=3&list=PL9xwgp-nwd-wwPhYaN3vUyduglg2m-xHO
            //cplex syntax exaple (it does nothing in project :D its only example):
            //min(0.5*x + y)
            //define vars: 
            IloLineraNumExpr objective = cplex.lineraNumExpr();
            objective.addTerm(0.5, x);
            objective.addTerm(1, y);
            
            //define objective
            cplex.addMinimize(objective);
            
            //define constraints
            cplex.addGe(cplex.sum(cplex.prod(60, x)),cplex.prod(60, y)), 300); // 60x+60y >= 300
            cplex.addLe(cplex.sum(cplex.prod(60, x)),cplex.prod(60, y)), 300); //60x+60y <= 300
            cplex.addEq(cplex.sum(cplex.prod(60, x)),cplex.prod(60, y)), 300); //60x+60y = 300
            */
           

            
            
            

        } 
        catch (IloException ex) {
                System.out.println("IloException: " + ex);
        }
    }

}
