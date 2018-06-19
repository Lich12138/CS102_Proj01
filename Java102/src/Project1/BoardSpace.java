
public class BoardSpace {
	
    int printrow;
    int printcol;
    int pointvalue;
    String type;
    // "R" for regular  "E" for end node of the Board
    //the playerId which occupied this node:A or B or C or D
    String playerId;
    //the number of the square:1,2,3,4,5,6...
    Integer number;
    BoardSpace(Integer number) {
        this.number = number;
        this.playerId = null;
    }
    public boolean isOccupiedByOthers(){
        return playerId!=null;
    }
    public void reset(){
        playerId=null;
    }
    public String getOccupiedPlayer(){
        return playerId;
    }
    public void setOccupiedPlayer(String playerId){
        this.playerId = playerId;
    }
    public Integer getNumber() {
        return number;
    }
    public String toString() {
    	return "(" + printrow + "," + printcol + ")-" + pointvalue
    			+"type="+type+",playerId="+playerId+",number="+number+",pointvalue="+pointvalue;
    }
}
