import java.util.ArrayList;
import java.util.Scanner;

public class BlackJack {

	private int numPlayers; 
	private int numDecks; 
	private Deck shoe; 
	private int playerResponse; 
	private int playerCount; 
	ArrayList<Player> Players = new ArrayList<Player>(); 
	//put list of random names for players
	
	Scanner scan = new Scanner(System.in); 
	String Buffer; 
	Boolean valid = false; 
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("welcome to blackjack! \n");
		
		BlackJack test1 = new BlackJack(); 
	//	test1.Players.add(new Player("House"));
	//	test1.Players.add(new Player("User"));
		
		test1.playGame();
			
	}//end of main
	
	public void playGame() {
		Boolean continueGame; 
		
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
		
		//pre add the house as player zero
		Players.add(new Player("House")); 
		//add rest of the players 
		for(int i=0; i< numPlayers; i++) {
			Players.add(new Player("Player " + (i + 1))); 
		}
		
		System.out.println(Players); 
		
		numPlayers = Integer.parseInt(Buffer);
		this.shoe.shuffle();
		do{
			this.playHand();
			System.out.println("Please enter 1 to play another hand, 2 to quit");
			
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
			if(Integer.parseInt(Buffer) == 1) 
				continueGame = true; 
			else continueGame = false; 
		}while(continueGame); 
		
	}//end of play Game
	
	public void playHand() {
		
		//dealer is always player zero so we start the hit count at one
		playerCount = 1; 
		this.resetPlayers(); //clears the hands from previous game 
		//deals two cards from the top of the deck to a player
		for(Player P : this.Players) {
			
		/*	if(P.equals(this.Players.get(1))) {
				P.Hand.add(new card(1, "Spade")); 
				P.Hand.add(new card(10, "Spade"));
				continue; 
			} */
			
			this.hit(P);
			this.hit(P);
			if(P.name != "House") {
				System.out.println(P); 
			}
			/* can't remove P while using P to travese come up with a better solution to removing players that have a black jack
			if(this.isBlackJack(P)) {
				System.out.println("BLACKJACK!");
				//remove the player from the current hand, they have already won
				this.Players.remove(P); 
		
			} */ 
		}//end of starting deal
		
		//dealer will always be the first person in the list, so we can print their hand seperately
		System.out.println("[Hidden, " + this.Players.get(0).Hand.get(1) + "] Total: [" + this.Players.get(0).handValue + "]"); 
			
		while(playerCount != this.Players.size()) {
			//grab which ever player's current turn it is
			Player currentPlayer = this.Players.get(playerCount);
			
			//gets next action
			System.out.println(currentPlayer.name + " Enter 1 to hit 2 to stay: \n");
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
			playerResponse = Integer.parseInt(Buffer);
			
			if(playerResponse == 1) {
				this.hit(currentPlayer);
				System.out.println(currentPlayer); 
				if(currentPlayer.handValue > 21) {
					System.out.println("BUST");
					playerCount++; //move to next player
					continue; 
				}//end of bust branch
			}else
				playerCount++; 
			
		}//end of while PlayerCount
		
		//all players have, gone dealers turn now
		houseLogic(this.Players.get(0));
		 
		//Prints results messages
		int lostCount =0; 
		for(Player P : this.Players) {
			if(P.name == "House") continue; //skip the dealer
			
			if(P.handValue > this.Players.get(0).handValue && P.handValue <= 21) {
				System.out.println(P.name + " Wins!\n"); 
			}else if(P.handValue <= 21 && P.handValue == this.Players.get(0).handValue)
				System.out.println(P.name + " Draws!"); 
			else {
				System.out.println(P.name + " Loses!"); 
				lostCount++;
			}
		}//end of determine winner loop
		
		if(lostCount == this.Players.size() -1)
			System.out.println("House Wins!");
		
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
	
	public void houseLogic(Player house) {
		if(house.handValue >= 17) System.out.println(house); //prints hand if we never hit
		while(house.handValue < 17 ) {
			this.hit(house);
			System.out.println(house); 
		}
		if(house.handValue > 21) house.handValue = 0; 
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
	
	//takes in a player and sees if their hand is a black jack
	//only needs to check the first two cards as a black jack is only possible given the first two cards of the game
	public Boolean isBlackJack(Player P) {
		if( P.Hand.get(0).cardType == "Ace" || P.Hand.get(1).cardType == "Ace" ) {
			if(P.Hand.get(0).myValue == 10 || P.Hand.get(1).myValue == 10)
				return true;
		}
		return false; 
	}//end of is black jack
	
	public void resetPlayers() {
		for(Player P : this.Players) {
			P.resetHand(); 
		}
	}//end of reset players

}//end of BlackJack
