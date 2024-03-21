import java.util.ArrayList;
import java.util.Scanner;

public class BlackJack {

	int numPlayers; 
	int numDecks; 
	Deck shoe; 
	int playerResponse; 
	ArrayList<Player> Players = new ArrayList<Player>(); 
	//put list of random names for players
	
	Scanner scan = new Scanner(System.in); 
	String Buffer; 
	Boolean valid = false; 
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		BlackJack test1 = new BlackJack(); 
		test1.Players.add(new Player("Dealer"));
		test1.Players.add(new Player("User"));
		
		test1.playGame();
		
		System.out.println("welcome to blackjack! \n"
				+ "Please press 1 to play 2 to quit. \n");
		
		
			
	}//end of main
	
	public void playGame() {
		System.out.println("How many decks do you want to play with? \n");
		Buffer = scan.next(); 
		while(!valid) {
			if(isNumeric(Buffer)) {
				valid = true; 
			}else {
				System.out.println("Please input only numbers: \n");
				Buffer = scan.next(); 
			}
		}//end of number validation check
		numDecks = Integer.parseInt(Buffer);
		valid = false; 
		
		shoe = new Deck (numDecks);
		
		System.out.println("How many people are playing?"); 
		Buffer = scan.next(); 
		while(!valid) {
			if(isNumeric(Buffer)) {
				valid = true; 
			}else {
				System.out.println("Please input only numbers: \n");
				Buffer = scan.next(); 
			}
		}//end of number validation check
		valid = false; 
		
		numPlayers = Integer.parseInt(Buffer);
		this.shoe.shuffle();
		System.out.println(this.shoe); 
		this.playHand();
		//add support for adding multiple players
		//for(int i=0; i< numPlayers; i++) {
			//Players.add(Player )
		//}
		
	}//end of play Game
	
	public void playHand() {
		//deals two cards from the top of the deck to a player
		Boolean con_flag = true; 
		
		for(Player P : this.Players) {
			this.hit(P);
			this.hit(P);
			if(P.name != "Dealer") {
				System.out.println("Player: " + P.name + " " + P.Hand + " Total: [" + P.handValue + "]"); 
			}
		}//end of starting deal
		
		//dealer will always be the first person in the list, so we can print their hand seperately
		System.out.println("[Hidden, " + this.Players.get(0).Hand.get(1) + "] Total: [" + this.Players.get(0).handValue + "]"); 
		
		while(con_flag) {
			System.out.println("Enter 1 to hit 2 to stay: \n");
			while(!valid) {
				if(isNumeric(Buffer)) {
					valid = true; 
				}else {
					System.out.println("Please input only numbers: \n");
					Buffer = scan.next(); 
				}
			}//end of number validation check
			playerResponse = Integer.parseInt(Buffer); 
			
			//finish adding player response to hit me
			//if(playerResponse == 1) {
				
			//}else
		}
		
		
		
		
		
	}//end of play hand
	
	//draws top card from shoe, adds value to player's hand then adds card to player's hand
	public void hit(Player person) {
			card nextCard = this.shoe.contents.remove(0); 
			person.handValue += nextCard.myValue;
			//handles converting ace to 1 if total is over 21
			if(person.handValue > 21 && hasAce(person) != -1 ) {
				person.Hand.get(hasAce(person)).myValue =1;
				//recalculate hand value
				person.handValue = 0; 
				for(card C : person.Hand) {
					person.handValue += C.myValue; 
				}
			}
			person.Hand.add(nextCard); 
	}
	
	public void dealerLogic(Player dealer) {
		while(dealer.handValue < 17 ) {
			this.hit(dealer);
		}	
	}//end of dealer logic
	
	//returns index of ace if the player hand contains an Ace
	public int hasAce(Player p) {
		for(card C : p.Hand) {
			if(C.cardType.equals("Ace"))
				return p.Hand.indexOf(C);
		}
		return -1; 
	}
	
	//determines if input string is a number
	public Boolean isNumeric(String testString) {
		if (testString.matches("-?\\d+")) { //reg rex expression asks for only numeric digits
			return true;
		}else
			return false;
		
	}//end of isNumeric

}//end of BlackJack
