
public class card {
	
	protected int myValue; 
	protected String mySuit; 
	protected String cardType; 
	
	public card(int value, String suit){ 
		this.myValue = value; 
		this.mySuit = suit;
		
		//sets cardtype for
		switch(value) {
		case(1):
			this.cardType = "Ace";
			this.myValue = 11; //defult aces to 11 if they go over then blackJack changes value to a one
		break; 
		case(2):
		case(3):
		case(4):
		case(5):
		case(6):
		case(7):
		case(8):
		case(9):
		case(10):
			this.cardType = String.valueOf(value);
		break;
		case(11):
			this.cardType = "Jack";
			this.myValue = 10; 
		break;
		case(12):
			this.cardType = "Queen";
			this.myValue = 10; 
		break;
		case(13): 
			this.cardType = "King"; 
			this.myValue = 10; 
		break; 
		
		}//end of switch case

		
	}//end of constructor
	
	public String toString() {
		return this.cardType + " of " + this.mySuit; 
	}//end of to string method
	
	
	//for debugging
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		card test1 = new card(1, "hearts"); 
		System.out.println(test1); 
		

	}

}
