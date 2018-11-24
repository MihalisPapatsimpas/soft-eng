package mainEngine;
import java.io.PrintStream;

public class PatternComponent {
	private String name;
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){

		this.name = name;
	}
	
	public String getContests(){
		return "";
	}
	
	public void setContents(String contents){
		return;
	}

	public void setLabel(String label){
		
	}
	
	public String getLabel(){
		return "";
	}
	
	public void setContents(String contents, int index){
		return;
	}
	
	public void add(PatternComponent patternComponent){
		return;
	}
	
	public void remove(int index){
		return;
	}
	
	public void saveContents(PrintStream printStream){
		return;
	}
	
	public PatternComponent getChild(int index){
		return null;
		
	}
	
	//for decorator
	public void saveName(PrintStream printStream){
		try{
			printStream.print(name);
		}catch(Exception e){
			System.err.println("Caught Exception: " + e.getMessage());
		}
	}
	
	public void setPatternComponent(PatternComponent patternComponent){
		return;
	}
}
