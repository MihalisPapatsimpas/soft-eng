package mainEngine;
import java.io.PrintStream;
import java.util.*;


public abstract class PatternComposite extends PatternComponent{
	private ArrayList<PatternComponent> componentList = new ArrayList<PatternComponent>();
	
	public ArrayList<PatternComponent> getComponentList(){
		return componentList;
	}
	
	public void setContents(String contents, int index){
		componentList.get(index).setContents(contents);
	}
	
	public void saveContents(PrintStream printStream){
		try{
			printStream.print(toString());
		}catch(Exception e){
			System.err.println("Caught Exception: " + e.getMessage());
		}
	}

	public void add(PatternComponent patternComponent){
		componentList.add(patternComponent);
	}
	
	public String getContests(int index){
		return componentList.get(index).getContests();
		
	}
	
	public String getLabels(int index){
		return componentList.get(index).getLabel();
		
	}
	
	public ArrayList<String> getLabels(){
		ArrayList<String> labels = new ArrayList<String>();
		for (PatternComponent current : componentList) {
			labels.add(current.getLabel());
		}
		return labels;
	}
	
	public void remove(int index){
		componentList.remove(index);
	}
	
	public PatternComponent getChild(int index){
		return componentList.get(index);
	}
	
	public int getSize(){ 
		return componentList.size();
	}
	
	public String toString(){
		StringBuilder builder = new StringBuilder();
		String str;
		builder.append(getName() + '\n');
		for(PatternComponent value : componentList ){
			builder.append(value.toString() + '\n');
		}
		str = builder.toString();
		return str;
	}
	
	public abstract void decorateComponents(LatexDecoratorFactory latexDecoratorFactory);
}
