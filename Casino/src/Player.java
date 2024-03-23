import java.util.ArrayList;

public class Player {

	protected ArrayList<card> Hand;
	protected int score; 
	protected int chips; 
	protected int handValue; 
	protected String name; 
	protected Boolean blackjack; 
	
	public Player (String nameIn) {
		this.Hand = new ArrayList<card>();
		this.score =0; 
		this.chips =0; 
		this.handValue = 0;
		this.name = nameIn;
		this.blackjack = false; 
	}//end of constructor
	
	public String toString() {
		return "Player: " + this.name + " " + this.Hand + " Total: [" + this.handValue + "]\n"; 
	}//end of to string
	
	public void updateHandValue() { 
		for(card C : this.Hand) {
			handValue += C.myValue; 
		}
	}//end of update Hand Value
	
	public void resetHand() {
		this.Hand.removeAll(this.Hand);
		handValue = 0;
		blackjack = false; 
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

	}

	
	
}
