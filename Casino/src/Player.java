import java.util.ArrayList;

public class Player {

	protected int score; 
	protected int chips;  
	protected String name; 
	protected ArrayList<Hand> Hands; 
	
	public Player (String nameIn) {
		this.Hands = new ArrayList<Hand>(); 
		this.Hands.add(new Hand());
		this.score =0; 
		this.chips =0; 
		this.name = nameIn;
	}//end of constructor
	
	public String toString() {
		int handNumber= 0; 
		System.out.println("Player: " + this.name + ":"); 
		//prints out a single hand
		if(Hands.size() == 1) {
			System.out.println("\t " + this.Hands.get(0)); 
		}else { //prints out multiple hands
			for(Hand H : this.Hands) {
				System.out.println("Hand: " + handNumber + "\t" + H );
				handNumber++;
				System.out.println("pop"); 
			}//end of for loop 
		}//end of else
		return ""; 
	}//end of to string
	
	public void reset() {
		this.Hands.removeAll(Hands); 
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

	}

	
	
}
