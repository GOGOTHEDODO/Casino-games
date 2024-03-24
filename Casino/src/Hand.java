import java.util.ArrayList;

public class Hand {
	int myValue;
	ArrayList<card> myCards; 
	Boolean blackJack; 
	
	public Hand() {
		myValue = 0; 
		myCards = new ArrayList<card>(); 
		blackJack = false; 
	}// end of defult constructor

	public void updateHandValue() {
		myValue = 0;
		for(card C : myCards) {
			myValue += C.myValue; 
		}
	}//end of update hand value
	
	public void resetHand() {
		myValue=0; 
		myCards.removeAll(myCards);
		blackJack = false; 
	}//end of resest hand
	
	public String toString() {
		
		return myCards + " Value: [" + myValue + "]"; 
	}//end of toString
	
}

