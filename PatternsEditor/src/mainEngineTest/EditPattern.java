package mainEngineTest;

import static org.junit.Assert.*;
import mainEngine.Pattern;
import mainEngine.TemplateFactory;

import org.junit.Test;

public class EditPattern {

	@Test
	public void test() {
		TemplateFactory factory = new TemplateFactory();
        Pattern pattern = factory.createTemplate("MicroPattern");
   
        for(int i = 0; i < pattern.getSize(); i++ ){
        	pattern.setContents("edited " + i, i);
        }
        
        assertEquals(pattern.toString(), "MicroPattern\nName:  edited 0\nTemplate:  edited 1\nProblem:  edited 2\nSolution:  edited 3\n");
	}

}
