package mainEngineTest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import mainEngine.Pattern;
import mainEngine.PatternLanguage;
import mainEngine.TemplateFactory;

import org.junit.Test;

public class RemovePatter {

	@Test
	public void test() {
		PatternLanguage patternLanguage = new PatternLanguage();
        TemplateFactory factory = new TemplateFactory();
        ArrayList<Pattern> patterns = new ArrayList<Pattern>();
        String[] templates = {"MicroPattern", "Inductive Mini-Pattern", "Deductive Mini-Pattern", "Gang-of-four Pattern", "System of Patterns Template"};
        patternLanguage.setName("TestName");
        
        patterns.add(factory.createTemplate(templates[0]));//creates patterns
        patternLanguage.add(patterns.get(patterns.size()-1));//add to pattern language
        patterns.add(factory.createTemplate(templates[0]));
        patternLanguage.add(patterns.get(patterns.size()-1));
        patterns.add(factory.createTemplate(templates[0]));
        patternLanguage.add(patterns.get(patterns.size()-1));
   
        patterns.remove(0);
        patternLanguage.remove(0);
        patterns.remove(0);
        patternLanguage.remove(0);
        patterns.remove(0);
        patternLanguage.remove(0);
     
    
        
        assertEquals(patternLanguage.getSize(), 0);
        
	}

}
