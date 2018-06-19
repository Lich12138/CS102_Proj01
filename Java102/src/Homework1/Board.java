package Homework1;

public class Board {
	private DoublyLinkedList<Integer> dll = new DoublyLinkedList<Integer>();
	
	public Board() {
		Integer[] arr = {5, 10, 8, 10, 7, 5, 9, 10, 6, 7, 10, 6, 5, 8, 9, 5, 10, 5, 9, 6, 8, 7, 10, 6, 8};
		for(int i : arr) {
			dll.add(i);
		}
	}
	
	public int score(int loc) {
		return dll.loc(loc).getElement().intValue();
	}
	
	public void printBoard() {
		System.out.print("Start - ");
		dll.toString(0, 8);
		System.out.print("\n");
		dll.toString(9, 16);
		System.out.print("\n");
		dll.toString(17, 26);
		System.out.print(" - End");
	}
	
	public void printLoc() {
		//show where the plays are
		//Q: how to determine how many players
		//Q: how to get the parameter
	}
	
}
