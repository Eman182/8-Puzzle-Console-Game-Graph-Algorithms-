
public class State_cost {
private State parent;
@Override
public String toString() {
	return "State_cost [parent=" + parent + ", costG=" + costG + "]";
}
private int costG;

public State_cost(State parent, int costG) {
	super();
	this.parent = parent;
	this.costG = costG;
}
public State_cost() {
	super();
	// TODO Auto-generated constructor stub
}


public State getParent() {
	return parent;
}
public void setParent(State parent) {
	this.parent = parent;
}

public int getCostG() {
	return costG;
}
public void setCostG(int costG) {
	this.costG = costG;
}
}
