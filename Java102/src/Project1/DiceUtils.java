import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DiceUtils {
//	private static List<String> lines;
//	private static List<Integer> values;
//	//private static Iterator<Integer> iter;
//	private static int index=0;
//	static{
//		
//		// static code area will run automatically for only once
//		try {
//			lines=Files.readAllLines(Paths.get("test_data_1player.txt"));
//			values=new ArrayList<Integer>();
//			for(String line:lines) {
//				values.add(Integer.parseInt(line));
//			}
//			
	//		values=lines.stream().map((line)->Integer.parseInt(line)).collect(Collector.toList);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
    //generate a random number between 1 to 6
	
    public static Integer generateDice(){
        return (int)(Math.random()*6)+1;
    	//	Integer value=values.get(index);
      //  index++;
       // return value;
    }
}
