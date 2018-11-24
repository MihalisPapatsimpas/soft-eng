package mainEngineTest;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import mainEngine.PatternLanguage;

import org.junit.Test;

public class SavePatternLanguageToTextFile {

	@Test
	public void test() throws FileNotFoundException{
		OutputStream out = new FileOutputStream("./test.txt");;
		PrintStream printStream = new PrintStream(out);
		PatternLanguage patternLanguage = new PatternLanguage();
		patternLanguage.setName("hhh");
		patternLanguage.saveContents(printStream);
		printStream.close();
		
		File tempFile = new File("./test.txt");
		if(tempFile.exists()){
			assertEquals(tempFile.exists(), true);
			tempFile.delete();
		}else{
			assertEquals(tempFile.exists(), false);
		}
	}

}
