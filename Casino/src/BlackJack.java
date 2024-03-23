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
			Hand newHand = new Hand();  
			this.hit(newHand);
			this.hit(newHand);
			/*
			if(newHand.myCards.get(0) == newHand.myCards.get(1)) {
				P.splitHands.add(splitLogic(P.Hand, P)); 
			} */ 
			
			if(P.name != "House") {
				System.out.println(P.name + " " +  newHand); 
			}
			
			if(newHand.myValue == 21) {
				System.out.println("BLACKJACK! \n");
				newHand.blackJack = true; 
			}
			P.Hands.add(newHand); 
		}//end of starting deal
		
//////////////////////////////////////////////////////////END OF STARTING DEAL////////////////////////////////////////////////////
		
		//dealer will always be the first person in the list, so we can print their hand seperately
		System.out.println("[Hidden, " + this.Players.get(0).Hands.get(0).myCards.get(1) + "]"); 
			
		while(playerCount != this.Players.size()) {
			//grab which ever player's current turn it is
			Player currentPlayer = this.Players.get(playerCount);
			
			//if the player has a blackjack skip their turn:: only possible for the first hand no splits
			if(currentPlayer.Hands.get(0).blackJack == true) {
				playerCount++; 
				continue; 
			}//end of black jack if
			
			//gets next action
			System.out.println(currentPlayer.name + ": \n "); 
			for(Hand H : currentPlayer.Hands) {
				do {
					if(currentPlayer.Hands.size() == 1)
						System.out.println("enter 1 to hit 2 to stay: \n"); 
					else
						System.out.println("Hand " +  currentPlayer.Hands.indexOf(H) + " Enter 1 to hit 2 to stay: \n");
					
					
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
						this.hit(H);
						System.out.println(H); 
						if(H.myValue > 21) {
							System.out.println("BUST");
							playerResponse = 2; //handles the case where we continue out of this player
							continue; 
						}//end of bust branch
					}//end of hit
				}while(playerResponse == 1); 
			}//end of H loop
			playerCount++; 
		}//end of while PlayerCount
		
		//all players have, gone dealers turn now
		houseLogic(this.Players.get(0));
		 
		//Prints results messages
		int lostCount =0; 
		for(Player P : this.Players) {
			if(P.name == "House") continue; //skip the dealer
			
			if(P.Hands.size()==1) {
				if(P.Hands.get(0).myValue > this.Players.get(0).Hands.get(0).myValue && P.Hands.get(0).myValue <= 21) {
					System.out.println(P.name + " Wins!\n"); 
				}else if(P.Hands.get(0).myValue <= 21 && P.Hands.get(0).myValue == this.Players.get(0).Hands.get(0).myValue)
					System.out.println(P.name + " Draws!"); 
				else {
					System.out.println(P.name + " Loses!"); 
					lostCount++;
				}
			}//end of 1 hand print result
			else{ 
				for(Hand H : P.Hands) {
					if(H.myValue > this.Players.get(0).Hands.get(0).myValue && H.myValue <= 21 )
						System.out.println(P.name + " Hand " + P.Hands.indexOf(H) + " WINS! \n"); 
					else if(H.myValue == this.Players.get(0).Hands.get(0).myValue && H.myValue <= 21)
						System.out.println(P.name + " Hand " + P.Hands.indexOf(H) + " Draws! \n");
					else {
						System.out.println(P.name + " Hand " + P.Hands.indexOf(H) + " Loses!"); 
						lostCount++;
					}
						
				}
			}
			
			
		}//end of determine winner loop
		
		if(lostCount == this.Players.size() -1)
			System.out.println("House Wins!");
		
	}//end of play hand
	
	//draws top card from shoe, adds value to player's hand then adds card to player's hand
	public void hit(Hand H) {
			H.myCards.add( this.shoe.contents.remove(0)); 
			H.updateHandValue(); 
			//handles converting ace to 1 if total is over 21
			if(H.myValue > 21 && hasAce(H) != -1 ) {
				H.myCards.get(hasAce(H)).myValue =1; //change Ace value to one
				//recalculate hand value
				H.updateHandValue();
			}//end of convert ace if
	}//end of hit
	
	public void houseLogic(Player house) {
		if(house.Hands.get(0).myValue >= 17) System.out.println(house); //prints hand if we never hit
		while(house.Hands.get(0).myValue< 17 ) {
			this.hit(house.Hands.get(0));
			System.out.println(house); 
		}
		//bust means relitive value is zero
		if(house.Hands.get(0).myValue > 21) house.Hands.get(0).myValue = 0; 
	}//end of dealer logic
	
	//returns index of ace if the player hand contains an Ace
	public int hasAce(Hand H) {
		for(card C : H.myCards) {
			if(C.cardType.equals("Ace"))
				return H.myCards.indexOf(C);
		}
		return -1; 
	}//end of hasAce
	
	//determines if input string is a number
	public Boolean isNumeric(String testString) {
		if (testString.matches("-?\\d+")) { //reg rex expression asks for only numeric digits
			return true;
		}else
			return false;
		
	}//end of isNumeric
	
	//resets removes all hands from player
	public void resetPlayers() {
		for(Player P : this.Players) {
			P.reset();
		}//end of P loop
	}//end of reset players
	/* 
	public ArrayList<card> splitLogic(ArrayList<card> doubleHand, Player P) {
		
		System.out.println("SPLIT OPPERTUNITY! \n"
				+ "Please enter 1 if you want to split 2 to continue \n");
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
		
		if(playerResponse ==1) {
			ArrayList <card> newHand = new ArrayList<card>(); 
			//split the hand
			newHand.add(doubleHand.remove(0));
			this.hit(newHand);
			return newHand; 
		}
		else return null; 
	}//end of split logic
	*/ 
}//end of BlackJack
