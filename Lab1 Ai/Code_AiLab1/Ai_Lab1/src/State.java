
import java.util.ArrayList;

/*
 * State class
 * Contains everything related to the state
 * @Global_variable value: the value of the state
 * @Global_variable father: the parent of that state, initial state has parent value null
 * @Global_variable neighbours: the neighbours of the state
 */
public class State implements Comparable<State>{
	String value;
	State father;
	int manhatenDist;//h
	double totalCost=0; //f
	double EucledeanDist;
	
	int costTillNow=0;//g
	
	ArrayList<String> neighbours = new ArrayList<String>();
	/**
	 * Constructor
	 */
	public State(String value, State father) {
		this.value = value;
		this.father = father;
		int zeroLocation = findZero();
		if((zeroLocation + 1 <= 8) && ((zeroLocation + 1) % 3 != 0)) {
			neighbours.add(swap(zeroLocation, zeroLocation + 1));
		}if(zeroLocation + 3 <= 8) {
			neighbours.add(swap(zeroLocation, zeroLocation + 3));
		}if((zeroLocation - 1 >= 0) && (zeroLocation % 3 != 0)) {
			neighbours.add(swap(zeroLocation, zeroLocation - 1));
		}if(zeroLocation - 3 >= 0) {
			neighbours.add(swap(zeroLocation, zeroLocation - 3));
		}
	}
	/**
	 * This method find the location of the empty cell resembled by zero in the state's value
	 * Returned values range from 0 to 8
	 * @return the zero location in state's vale
	 */
	private int findZero() {
		for(int i = 0; i < value.length(); i++) {
			if(value.charAt(i) == '0') {
				return i;
			}
		}
		return 0;
	}
	/**
	 * Swap two characters of the state value
	 * @param x: one of the indexes to be swapped
	 * @param y: the second index to be swapped
	 */
	private String swap(int x, int y){
		char[] arr = value.toCharArray();
		char temp = arr[x];
		arr[x] = arr[y];
		arr[y] = temp;
		return String.valueOf(arr);
	}
	/**
	 * Getter
	 * @return the value of the state
	 */
	public String getValue() {
		return this.value;
	}
	/**
	 * Getter
	 * @return the father of the state
	 */
	public State getFather() {
		return this.father;
	}
	/*
	 * This method print the state in an analyzable format
	 */
	public void printState() {
		for(int j = 0; j <= 8; j++) {
			if(j == 0 || j == 3 || j == 6) {
				System.out.print("| ");
			}
			System.out.print(value.charAt(j) + " | ");
			if(j == 2 || j == 5) {
				System.out.println();
			}
		}
		System.out.println("\n\n------------------\n");
	}
	public int getManhatenDist() {
		return manhatenDist;
	}
	public void setManhatenDist(int manhatenDist) {
		this.manhatenDist = manhatenDist;
	}
	
	
	public double getEucledeanDist() {
		return EucledeanDist;
	}
	public void setEucledeanDist(double eucledeanDist) {
		EucledeanDist = eucledeanDist;
	}
	public int getCostTillNow() {
		return costTillNow;
	}
	public void setCostTillNow(int costTillNow) {
		this.costTillNow = costTillNow;
	}
	
	public void setFather(State father) {
		this.father = father;
	}
	public double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	@Override
	public String toString() {
		return "State [value=" + value + ", father=" + father + "]";
	}
	
	@Override
	public int compareTo(State o) {
		return this.getTotalCost() > o.getTotalCost() ? 1 : -1;
	
	}
	public ArrayList<String> getNeighbours() {
		return neighbours;
	}
	public void setNeighbours(ArrayList<String> neighbours) {
		this.neighbours = neighbours;
	}
}
