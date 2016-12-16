package pl.edu.pojo;

public class Path {
	
	static Integer counter = 1; //licznik sciezek
	
	int n;	//numer sciezki
	int d;  //numer zapotrzebowania realizowanego na sciezce
	int[] V; //wezly nalezace do sciezki
	
	public Path(int d, int[] V)
	{
		this.n = counter;
		this.d = d;
		this.V = V.clone(); 
		counter++;
	}
	
	public int getN()
	{
		return this.n;
	}
	public int getD()
	{
		return this.d;
	}
	public int[] getAllV()
	{
		return this.V;
	}
	public int getFirstV()
	{
		return this.V[0];
	}
	public int getLastV()
	{
		return this.V[V.length - 1];
	}
	public int getSpecificV(int index)
	{
		return this.V[index];
	}
	

}
