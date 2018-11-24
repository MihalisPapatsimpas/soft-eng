package mainEngineTest;

import static org.junit.Assert.*;
import java.util.ArrayList;
import mainEngine.Pattern;
import mainEngine.PatternLanguage;
import mainEngine.TemplateFactory;

import org.junit.Test;

public class CreatePatternLanguage {

	@Test
	public void test() {
		PatternLanguage patternLanguage = new PatternLanguage();
        TemplateFactory factory = new TemplateFactory();
        ArrayList<Pattern> patterns = new ArrayList<Pattern>();
        String[] templates = {"MicroPattern", "Inductive-Mini-Pattern", "Deductive-Mini-Pattern", "Gang-of-four-Pattern", "System-of-Patterns-Template"};
        patternLanguage.setName("TestName");
           
        for(int i = 0; i < templates.length; i++){
            patterns.add(factory.createTemplate(templates[i])); //creates patterns
            patternLanguage.add(patterns.get(patterns.size()-1));    //add to pattern language
        }
      
       assertEquals(patternLanguage.getName(),"TestName");
       assertEquals(patternLanguage.getSize(), templates.length);
       assertEquals(patterns.get(0).toString(), "MicroPattern\nName:  \nTemplate:  \nProblem:  \nSolution:  \n");
       assertEquals(patterns.get(1).toString(), "Inductive-Mini-Pattern\nName:  \nTemplate:  \nContext:  \nForces:  \nSolution:  \n");
       assertEquals(patterns.get(2).toString(), "Deductive-Mini-Pattern\nName:   \nTemplate:   \nProblem:   \nSolution:   \nBenefits:   \nConsequences:   \n");
       assertEquals(patterns.get(3).toString(), "Gang-of-four-Pattern\nName:  \nTemplate:  \nPattern-Classification:  \nIntent:  \nAlso-Known-As:  \nMotivation:  \nApplicability:  \nStructure:  \nParticipants:  \nCollaborations:  \nConsequences:  \nImplementation:  \nSample-Code:  \nKnown-Uses:  \nRelated-Patterns:  \n");
       assertEquals(patterns.get(4).toString(), "System-of-Patterns-Template\nName:  \nTemplate:  \nAlso-Known-As:  \nExample:  \nContex:  \nProblem:  \nSolution:  \nStructure:  \nDynamics:  \nImplementation:  \nExample-Resolved:  \nVariants:  \nKnown-Uses:  \nConsequences:  \n");
	}

}
