package mainEngine;
import java.io.PrintStream;

public class PatternPart extends PatternComponent {
	private String contents;
	private String label;
	
	public PatternPart(String label, String contents){
		this.contents = contents;
		this.label = label;
		setName(label);
	}
	
	public String getContests(){
		return this.contents;
	}
	
	public String getLabel(){
		return this.label;
	}
	
	public void setContents(String contents){
			this.contents = contents;
	}

	public void setLabel(String label){
		this.label = label;
	}
	
	public String toString(){
		String labelContents = this.label + " " + this.contents;
		return labelContents;
	}
	
	public void saveContents(PrintStream printStream){
		try{
			printStream.print(contents);
		}catch(Exception e){
			System.err.println("Caught Exception: " + e.getMessage());
		}
	}
	
}
