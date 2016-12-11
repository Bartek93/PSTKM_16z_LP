package pl.edu;

import ilog.concert.IloException;
import ilog.cplex.IloCplex;

public class Model {
	
	public Model()
	{
		
	}
	
	private void createModel(){
		try {
			IloCplex cplex = new IloCplex();
			
		} catch (IloException ex) {
			System.out.println("IloException: " + ex);
		}
	}

}
