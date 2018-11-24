package mainEngine;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.swing.JTextPane;

public class Controller {
	
	private PatternLanguage patternLanguage;
	private TemplateFactory factory = new TemplateFactory();
	private ArrayList<Pattern> patterns;
	private Pattern pattern;
	private OutputStream out;
	private PrintStream printStream;
	private LatexDecoratorFactory latexDecoratorFactory = new LatexDecoratorFactory();
	
	public String toString(){
		StringBuilder builder = new StringBuilder();
		String str;
		builder.append("Title:  " + patternLanguage.getName() + '\n');
		for(int i = 0; i < patterns.size(); i++){
			builder.append(patterns.get(i).toString() + '\n');
		}
		str = builder.toString();
		return str;
	}
	
	public void createPatternLanguage(String title) {
		patternLanguage = new PatternLanguage();
		patterns = new ArrayList<Pattern>();
		patternLanguage.setName(title);
	}
	
	public ArrayList<String> addPattern(String name) {
		pattern = factory.createTemplate(name);
		patterns.add(pattern); //creates patterns
		patternLanguage.add(pattern);	//add to pattern language
		return pattern.getLabels();
	}
	
	public void setFields(ArrayList<JTextPane> fields, Pattern pattern){
		for(int i = 0; i < fields.size(); i++){
			pattern.setContents(fields.get(i).getText(), i);
		}
	}
	
	public void savePatternLanguage(String path, String fileName) throws FileNotFoundException{
		out = new FileOutputStream(path + "/" + fileName);
		printStream = new PrintStream(out);
		printStream.print("Title: ");
		printStream.print(patternLanguage.getName() + '\n' + '\n');
		for(int i = 0; i < patterns.size(); i++){
			patterns.get(i).saveContents(printStream);
			printStream.print('\n');
		}
		printStream.close();
	}
	
	public Pattern getPattern(){
		return patterns.get(patterns.size() - 1);
	}
	
	public boolean removePattern(String patternName){
		for(int i = 0; i < patternLanguage.getSize(); i++){
			if(patterns.get(i).getContests(0).equals(patternName)){
				patternLanguage.remove(i);
				patterns.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public int getPatternPotition(String patternName){
		int patternPotition = -1;
		
		for(int i = 0; i < patternLanguage.getSize(); i++){
			System.out.println(patterns.get(i).getContests(0));
			if(patterns.get(i).getContests(0).equals(patternName)){
				return patternPotition = i;
			}
		}
		
		return patternPotition;
	}

	public Pattern getPattern(int index){
		return patterns.get(index);
	}

	public ArrayList<String> getLabels(int patternPosition) {
		ArrayList<String> labels = new ArrayList<String>();
		for(int i = 0; i < patterns.get(patternPosition).getSize(); i ++){
			labels.add(patterns.get(patternPosition).getLabels(i));
		}
		return labels;
	}

	public ArrayList<String> getContents(int patternPosition) {
		ArrayList<String> contents = new ArrayList<String>();
		for(int i = 0; i < patterns.get(patternPosition).getSize(); i ++){
			contents.add(patterns.get(patternPosition).getContests(i));
		}
		return contents;
	}

	public void saveDecoratedPatternLanguage(String path, String fileName) throws FileNotFoundException {
		out = new FileOutputStream(path + "/" + fileName);
		printStream = new PrintStream(out);
		printStream.print("\\documentclass{article}" + '\n'+ '\n' + "\\begin{document}" + '\n' + '\n' + "\\title{" + patternLanguage.getName() + "}" + '\n' + '\n' + "\\maketitle" + '\n' + '\n');
		patternLanguage.decorateComponents(latexDecoratorFactory);
		for(int i = 0; i < patternLanguage.getSize(); i++){
			patternLanguage.getChild(i).saveName(printStream); //suction{}
			patterns.get(i).decorateComponents(latexDecoratorFactory);
			for(int j = 0; j < patterns.get(i).getSize(); j++){
				printStream.print('\t');
				patterns.get(i).getChild(j).saveName(printStream);
				patterns.get(i).getChild(j).saveContents(printStream);
			}
			printStream.print('\n');
		}
		printStream.print("\\end{document}");
		printStream.close();
	}
	
	public void loadPatternLanguage(String path){
		String loadedContents = loadContents(path);
		convertData(loadedContents);
	}
	
	public void loadDecoratedPatternLanguage(String path){
		String loadedContents = loadContents(path);
		convertDecoratedData(loadedContents);
	}
	
	public String loadContents(String fullPath){
		String line;
		String loadedFile = "";
		try{
			InputStream inPutStream = new FileInputStream(fullPath);
			InputStreamReader inPutStreamReader = new InputStreamReader(inPutStream);
			BufferedReader fileReader = new BufferedReader(inPutStreamReader);
			while((line = fileReader.readLine()) != null){
				loadedFile += line;
				loadedFile += '\n';
			}
			inPutStream.close();
			return loadedFile;
		}catch(Exception e){
			System.err.println("Caught Exception: " + e.getMessage());
			return "";
		}
	}

	public void convertData(String loadedData){
	 	String [] splitedData = loadedData.split("\\s+");
		String contents = "";
		int i = 0;
		
		//reads title
		if(splitedData[i].equals("Title:")){
			i++;
			while(!(splitedData[i].equals("MicroPattern")) && !(splitedData[i].equals("Inductive-Mini-Pattern")) && !(splitedData[i].equals("Deductive-Mini-Pattern")) && !(splitedData[i].equals("System-of-Patterns-Template"))  && !(splitedData[i].equals("Gang-of-four-Pattern")) && i < splitedData.length){
				contents += splitedData[i] + " "; 
				i++;	
			}
		}else{
			System.out.println("Error");
		}
		//creates pattern language with title
		patternLanguage = new PatternLanguage();
		patterns = new ArrayList<Pattern>();
		patternLanguage.setName(contents);
		
		//checks what kind of pattern we found

		while(i < splitedData.length){
			contents = "";
			if(splitedData[i].equals("MicroPattern")){
				patterns.add(factory.createTemplate(splitedData[i])); //creates patterns
				patternLanguage.add(patterns.get(patterns.size() - 1));	//add to pattern language
				i++;
				i = setLoadedContents("Template:", splitedData, i, 0, patterns.get(patterns.size() - 1));
				i--;
				i = setLoadedContents("Problem:", splitedData, i, 1, patterns.get(patterns.size() - 1));
				i--;
				i = setLoadedContents("Solution:", splitedData, i, 2, patterns.get(patterns.size() - 1));
				while(i < splitedData.length && (!splitedData[i].equals("MicroPattern") && !splitedData[i].equals("Inductive-Mini-Pattern") && !splitedData[i].equals("Deductive-Mini-Pattern") && !splitedData[i].equals("Gang-of-four-Pattern") && !splitedData[i].equals("System-of-Patterns-Template"))){ 
					contents += splitedData[i] + " ";
					i++;
				}
				patterns.get(patterns.size() - 1).getChild(3).setContents(contents);
				contents = "";
				if(i>= splitedData.length) break;
			}
			if(splitedData[i].equals("Inductive-Mini-Pattern")){
				patterns.add(factory.createTemplate(splitedData[i])); //creates patterns
				patternLanguage.add(patterns.get(patterns.size() - 1));	//add to pattern language
				i++;
				i = setLoadedContents("Template:", splitedData, i, 0, patterns.get(patterns.size() - 1));
				i--;
				i = setLoadedContents("Context:", splitedData, i, 1, patterns.get(patterns.size() - 1));
				i--;
				i = setLoadedContents("Forces:", splitedData, i, 2, patterns.get(patterns.size() - 1));
				i--;
				i = setLoadedContents("Solution:", splitedData, i, 3, patterns.get(patterns.size() - 1));
				while(i < splitedData.length && (!splitedData[i].equals("MicroPattern") && !splitedData[i].equals("Inductive-Mini-Pattern") && !splitedData[i].equals("Deductive-Mini-Pattern") && !splitedData[i].equals("Gang-of-four-Pattern") && !splitedData[i].equals("System-of-Patterns-Template"))){ 
					contents += splitedData[i] + " ";
					i++;
				}
				patterns.get(patterns.size() - 1).getChild(4).setContents(contents);
				contents = "";
				if(i>= splitedData.length) break;
			}
			
			if(splitedData[i].equals("Deductive-Mini-Pattern")){
				patterns.add(factory.createTemplate(splitedData[i])); //creates patterns
				patternLanguage.add(patterns.get(patterns.size() - 1));	//add to pattern language
				i++;
				i = setLoadedContents("Template:", splitedData, i, 0, patterns.get(patterns.size() - 1));
				i--;
				i = setLoadedContents("Problem:", splitedData, i, 1, patterns.get(patterns.size() - 1));
				i--;
				i = setLoadedContents("Solution:", splitedData, i, 2, patterns.get(patterns.size() - 1));
				i--;
				i = setLoadedContents("Benefits:", splitedData, i, 3, patterns.get(patterns.size() - 1));
				i--;
				i = setLoadedContents("Consequences:", splitedData, i, 4, patterns.get(patterns.size() - 1));
				while(i < splitedData.length && (!splitedData[i].equals("MicroPattern") && !splitedData[i].equals("Inductive-Mini-Pattern") && !splitedData[i].equals("Deductive-Mini-Pattern") && !splitedData[i].equals("Gang-of-four-Pattern") && !splitedData[i].equals("System-of-Patterns-Template"))){ 
					contents += splitedData[i] + " ";
					i++;
				}
				patterns.get(patterns.size() - 1).getChild(5).setContents(contents);
				contents = "";
				if(i>= splitedData.length) break;
			}
			
			if(splitedData[i].equals("Gang-of-four-Pattern")){
				patterns.add(factory.createTemplate(splitedData[i])); //creates patterns
				patternLanguage.add(patterns.get(patterns.size() - 1));	//add to pattern language
				i++;
				i = setLoadedContents("Template:", splitedData, i, 0, patterns.get(patterns.size() - 1));
				i--;
				i = setLoadedContents("Pattern-Classification:", splitedData, i, 1, patterns.get(patterns.size() - 1));
				i--;
				i = setLoadedContents("Intent:", splitedData, i, 2, patterns.get(patterns.size() - 1));
				i--;
				i = setLoadedContents("Also-Known-As:", splitedData, i, 3, patterns.get(patterns.size() - 1));
				i--;
				i = setLoadedContents("Motivation:", splitedData, i, 4, patterns.get(patterns.size() - 1));
				i--;
				i = setLoadedContents("Applicability:", splitedData, i, 5, patterns.get(patterns.size() - 1));
				i--;
				i = setLoadedContents("Structure:", splitedData, i, 6, patterns.get(patterns.size() - 1));
				i--;
				i = setLoadedContents("Participants:", splitedData, i, 7, patterns.get(patterns.size() - 1));
				i--;
				i = setLoadedContents("Collaborations:", splitedData, i, 8, patterns.get(patterns.size() - 1));
				i--;
				i = setLoadedContents("Consequences:", splitedData, i, 9, patterns.get(patterns.size() - 1));
				i--;
				i = setLoadedContents("Implementation:", splitedData, i, 10, patterns.get(patterns.size() - 1));
				i--;
				i = setLoadedContents("Sample-Code:", splitedData, i, 11, patterns.get(patterns.size() - 1));
				i--;
				i = setLoadedContents("Known-Uses:", splitedData, i, 12, patterns.get(patterns.size() - 1));
				i--;
				i = setLoadedContents("Related-Patterns:", splitedData, i, 13, patterns.get(patterns.size() - 1));
				while(i < splitedData.length && (!splitedData[i].equals("MicroPattern") && !splitedData[i].equals("Inductive-Mini-Pattern") && !splitedData[i].equals("Deductive-Mini-Pattern") && !splitedData[i].equals("Gang-of-four-Pattern") && !splitedData[i].equals("System-of-Patterns-Template"))){ 
					contents += splitedData[i] + " ";
					i++;
				}
				patterns.get(patterns.size() - 1).getChild(14).setContents(contents);
				contents = "";
				if(i>= splitedData.length) break;
			}
			
			if(splitedData[i].equals("System-of-Patterns-Template")){
				patterns.add(factory.createTemplate(splitedData[i])); //creates patterns
				patternLanguage.add(patterns.get(patterns.size() - 1));	//add to pattern language
				i++;
				i = setLoadedContents("Template:", splitedData, i, 0, patterns.get(patterns.size() - 1));
				i--;
				i = setLoadedContents("Also-Known-As:", splitedData, i, 1, patterns.get(patterns.size() - 1));
				i--;
				i = setLoadedContents("Example:", splitedData, i, 2, patterns.get(patterns.size() - 1));
				i--;
				i = setLoadedContents("Contex:", splitedData, i, 3, patterns.get(patterns.size() - 1));
				i--;
				i = setLoadedContents("Problem:", splitedData, i, 4, patterns.get(patterns.size() - 1));
				i--;
				i = setLoadedContents("Solution:", splitedData, i, 5, patterns.get(patterns.size() - 1));
				i--;
				i = setLoadedContents("Structure:", splitedData, i, 6, patterns.get(patterns.size() - 1));
				i--;
				i = setLoadedContents("Dynamics:", splitedData, i, 7, patterns.get(patterns.size() - 1));
				i--;
				i = setLoadedContents("Implementation:", splitedData, i, 8, patterns.get(patterns.size() - 1));
				i--;
				i = setLoadedContents("Example-Resolved:", splitedData, i, 9, patterns.get(patterns.size() - 1));
				i--;
				i = setLoadedContents("Variants:", splitedData, i, 10, patterns.get(patterns.size() - 1));
				i--;
				i = setLoadedContents("Known-Uses:", splitedData, i, 11, patterns.get(patterns.size() - 1));
				i--;
				i = setLoadedContents("Consequences:", splitedData, i, 12, patterns.get(patterns.size() - 1));
				while(i < splitedData.length && (!splitedData[i].equals("MicroPattern") && !splitedData[i].equals("Inductive-Mini-Pattern") && !splitedData[i].equals("Deductive-Mini-Pattern") && !splitedData[i].equals("Gang-of-four-Pattern") && !splitedData[i].equals("System-of-Patterns-Template"))){ 
					contents += splitedData[i] + " ";
					i++;
				}
				patterns.get(patterns.size() - 1).getChild(13).setContents(contents);
				contents = "";
				if(i>= splitedData.length) break;
			}
		}
	}
	
	public void convertDecoratedData(String loadedData) {
		String [] splitedData = loadedData.split("\\\\");
		String stringInsideBrachets;
		int i = 1;
		while(!splitedData[i].contains("title")) {
			i++;
		}
		stringInsideBrachets=splitedData[i].substring(splitedData[i].indexOf("{")+1,splitedData[i].indexOf("}"));
		patternLanguage = new PatternLanguage();
		patterns = new ArrayList<Pattern>();
		patternLanguage.setName(stringInsideBrachets);
		i++;
		while(!splitedData[i].contains("section") && i < splitedData.length){
			i++;	
		}
		
		while(i < splitedData.length - 1){
			stringInsideBrachets=splitedData[i].substring(splitedData[i].indexOf("{")+1,splitedData[i].indexOf("}"));
			if(stringInsideBrachets.equals("MicroPattern")){
				patterns.add(factory.createTemplate(stringInsideBrachets)); //creates patterns
				patternLanguage.add(patterns.get(patterns.size() - 1));	//add to pattern language
				i++;
				splitName("\\subsection{Name:}", splitedData, i, 0, patterns.get(patterns.size() - 1));
				i++;
				setDecoratedContents("\\subsection{Template:}", splitedData, i, 1, patterns.get(patterns.size() - 1));
				i++;
				setDecoratedContents("\\subsection{Problem:}", splitedData, i, 2, patterns.get(patterns.size() - 1));
				i++;
				setDecoratedContents("\\subsection{Solution:}", splitedData, i, 3, patterns.get(patterns.size() - 1));
				i++;
			}
			stringInsideBrachets=splitedData[i].substring(splitedData[i].indexOf("{")+1,splitedData[i].indexOf("}"));
			if(stringInsideBrachets.equals("Inductive-Mini-Pattern")){
				patterns.add(factory.createTemplate(stringInsideBrachets)); //creates patterns
				patternLanguage.add(patterns.get(patterns.size() - 1));	//add to pattern language
				i++;
				splitName("\\subsection{Name:}", splitedData, i, 0, patterns.get(patterns.size() - 1));
				i++;
				setDecoratedContents("\\subsection{Template:}", splitedData, i, 1, patterns.get(patterns.size() - 1));
				i++;
				setDecoratedContents("\\subsection{Context:}", splitedData, i, 2, patterns.get(patterns.size() - 1));
				i++;
				setDecoratedContents("\\subsection{Forces:}", splitedData, i, 3, patterns.get(patterns.size() - 1));
				i++;
				setDecoratedContents("\\subsection{Solution:}", splitedData, i, 4, patterns.get(patterns.size() - 1));
				i++;
			}
			stringInsideBrachets=splitedData[i].substring(splitedData[i].indexOf("{")+1,splitedData[i].indexOf("}"));
			if(stringInsideBrachets.equals("Deductive-Mini-Pattern")){
				patterns.add(factory.createTemplate(stringInsideBrachets)); //creates patterns
				patternLanguage.add(patterns.get(patterns.size() - 1));	//add to pattern language
				i++;
				splitName("\\subsection{Name:}", splitedData, i, 0, patterns.get(patterns.size() - 1));
				i++;
				setDecoratedContents("\\subsection{Template:}", splitedData, i, 1, patterns.get(patterns.size() - 1));
				i++;
				setDecoratedContents("\\subsection{Problem:}", splitedData, i, 2, patterns.get(patterns.size() - 1));
				i++;
				setDecoratedContents("\\subsection{Solution:}", splitedData, i, 3, patterns.get(patterns.size() - 1));
				i++;
				setDecoratedContents("\\subsection{Benefits:}", splitedData, i, 4, patterns.get(patterns.size() - 1));
				i++;
				setDecoratedContents("\\subsection{Consequences:}", splitedData, i, 5, patterns.get(patterns.size() - 1));
				i++;
			}
			stringInsideBrachets=splitedData[i].substring(splitedData[i].indexOf("{")+1,splitedData[i].indexOf("}"));
			if(stringInsideBrachets.equals("Gang-of-four-Pattern")){
				patterns.add(factory.createTemplate(stringInsideBrachets)); //creates patterns
				patternLanguage.add(patterns.get(patterns.size() - 1));	//add to pattern language
				i++;
				splitName("\\subsection{Name:}", splitedData, i, 0, patterns.get(patterns.size() - 1));
				i++;
				setDecoratedContents("\\subsection{Template:}", splitedData, i, 1, patterns.get(patterns.size() - 1));
				i++;
				setDecoratedContents("\\subsection{Pattern-Classification:}", splitedData, i, 2, patterns.get(patterns.size() - 1));	
				i++;
				setDecoratedContents("\\subsection{Intent:}", splitedData, i, 3, patterns.get(patterns.size() - 1));
				i++;
				setDecoratedContents("\\subsection{Also-Known-As:}", splitedData, i, 4, patterns.get(patterns.size() - 1));
				i++;
				setDecoratedContents("\\subsection{Motivation:}", splitedData, i, 5, patterns.get(patterns.size() - 1));
				i++;
				setDecoratedContents("\\subsection{Applicability:}", splitedData, i, 6, patterns.get(patterns.size() - 1));
				i++;
				setDecoratedContents("\\subsection{Structure:}", splitedData, i, 7, patterns.get(patterns.size() - 1));
				i++;
				setDecoratedContents("\\subsection{Participants:}", splitedData, i, 8, patterns.get(patterns.size() - 1));
				i++;
				setDecoratedContents("\\subsection{Collaborations:}", splitedData, i, 9, patterns.get(patterns.size() - 1));
				i++;
				setDecoratedContents("\\subsection{Consequences:}", splitedData, i, 10, patterns.get(patterns.size() - 1));
				i++;
				setDecoratedContents("\\subsection{Implementation:}", splitedData, i, 11, patterns.get(patterns.size() - 1));
				i++;
				setDecoratedContents("\\subsection{Sample-Code:}", splitedData, i, 12, patterns.get(patterns.size() - 1));
				i++;
				setDecoratedContents("\\subsection{Known-Uses:}", splitedData, i, 13, patterns.get(patterns.size() - 1));
				i++;
				setDecoratedContents("\\subsection{Related-Patterns:}", splitedData, i, 14, patterns.get(patterns.size() - 1));
				i++;
			}
			stringInsideBrachets=splitedData[i].substring(splitedData[i].indexOf("{")+1,splitedData[i].indexOf("}"));
			if(stringInsideBrachets.equals("System-of-Patterns-Template")){
				patterns.add(factory.createTemplate(stringInsideBrachets)); //creates patterns
				patternLanguage.add(patterns.get(patterns.size() - 1));	//add to pattern language
				i++;
				splitName("\\subsection{Name:}", splitedData, i, 0, patterns.get(patterns.size() - 1));
				i++;
				setDecoratedContents("\\subsection{Template:}", splitedData, i, 1, patterns.get(patterns.size() - 1));
				i++;
				setDecoratedContents("\\subsection{Also-Known-As:}", splitedData, i, 2, patterns.get(patterns.size() - 1));
				i++;
				setDecoratedContents("\\subsection{Example:}", splitedData, i, 3, patterns.get(patterns.size() - 1));
				i++;
				setDecoratedContents("\\subsection{Contex:}", splitedData, i, 4, patterns.get(patterns.size() - 1));
				i++;
				setDecoratedContents("\\subsection{Problem:}", splitedData, i, 5, patterns.get(patterns.size() - 1));
				i++;
				setDecoratedContents("\\subsection{Solution:}", splitedData, i, 6, patterns.get(patterns.size() - 1));
				i++;
				setDecoratedContents("\\subsection{Structure:}", splitedData, i, 7, patterns.get(patterns.size() - 1));
				i++;
				setDecoratedContents("\\subsection{Dynamics:}", splitedData, i, 8, patterns.get(patterns.size() - 1));
				i++;
				setDecoratedContents("\\subsection{Implementation:}", splitedData, i, 9, patterns.get(patterns.size() - 1));
				i++;
				setDecoratedContents("\\subsection{Example-Resolved:}", splitedData, i, 10, patterns.get(patterns.size() - 1));
				i++;
				setDecoratedContents("\\subsection{Variants:}", splitedData, i, 11, patterns.get(patterns.size() - 1));
				i++;
				setDecoratedContents("\\subsection{Known-Uses:}", splitedData, i, 12, patterns.get(patterns.size() - 1));
				i++;
				setDecoratedContents("\\subsection{Consequences:}", splitedData, i, 13, patterns.get(patterns.size() - 1));
				i++;
			}
		}
	}

	public void splitName(String label, String [] splitedData, int i, int index, Pattern pattern) {
		String contents;
		contents = (splitedData[i].substring(splitedData[i].indexOf("}")+1));
		contents = contents.trim().replaceAll("\\s"," ");
		pattern.getChild(index).setContents(contents);
	}
	
	public int setLoadedContents(String label, String [] splitedData, int i, int index, Pattern pattern){
		String contents = "";
		i++;
		while(!splitedData[i].contains(label)){
			if(splitedData[i+1].contains(label)){
				contents += splitedData[i];
				i++;
			}else{
				contents += splitedData[i] + " ";
				i++;
			}
		}
		pattern.getChild(index).setContents(contents);
		i++;
		return i;
	}
	
	public void setDecoratedContents(String label, String [] splitedData, int i, int index, Pattern pattern) {
		String contents = "";
		contents = splitedData[i].substring(splitedData[i].indexOf("}")+1);
		pattern.getChild(index).setContents(contents);
		return;
	}
}
