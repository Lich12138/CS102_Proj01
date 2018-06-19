import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Board {

    //This DoubleLinkedList holds the actual data of the board
    private DoublyLinkedList doubleLinkedList;

    //the board's dimension is 6脳6
    private static final Integer BOARD_COLUMN_COUNT = 5;

    private static final Integer TOTAL_GAME_TIMES = 1000;

    private static final String[] playerIds = {"A","B","C","D"};

    private static final String LINE_SEPARATOR = "\r\n";

    private static final String TEST_FILE_PREFIX = "src/test_data_";

    private static final String TEST_FILE_SUFFIX = ".txt";

    private Integer[][] diceArray1 = new Integer[TOTAL_GAME_TIMES][1];
    private Integer[][] diceArray2 = new Integer[TOTAL_GAME_TIMES][2];
    private Integer[][] diceArray3 = new Integer[TOTAL_GAME_TIMES][3];
    private Integer[][] diceArray4 = new Integer[TOTAL_GAME_TIMES][4];

    private Player playerA = new Player(playerIds[0]);
    private Player playerB = new Player(playerIds[1]);
    private Player playerC = new Player(playerIds[2]);
    private Player playerD = new Player(playerIds[3]);

    int count = 0;

    public void display(int gameTime){
        int col = 0;
        boolean stopPrint = false;
        DoublyLinkedList.Node node = doubleLinkedList.getFirstNode();
        System.out.println("************"+gameTime+"th:"+"The current status of the Board************");
        while(node!=null){
            col = 0;
            for(;col<BOARD_COLUMN_COUNT;++col){
                BoardSpace boardSpace = (BoardSpace) node.getElement();
                if(boardSpace.isOccupiedByOthers()){
                    System.out.print(boardSpace.getOccupiedPlayer());
                }else{
                    System.out.print("#");
                }
                node = node.getNext();
                if(doubleLinkedList.isTrailerNode(node)){
                    stopPrint = true;
                    break;
                }
                if(col==BOARD_COLUMN_COUNT-1){
                    System.out.println();
                }
            }
            if(stopPrint){
                break;
            }
        }
        System.out.println();
    }

    public void init(){
        doubleLinkedList = new DoublyLinkedList();
        for(int i = 0;i<20;++i){
            BoardSpace space = new BoardSpace(i+1);
            space.printrow = i / 5;
            space.printcol = i % 5;
            space.pointvalue= space.printcol+1;
            doubleLinkedList.addLast(space);
        }
        loadTestCase(1);
        loadTestCase(2);
        loadTestCase(3);
        loadTestCase(4);
    }
    
    
    public void displayLinkList() {
    	DoublyLinkedList.Node node=doubleLinkedList.getFirstNode();
    	DoublyLinkedList.Node tmp=node;
    	int index=0;
    	while(!doubleLinkedList.isLastNode(tmp)) {
    		index++;
    		BoardSpace space =(BoardSpace)(tmp.getElement());
    		System.out.println(index+","+space.pointvalue+","+space.playerId);
    		tmp=tmp.getNext();
    	}
    	index++;
    	BoardSpace space =(BoardSpace)(tmp.getElement());
//		System.out.println(index+","+space.pointvalue+","+space.playerId);
    	
    }
    

    public void runGame(Integer playerCount){
        System.out.println("************"+playerCount+"-Player Game Begin************");
        playerA.setCurrentSquare(doubleLinkedList.getFirstNode());
        playerB.setCurrentSquare(doubleLinkedList.getFirstNode());
        playerC.setCurrentSquare(doubleLinkedList.getFirstNode());
        playerD.setCurrentSquare(doubleLinkedList.getFirstNode());
        boolean end = false;
        outerloop:for(int time = 0;time<TOTAL_GAME_TIMES;++time){
            for(int playerIndex = 0;playerIndex<playerCount;++playerIndex){
                int dice;
                if(playerCount==1){
                    dice = diceArray1[time][playerIndex];
                }else if(playerCount==2){
                    dice = diceArray2[time][playerIndex];
                    
                }else if(playerCount==3){
                    dice = diceArray3[time][playerIndex];
                }else{
                    dice = diceArray4[time][playerIndex];
                }
//                System.out.println("dice="+dice);
//                System.out.println("list1:");
//                displayLinkList();
                if(playerIndex==0){
//                	System.out.println("A");
                    playerA.setCurrentDiceNumber(dice);
                    end = moveForward(playerA);
                }else if(playerIndex==1){
//                	System.out.println("B");
                    playerB.setCurrentDiceNumber(dice);
                    end = moveForward(playerB);
                }else if(playerIndex==2){
//                	System.out.println("C");
                    playerC.setCurrentDiceNumber(dice);
                    end = moveForward(playerC);
                }else{
//                	System.out.println("D");
                    playerD.setCurrentDiceNumber(dice);
                    end = moveForward(playerD);
                }
//                System.out.println("dice="+dice);
//                System.out.println("list2:");
//                displayLinkList();
                if(end){
                    break;
                }
            }
//            if((time+100)%1==0){
//                display(time+1);
//            }
            if(end){
                resetPlayersAndBoard();
                end = false;
            }
        }
        System.out.println("************Game Data Statistics Begin************");
        for(int i = 0;i<playerCount;++i){
            System.out.print(playerIds[i]);
            if(i!=playerCount-1){
                System.out.print("\t\t");
            }
        }
        System.out.println();
        for(int j = 0;j<playerCount;++j){
            if(j==0){
                System.out.print(String.format("%.0f",playerA.getAverageMove())+"/"+String.format("%.2f",(playerA.getWinMoves().size()*100.0/count))+"%\t");
            }else if(j==1){
                System.out.print(String.format("%.0f",playerB.getAverageMove())+"/"+String.format("%.2f",(playerB.getWinMoves().size()*100.0/count))+"%\t");
            }else if(j==2){
                System.out.print(String.format("%.0f",playerC.getAverageMove())+"/"+String.format("%.2f",(playerC.getWinMoves().size()*100.0/count))+"%\t");
            }else{
                System.out.print(String.format("%.0f",playerD.getAverageMove())+"/"+String.format("%.2f",(playerD.getWinMoves().size()*100.0/count))+"%\t");
            }
        }
        System.out.println();
        System.out.println("************Game Data Statistics End************");
        System.out.println("************"+playerCount+"-Player Game End************");
    }

    public void runGameWithInputs(Integer gameNo,Integer playerCount,Integer totalMoves){
        System.out.println("************Here is the status of the game board with provided inputs************");
        playerA.setCurrentSquare(doubleLinkedList.getFirstNode());
        playerB.setCurrentSquare(doubleLinkedList.getFirstNode ());
        playerC.setCurrentSquare(doubleLinkedList.getFirstNode());
        playerD.setCurrentSquare(doubleLinkedList.getFirstNode());
        boolean end = false;
        for(int time = 0;time<TOTAL_GAME_TIMES;++time){
            for(int playerIndex = 0;playerIndex<playerCount;++playerIndex){
                int dice;
                if(playerCount==1){
                    dice = diceArray1[time][playerIndex];
                }else if(playerCount==2){
                    dice = diceArray2[time][playerIndex];
                }else if(playerCount==3){
                    dice = diceArray3[time][playerIndex];
                }else{
                    dice = diceArray4[time][playerIndex];
                }
                if(playerIndex==0){
                    playerA.setCurrentDiceNumber(dice);
                    end = moveForward(playerA);
                    totalMoves-=playerA.getMove();
                }else if(playerIndex==1){
                    playerB.setCurrentDiceNumber(dice);
                    end = moveForward(playerB);
                    totalMoves-=playerB.getMove();
                }else if(playerIndex==2){
                    playerC.setCurrentDiceNumber(dice);
                    end = moveForward(playerC);
                    totalMoves-=playerC.getMove();
                }else{
                    playerD.setCurrentDiceNumber(dice);
                    end = moveForward(playerD);
                    totalMoves-=playerD.getMove();
                }
                if(end){
                    break;
                }
            }
            if((time+1)==gameNo){
                display(time+1);
                break;
            }
            if(end){
                resetPlayersAndBoard();
                end = false;
            }
        }
        System.out.println("************End************");
    }

    public boolean moveForward(Player player){
        DoublyLinkedList.Node currentNode,nextNode=null;
        currentNode = player.getCurrentSquare();
//        System.out.println("before moveForward:node="+currentNode);
//        System.out.println("before moveForward:player="+player); 
        player.setCurrentSquare(null);
        ((BoardSpace)currentNode.getElement()).reset();
        
        for(int i = 0;i<player.getCurrentDiceNumber();++i){
        	//System.out.println("1:"+currentNode);
        	nextNode = currentNode.getNext();
            //nextNode = currentNode.getNext();
//            System.out.println(doubleLinkedList.isLastNode(currentNode));
            if(doubleLinkedList.isLastNode(currentNode)){
//            	System.out.println(player.getTotal());
                //player.setTotal(player.getTotal()+((BoardSpace)nextNode.getElement()).getNumber());
                if(player.getTotal()>=40){
                	player.addMove(1);
                    ((BoardSpace)(doubleLinkedList.last())).setOccupiedPlayer(player.getId());
                    player.setCurrentSquare(doubleLinkedList.getLastNode());
                    player.getWinMoves().add(player.getMove());
//                    System.out.println("WinMoves:"+ player.getWinMoves());
                    ++count;
//                    System.out.println("#################"+player);
//                    System.out.println("after moveForward:node="+currentNode);
//                    System.out.println("after moveForward:player="+player); 
//                    System.out.println();
//                    System.out.println();
//                    System.out.println();
                    return true;
                }else{
                	nextNode=doubleLinkedList.getFirstNode();
                	currentNode = nextNode;
                	break;
                }
            }
            currentNode = nextNode;
            //System.out.println("2:"+currentNode);
            //System.out.println();
        }
        player.addMove(1);
//        System.out.println("new Position:"+currentNode);
        if(!((BoardSpace)nextNode.getElement()).isOccupiedByOthers()){
            ((BoardSpace)nextNode.getElement()).setOccupiedPlayer(player.getId());
            player.setCurrentSquare(nextNode);
            player.setTotal(player.getTotal()+((BoardSpace)nextNode.getElement()).pointvalue);
        }else{
            setPlayerBack3Step(player, currentNode);
            player.setTotal(player.getTotal()+((BoardSpace)nextNode.getElement()).pointvalue);
//            System.out.println("把之前的向后移动三个");
//            displayLinkList();
        }
        if(player.getTotal()%4==0){
            ((BoardSpace)player.getCurrentSquare().getElement()).reset();
            if(((BoardSpace)doubleLinkedList.getFirstNode().getElement()).isOccupiedByOthers()){
            	setPlayerBack3Step(player,doubleLinkedList.getFirstNode());
            }else {
            	currentNode=doubleLinkedList.getFirstNode();
            	((BoardSpace)currentNode.getElement()).setOccupiedPlayer(player.getId());
        		player.setCurrentSquare(currentNode);
            }
        }
//        System.out.println("after moveForward:node="+currentNode);
//        System.out.println("after moveForward:player="+player); 
//        System.out.println();
//        System.out.println();
//        System.out.println();
        return false;
    }

	private DoublyLinkedList.Node setPlayerBack3Step(Player player, DoublyLinkedList.Node currentNode){
			DoublyLinkedList.Node prePlayerNewPosition=currentNode;
		Player previousPlayer = getPlayer(((BoardSpace)currentNode.getElement()).getOccupiedPlayer());
//		System.out.println("setPlayerBack3Step"+player.getId()+":previousPlayer="+previousPlayer);
		((BoardSpace)currentNode.getElement()).reset();
		DoublyLinkedList.Node previous=currentNode;
		for(int i = 0;i<3;++i){
		    if(previous!=doubleLinkedList.getFirstNode()){
//		    	System.out.println("pre node");
		        previous = prePlayerNewPosition.getPrev();
		        prePlayerNewPosition = previous;
		    }else{
//		    	System.out.println("skip to last node");
		    	previous = doubleLinkedList.getLastNode();
		    	prePlayerNewPosition = previous;
		    }
		}
//		System.out.println("setPlayerBack3Step"+player.getId()+":prePlayerNewPosition="+prePlayerNewPosition);
		if(((BoardSpace)prePlayerNewPosition.getElement()).isOccupiedByOthers()) {
			setPlayerBack3Step(previousPlayer,prePlayerNewPosition);
		}
		
		((BoardSpace)currentNode.getElement()).setOccupiedPlayer(player.getId());
		player.setCurrentSquare(currentNode);
		
		
		return currentNode;
	}

    
  

    public void resetPlayersAndBoard(){
        playerA.reset(doubleLinkedList.getFirstNode());
        playerB.reset(doubleLinkedList.getFirstNode());
        playerC.reset(doubleLinkedList.getFirstNode());
        playerD.reset(doubleLinkedList.getFirstNode());
        resetDoubleLinkedList();
    }

    public void generateTestCase(Integer playerCount){
        try{
            String fileName = TEST_FILE_PREFIX + playerCount + "player" + TEST_FILE_SUFFIX;
            FileWriter writer = new FileWriter(fileName);
            StringBuilder sb = new StringBuilder();
            for(int i = 0;i<TOTAL_GAME_TIMES;++i){
                for(int j = 0;j<playerCount;++j){
                    sb.append(DiceUtils.generateDice());
                    if(j!=playerCount-1){
                        sb.append(" ");
                    }else if(i!=TOTAL_GAME_TIMES-1){
                        sb.append(LINE_SEPARATOR);
                    }
                }
            }
            writer.write(sb.toString());
            writer.close();
        }catch(Exception e){

        }
    }

    public void loadTestCase(Integer playerCount){
        try{
            String fileName = TEST_FILE_PREFIX + playerCount + "player" + TEST_FILE_SUFFIX;
            Scanner reader = new Scanner(new File(fileName));
            for(int i = 0;i<TOTAL_GAME_TIMES;++i){
                String line = reader.nextLine();
                String[] array = line.split(" ");
                for(int j = 0;j<playerCount;++j){
                    if(playerCount==1){
                        diceArray1[i][j] = Integer.parseInt(array[j]);
                    }else if(playerCount==2){
                        diceArray2[i][j] = Integer.parseInt(array[j]);
                    }else if(playerCount==3){
                        diceArray3[i][j] = Integer.parseInt(array[j]);
                    }else{
                        diceArray4[i][j] = Integer.parseInt(array[j]);
                    }
                }
            }
            reader.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public Player getPlayer(String id){
        if(id.equals("A")) return playerA;
        if(id.equals("B")) return playerB;
        if(id.equals("C")) return playerC;
        if(id.equals("D")) return playerD;
        return null;
    }

    public void resetDoubleLinkedList(){
        DoublyLinkedList.Node node = doubleLinkedList.getFirstNode();
        while(!doubleLinkedList.isTrailerNode(node)){
            ((BoardSpace)node.getElement()).reset();
            node = node.getNext();
        }
    }
}
