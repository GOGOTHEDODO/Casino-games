import java.util.ArrayList;
import java.util.Random;

public class Deck {

	protected ArrayList<card> contents; 
	protected int size; 
	Random rand = new Random(System.currentTimeMillis()); 
	
	public Deck(int numDecks) {
		contents = new ArrayList<card>(); 
		this.size = numDecks*52; 
		int j;
		
		//adds one entire deck worth of cards equal to num decks
		for(int i = 0; i < numDecks; i++ ) {
			for(j=1; j<14; j++) {
				contents.add(new card(j, "Spades")); 
			}
			for(j=1; j<14; j++) {
				contents.add(new card(j, "Hearts")); 
			}
			for(j=1; j<14; j++) {
				contents.add(new card(j, "Clubs")); 
			}
			for(j=1; j<14; j++) {
				contents.add(new card(j, "Dimonds")); 
			}
		}//end of deck creation for loop
		
		this.shuffle();
	}//end of constructor
	
	public String toString() {
		for(card C : contents) {
			System.out.println(C);
			
		}//end of for loop
		return ""; 
	}
	
	public card draw() {
		this.size--; 
		return this.contents.remove(0); 
	}
	
	public void shuffle () {
		for(int i=this.contents.size(); i >0; i-- ) {
			//remove a random element from the list then add it to the back of the list
			//each loop the possible random index reduces by one thus implimenting a Fisher–Yates shuffle O(N) time
			this.contents.add(contents.remove(this.rand.nextInt(i))); 
			
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			Deck test = new Deck(1);
			
			System.out.println(test); 
	}

}
