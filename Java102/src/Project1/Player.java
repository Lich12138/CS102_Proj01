
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Player {
	
	public String toString(){
		return "id="+id+"total="+total+",move="+move+",currentSquare="+currentSquare+",currentDiceNumber="+currentDiceNumber+
				";";
	}
	
    //total score of the player
    private Integer total = 0;

    //total moves
    private Integer move = 0;

    //the node of the linkedlist which is occupied by the player
    private DoublyLinkedList.Node currentSquare;

    //the current dice number
    private Integer currentDiceNumber;

    //A,B,C,D
    private String id;

    //the list to save all of the player's winning
    private List<Integer> winMoves = new ArrayList<Integer>();

    public Player(String id) {
        this.id = id;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
//    	System.out.println("old:"+this.total);
        this.total = total;
//        System.out.println("new:"+this.total);
    }

    public Integer getCurrentDiceNumber() {
        return currentDiceNumber;
    }

    public void setCurrentDiceNumber(Integer currentDiceNumber) {
        this.currentDiceNumber = currentDiceNumber;
    }

    public DoublyLinkedList.Node getCurrentSquare() {
        return currentSquare;
    }

    public void setCurrentSquare(DoublyLinkedList.Node currentSquare) {
        this.currentSquare = currentSquare;
    }

    public String getId() {
        return id;
    }

    public void reset(DoublyLinkedList.Node start){
        setTotal(0);
        setCurrentSquare(start);
        setMove(0);
    }

    public Integer getMove() {
        return move;
    }

    public void setMove(Integer move) {
        this.move = move;
    }

    public List<Integer> getWinMoves() {
        return winMoves;
    }

    public void addMove(Integer move){
        setMove(getMove()+move);
    }

    public Double getAverageMove(){
        int sum = 0;
        for(Integer temp:winMoves){
            sum+=temp;
        }
        return sum*1.0/winMoves.size();
    }
    
    
}
