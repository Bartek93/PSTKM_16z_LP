package pl.edu.pojo;

public class Demand {
	
	private int srcNode;
	private int dstNode;
	private int value;
	
	public Demand(int srcNode, int dstNode, int value) {
		super();
		this.srcNode = srcNode;
		this.dstNode = dstNode;
		this.value = value;
	}

	public int getSrcNode() {
		return srcNode;
	}

	public void setSrcNode(int srcNode) {
		this.srcNode = srcNode;
	}

	public int getDstNode() {
		return dstNode;
	}

	public void setDstNode(int dstNode) {
		this.dstNode = dstNode;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Demand [srcNode=" + srcNode + ", dstNode=" + dstNode
				+ ", value=" + value + "]";
	}
	
	
	

}
