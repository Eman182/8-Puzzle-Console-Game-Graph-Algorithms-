import java.awt.Point;
import java.util.*;

public class SolvePuzzle {
	/**
	 * Depth first search based 8 puzzle solver
	 * @param initial: the initial state of the puzzle
	 */
	public static void DFS(State initial) {
		Stack<State> frontierStack = new Stack<State>();
		HashSet<String> frontier = new HashSet<String>();
		HashSet<String> explored = new HashSet<String>();
		frontierStack.add(initial);
		frontier.add(initial.getValue());
		while(!frontierStack.isEmpty()) {
			State s = frontierStack.pop();
			
			frontier.remove(s.getValue());
			
			explored.add(s.getValue());
			if(s.getValue().equals("012345678")) {
				System.out.println("\nPuzzle solved successfully!\n");
				
				System.out.println("Steps:\n");
				
				Stack<State> routeStates = new Stack<State>();
				routeStates.push(s);
				while(s.getFather() != null) {
					routeStates.push(s.getFather());
					s = s.getFather();
				}
				int routeSize = routeStates.size();
				for(int i = 0; i < routeSize; i++) {
					routeStates.pop().printState();
				}
				System.out.println("Route length: " + routeSize);
				System.out.println("Explored nodes number:  " + explored.size());
				
				return;
			}
			ArrayList<String> neighbours = s.neighbours;
			for(int i = 0; i < neighbours.size(); i++) {
				String neighbour = neighbours.get(i);
				if(!frontier.contains(neighbour) && !explored.contains(neighbour)) {
					State newS = new State(neighbour, s);
					frontierStack.push(newS);
					frontier.add(neighbour);
				}
			}
		}
		System.out.println("Failed! This puzzle is unsolvable!");
	}
	/**
	 * Breadth first search based 8 puzzle solver
	 * @param initial: the initial state of the puzzle
	 */
	public static void BFS(State initial) {
		Queue<State> frontierQueue = new LinkedList<State>();
		HashSet<String> frontier = new HashSet<String>();
		HashSet<String> explored = new HashSet<String>();
		frontierQueue.add(initial);
		frontier.add(initial.getValue());
		while(!frontierQueue.isEmpty()) {
			State s = frontierQueue.remove();
			
			frontier.remove(s.getValue());
			
			explored.add(s.getValue());
			if(s.getValue().equals("012345678")) {
				System.out.println("\nPuzzle solved successfully!\n");
				
				System.out.println("Steps:\n");
				
				Stack<State> routeStates = new Stack<State>();
				routeStates.push(s);
				while(s.getFather() != null) {
					routeStates.push(s.getFather());
					s = s.getFather();
				}
				int routeSize = routeStates.size();
				for(int i = 0; i < routeSize; i++) {
					routeStates.pop().printState();
				}
				System.out.println("Route length: " + routeSize);
				System.out.println("Explored nodes number:  " + explored.size());
				return;
			}
			ArrayList<String> neighbours = s.neighbours;
			for(int i = 0; i < neighbours.size(); i++) {
				String neighbour = neighbours.get(i);
				if(!frontier.contains(neighbour) && !explored.contains(neighbour)) {
					State newS = new State(neighbour, s);
					frontierQueue.add(newS);
					frontier.add(neighbour);
				}
			}
		}
		System.out.println("Failed! This puzzle is unsolvable!");
	}
	
	public static int[][] grid(String s) {
        int[][] arr = new int[3][3];
        int x = 0;

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 3; ++j) {
                arr[i][j] = Integer.parseInt(String.valueOf(s.charAt(x)));
            }

            x += 2;
        }

        return arr;
    }
	
	
	private static int getManhatenDistances(String initial) {
		int i=0;String s=initial;int sum=0;
		int x=0;int y=0;
		HashMap<Integer,Point>h=new HashMap<>();
		int arr[][]=new int[3][3];int start=0;
		while(x<arr.length) {
			while(y<arr[0].length) {
				h.put(start, new Point(x,y));
				start++;
				arr[x][y]=Integer.parseInt(""+s.charAt(i));
				y++;i++;
			}
			y=0;
			x++;
		}
				//so get distance between it and its place
				//1 5 4
				//3 2 0
				//6 7 8
				//get x , y for the current state number and x,y for the goal
		x=0;
		y=0;
		//h contains 
		//0    0 0
		//1    0 1
		//2    0 2
		//3    1 0
		//.................. so on
		int manhatenDistTotal=0;
		while(x<3) {
			while(y<3) {
				manhatenDistTotal=(int) (manhatenDistTotal+Math.abs(x-h.get(arr[x][y]).getX())
				+ Math.abs(y-h.get(arr[x][y]).getY()));
				y++;
			}
			y=0;x++;
		}
		return manhatenDistTotal;
	}
	
	private static double getEucledeanDistances(String initial) {
		int i=0;String s=initial;int sum=0;
		int x=0;int y=0;
		HashMap<Integer,Point>h=new HashMap<>();
		int arr[][]=new int[3][3];int start=0;
		while(x<arr.length) {
			while(y<arr[0].length) {
				h.put(start, new Point(x,y));
				start++;
				arr[x][y]=Integer.parseInt(""+s.charAt(i));
				y++;i++;
			}
			y=0;
			x++;
		}
				//so get distance between it and its place
				//1 5 4
				//3 2 0
				//6 7 8
				//get x , y for the current state number and x,y for the goal
		x=0;
		y=0;
		double eucledeanDistTotal=0;
		while(x<3) {
			while(y<3) {
				eucledeanDistTotal= (eucledeanDistTotal +
						Math.sqrt(Math.pow(x-h.get(arr[x][y]).getX(),2)
				+ Math.pow(y-h.get(arr[x][y]).getY(),2)));
				y++;
			}
			y=0;x++;
		}
		return eucledeanDistTotal;
	}
	public static void AStar_UseManhatenDist(State initial){
		//estimate h is Manhattan distance we can get from Manhattan fn
		//g is calculated every step
		//f=g+h
		//cost in frontier will be total cost not only cost till now + Manhattan 
		//we can get cost till now from parent table
		
		HashMap<String,State_cost>parentTable=new HashMap<>();//to search in it faster for the parent we used map
		//as we need to change parent if new cost is smaller so search needs to be fast 
		//also to get path we need to know parent fast to print the path in very small time
		PriorityQueue<State>frontier=new PriorityQueue<>();
		HashSet<String>explored=new HashSet<>();
		HashSet<String>frontierSet=new HashSet<>();//we needed it to check is the node is in frontier or not
		//in fast time 
		HashMap <String,Integer>manhatenDist=new HashMap<>();//so that if we got a Manhattan distance of a node 
		//we can save it here to be used latter if we revisited this node or in case we found a way shorter to this node
		//we need to check costTill now + Manhattan
		
		initial.setCostTillNow(0);
		int m=getManhatenDistances(initial.getValue());
		initial.setManhatenDist(m);
		manhatenDist.put(initial.getValue(),m);
		parentTable.put(initial.getValue(), new State_cost(null,0));
		frontier.add(initial);frontierSet.add(initial.getValue());
		while(frontier.isEmpty()==false) {
			State s=new State(frontier.peek().getValue(),frontier.peek().getFather());
			s.setCostTillNow(frontier.peek().getCostTillNow());
			s.setManhatenDist(frontier.peek().getManhatenDist());
			s.setTotalCost(frontier.peek().getTotalCost());
			frontier.poll();
			frontierSet.remove(s.getValue());explored.add(s.getValue());
			if(s.getValue().contentEquals("012345678")) {
				System.out.println("Goal is reached successfully");
				//get path and print it
				LinkedList<State>path=new LinkedList<>();
				State st=s;
				while(st!=null) {
					path.add(st);
					st=st.getFather();
				}
				int i=path.size()-1;
				while(i>=0) {
					path.get(i).printState();
					i--;
				}
				System.out.println("Route length: " + path.size()+" nodes");
				System.out.println("Explored nodes number:  " + explored.size());
				//print explored states as required in report
				//System.out.println(explored.toString());
				System.out.println("Cost of Path : "+s.getCostTillNow());
				System.out.println("Max Depth reached during search : "+getMaxDepth(parentTable,explored));
				return ;
			}else {
				ArrayList<String> a=s.getNeighbours();
				int i=0;
				while(i<a.size()) {
					if( ! (frontierSet.contains(a.get(i)) || (explored.contains(a.get(i)) ) ) ) {
						State newSt=new State(a.get(i), s);
						newSt.setCostTillNow(s.getCostTillNow()+1);
						newSt.setManhatenDist(getManhatenDistances(a.get(i)));
						int x=newSt.getManhatenDist();
						//System.out.println("State : "+a.get(i)+ "  "+x);
						newSt.setTotalCost(x+newSt.getCostTillNow());
						manhatenDist.put(a.get(i),x);
						State_cost st=new State_cost();
						st.setCostG(newSt.getCostTillNow());
						st.setParent(s);
						parentTable.put(a.get(i),st);
						frontier.add(newSt);
					}else if(frontierSet.contains(a.get(i))) {
						//decrease key of neighbor what does this means 
						//this means look at it in the parent table and get its cost till now 
						// if g was < its current cost from node so decrease  key (change total cost in priority queue)
						//and change parent in parent table 
						decreaseKey(frontier,a.get(i),manhatenDist.get(a.get(i)),s,parentTable);
					}
					i++;
				}
			}
		}
		System.out.println("Failed! This puzzle is unsolvable!"); //if he didn't return in the loop
		//so he didn't reach a solution
	}
	
	
	public static void AStar_UseEucledianDist(State initial){
		//estimate h is Eucledian distance we can get from Eucledian fn
		//g is calculated every step
		//f=g+h
		//cost in frontier will be total cost not only cost till now + Euclidean distance
		//we can get cost till now from parent table
		
		HashMap<String,State_cost>parentTable=new HashMap<>();
		//to search in it faster for the parent we used map
		//as we need to change parent if new cost is smaller so search needs to be fast 
		//also to get path we need to know parent fast to print the path in very small time
		PriorityQueue<State>frontier=new PriorityQueue<>();
		//we needed it to check is the node is in frontier or not
		//in fast time 
		HashSet<String>explored = new HashSet<>();
		HashSet<String>frontierSet=new HashSet<>();
		HashMap <String,Double>eucledianDist=new HashMap<>();
		//so that if we got a Manhattan distance of a node 
		//we can save it here to be used latter if we revisited this node or in case we found a way shorter to this node
		//we need to check costTill now + Manhattan
		
		initial.setCostTillNow(0);
		double m=getEucledeanDistances(initial.getValue());
		initial.setEucledeanDist(m);
		parentTable.put(initial.getValue(),new State_cost(null,0));
		eucledianDist.put(initial.getValue(),m);
		frontier.add(initial);
		frontierSet.add(initial.getValue());
		while(frontier.isEmpty()==false) {
			State s=new State(frontier.peek().getValue(),frontier.peek().getFather());
			s.setCostTillNow(frontier.peek().getCostTillNow());
			s.setEucledeanDist(frontier.peek().getEucledeanDist());
			s.setTotalCost(frontier.peek().getTotalCost());
			frontier.poll();
			frontierSet.remove(s.getValue());explored.add(s.getValue());
			

			if(s.getValue().contentEquals("012345678")) {
				System.out.println("Goal is reached successfully");
				//get path and print it
				LinkedList<State>path=new LinkedList<>();
				State st=s;
				while(st!=null) {
					path.add(st);
					st=st.getFather();
				}
				int i=path.size()-1;
				while(i>=0) {
					path.get(i).printState();
					i--;
				}
				System.out.println("Route length: " + path.size()+" nodes");
				System.out.println("Explored nodes number:  " + explored.size());
				System.out.println("Cost of Path : "+s.getCostTillNow());
				//System.out.println(explored.toString());
				System.out.println("Max Depth reached during search : "+getMaxDepth(parentTable,explored));
				return ;
			}else {
				ArrayList<String> a=s.getNeighbours();
				int i=0;
				while(i<a.size()) {
					if( ! (frontierSet.contains(a.get(i)) || (explored.contains(a.get(i)) ) ) ) {
						State newSt=new State(a.get(i), s);
						newSt.setCostTillNow(s.getCostTillNow()+1);
						newSt.setEucledeanDist(getEucledeanDistances(a.get(i)));
						double x=newSt.getEucledeanDist();
						newSt.setTotalCost(x+newSt.getCostTillNow());
						eucledianDist.put(a.get(i),x);
						State_cost st=new State_cost();
						st.setCostG(newSt.getCostTillNow());
						st.setParent(s);
						parentTable.put(a.get(i),st);
						frontier.add(newSt);
					}else if(frontierSet.contains(a.get(i))) {
						//decrease key of neighbor what does this means 
						//this means look at it in the parent table and get its cost till now 
						// if g was < its current cost from node f so decrease  key (change its total cost in priority queue)
						//and change parent in parent table 
						decreaseKey(frontier,a.get(i),eucledianDist.get(a.get(i)),s,parentTable);
					}
					i++;
				}
			}
		}
		System.out.println("Failed! This puzzle is unsolvable!");
		//if he didn't return in the loop
		//so he didn't reach a solution
	}
	
	
	private static long getMaxDepth(HashMap<String, State_cost> parentTable, HashSet<String> explored) {
		long depMax=0;int depth;
		int i=0;Iterator iter= explored.iterator();
		while(iter.hasNext()) {
			depth=1;String s=(String) iter.next();
			if(parentTable.get(s).getParent()!=null) {
				State sta=parentTable.get(s).getParent();
				while(sta.getFather()!=null) {
					sta=sta.getFather();depth++;
				}
				if(depMax<depth) {
					depMax=depth;
				}
			}
			i++;
		}
		return depMax;
	}
	private static void decreaseKey(PriorityQueue<State> frontQueue,String str,double manhaten_eucledian, State s, HashMap<String, State_cost> parentTable) {
		// TODO Auto-generated method stub
		
		//check its cost till now in frontier if its cost is more than cost we can get from state s as its parent
		//so change cost to be cost of state s + 1 and parent in parent table to be s
		double PrevCost=parentTable.get(str).getCostG()+manhaten_eucledian;
		double cost=PrevCost;
		double newCost=s.getCostTillNow()+1+manhaten_eucledian;
		if(newCost<PrevCost) {
			cost=newCost;
			State_cost st=new State_cost();
			st.setParent(s);
			st.setCostG(s.getCostTillNow()+1);
			parentTable.replace(str,st);
		}
		PriorityQueue<State>p=new PriorityQueue<>();
		while(frontQueue.isEmpty()==false) {
			if(!frontQueue.peek().getValue().equals(str)) {
				p.add(frontQueue.poll());
			}
		}
		State newS=frontQueue.poll();
		newS.setTotalCost(cost);newS.setFather(s);
		newS.setCostTillNow((int) (cost-manhaten_eucledian));
		frontQueue.add(newS);
		while(p.isEmpty()==false) {
			frontQueue.add(p.poll());
		}
		
	}
	
	/**
	 * main method 
	 * @param args
	 */
	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		
		String puzzle = "";
		String choise = "";
		while(true) {
			System.out.println("Choose a method to use: ");
			System.out.println("1) DFS");
			System.out.println("2) BFS");
			System.out.println("3) A*");
			System.out.print("Your choise: ");
			choise = in.nextLine();
			if(!choise.equals("1") && !choise.equals("2") && !choise.equals("3")) {
				System.out.println("\nInvalid choise, try again");
			}else {
				while(true) {
					System.out.println("Please enter the puzzle initial state, example: 125340678 where the zero is the empty cell.");
					System.out.print("Your puzzle: ");
					puzzle = in.nextLine();
					if(puzzle.length() != 9) {
						System.out.println("Format is not correct try again.\n");
						continue;
					}int flag = 0;
					for(int i = 0; i < 9; i++) {
						if(!Character.isDigit(puzzle.charAt(i)) || puzzle.charAt(i) == '9') {
							System.out.println("Format is not correct try again.\n");
							flag = 1;
							break;
						}
					}if(flag == 1) {
						continue;
					}
					break;
				}
				break;
			}
		}
		//Now you have a valid puzzle to search for a solution for
		
		if(choise.equals("1")) {
			DFS(new State(puzzle, null));
		}else if(choise.equals("2")) {
			BFS(new State(puzzle, null));
		}else {
			// To be replaced when A* is implemented
			System.out.println("For : ");
			System.out.println("A* with manhaten distance ----> 1");
			System.out.println("A* with Eucledian distance ---> 2");
			in=new Scanner(System.in);
			int x=in.nextInt();
			if(x==1) {
			AStar_UseManhatenDist (new State(puzzle, null));
			}else if (x==2){
				AStar_UseEucledianDist (new State(puzzle, null));
			}else {
				System.out.println("Wrong choice");
			}
			
		}
		
		in.close();
		
//      Some initial states to be solved
// 		first one is from the lab PDF
//		second one is a very hard puzzle, 26 steps in best case
//      third one is an unsolvable puzzle
		
		
//		BFS(new State("125340678", null));
//		DFS(new State("125340678", null));
//		BFS(new State("647850321", null));
//		DFS(new State("647850321", null));
//		BFS(new State("702853641", null));
//		DFS(new State("702853641", null));

		
		
	}
}
