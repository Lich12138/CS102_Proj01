import java.util.Random;

public class CallDoublyLinkedList {
// This game makes a board of 20 spaces. Player A moves forward
// through the spaces each turn 1-3 spaces. Player A add the value
// of each board space to its score.
	public static void main(String[] args) {
		Board board = new Board();
		board.init();
		//run the game with 1 player
		board.runGame(1);
//		//run the game with 2 player
		board.runGame(2);
//		//run the game with 3 player
		board.runGame(3);
//		//run the game with 4 player
		board.runGame(4);
		//run the game with 1 input set
		//board.runGameWithInputs(678,3,16);
		//board.runGameWithInputs(3,1,3);
		//}
		
	}

}
