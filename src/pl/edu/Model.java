package pl.edu;

import ilog.concert.IloException;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;

public class Model {
	
    public Model()
    {

    }

    private void createModel(){
        
        int demands = 2; //get from parser
        int paths = 2; //get from parser - each demand should has got its own paths collection
        
        try {
            IloCplex cplex = new IloCplex();
            
            //x_dp not finished
            IloNumVar[][] x = new IloNumVar[paths][]; //or [demands][]?
            for (int i = 0; i < paths; i++) {
                x[i] = cplex.numVarArray(demands, 0, Double.MAX_VALUE);
            }
            
            
            
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
