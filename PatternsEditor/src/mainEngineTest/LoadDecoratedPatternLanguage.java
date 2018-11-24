package mainEngineTest;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;

import mainEngine.Controller;

import org.junit.Test;

public class LoadDecoratedPatternLanguage {

	@Test
	public void test() throws FileNotFoundException {
		Controller controller = new Controller();
		Controller controller2= new Controller();
		File tempFile = new File("./test.txt");
		controller.createPatternLanguage("TestTitle");
		controller.addPattern("MicroPattern");
		controller.getPattern(0).getChild(0).setContents("TestName");
		controller.getPattern(0).getChild(1).setContents("TestTemplate");
		controller.getPattern(0).getChild(2).setContents("TestProblem");
		controller.getPattern(0).getChild(3).setContents("TestSolution");
		controller.saveDecoratedPatternLanguage("./","test.txt");
		controller2.loadDecoratedPatternLanguage("./test.txt");
		assertEquals(controller2.toString().replaceAll("\n","").replaceAll(" ", "").replaceAll("\t", ""), "Title:TestTitleMicroPatternName:TestNameTemplate:TestTemplateProblem:TestProblemSolution:TestSolution");
		tempFile.delete();
	}

}
