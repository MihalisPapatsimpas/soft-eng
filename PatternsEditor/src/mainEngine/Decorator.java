package mainEngine;
import java.io.PrintStream;

public class Decorator extends PatternComposite{
	private PatternComponent patternComponent;
	private String beginTag;
	private String endTag;
	
	public Decorator(String beginTag, String endTag, PatternComponent patternComponent){
		this.beginTag = beginTag;
		this.endTag = endTag;
		this.patternComponent = patternComponent;
	}

	public void saveName(PrintStream printStream){
		try{
			printStream.print(beginTag);
			patternComponent.saveName(printStream);
			printStream.print(endTag);
			printStream.print('\n');
			//printStream.flush();
		}catch(Exception e){
			System.err.println("Caught Exception: " + e.getMessage());
		}
	}
	
	public void saveContents(PrintStream printStream){
		printStream.print('\t');
		printStream.print(patternComponent.getContests());
		printStream.print('\n');
	}

	@Override
	public void decorateComponents(LatexDecoratorFactory latexDecoratorFactory) {
		// TODO Auto-generated method stub
		
	}
}
