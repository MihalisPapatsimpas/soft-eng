package mainEngineTest;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import mainEngine.Controller;
import mainEngine.Pattern;
import mainEngine.PatternLanguage;
import mainEngine.TemplateFactory;

import org.junit.Test;

public class LoadFromTextFile {

	@Test
	public void test() throws FileNotFoundException {
		OutputStream out = new FileOutputStream("./test.txt");;
		PrintStream printStream = new PrintStream(out);
		PatternLanguage patternLanguage = new PatternLanguage();
		TemplateFactory factory = new TemplateFactory();
	    ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		patternLanguage.setName("TestTitle");
		File tempFile = new File("./test.txt");
		Controller controller = new Controller();
		
		patterns.add(factory.createTemplate("MicroPattern"));//creates patterns
		patterns.get(0).getChild(0).setContents("TestName");
	    patternLanguage.add(patterns.get(patterns.size()-1));//add to pattern language
	    printStream.print("Title:  ");
		patternLanguage.saveContents(printStream);
		printStream.close();
		
		controller.loadPatternLanguage("./test.txt");
		assertEquals(controller.toString(),"Title:  TestTitle \nMicroPattern\nName:  TestName\nTemplate:  \nProblem:  \nSolution:  \n\n");
		tempFile.delete();
	}

}
