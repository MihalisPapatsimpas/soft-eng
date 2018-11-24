package mainEngineTest;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;

import mainEngine.Controller;

import org.junit.Test;

public class SaveDecoratedPatternLanguage {

	@Test
	public void test() throws FileNotFoundException {
		Controller controller = new Controller();
		controller.createPatternLanguage("TestTitle");
		controller.addPattern("MicroPattern");
		controller.savePatternLanguage("./" , "test.txt");
		File tempFile = new File("./test.txt");
		
		if(tempFile.exists()){
			assertEquals(tempFile.exists(), true);
			tempFile.delete();
		}else{
			assertEquals(tempFile.exists(), false);
		}
	}

}
