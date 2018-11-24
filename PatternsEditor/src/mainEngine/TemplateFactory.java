package mainEngine;

//import java.util.HashMap;

public class TemplateFactory {
	
	public Pattern createTemplate(String templateName) {
		if (templateName.equals("MicroPattern")) {
			return createMicroPattern();
		} else if (templateName.equals("Inductive-Mini-Pattern")) {
			return createInductiveMiniPattern();
		} else if (templateName.equals("Deductive-Mini-Pattern")) {
			return createDeductiveMiniPattern();
		} else if (templateName.equals("Gang-of-four-Pattern")) {
			return createGangOfFourPattern();
		} else if (templateName.equals("System-of-Patterns-Template")) {
			return createSystemOfPatterns();
		} else {
			throw new IllegalArgumentException("Unsupported template: " + templateName);
		}
	}

	private Pattern createSystemOfPatterns() {
		Pattern system = new Pattern();
		system.setName("System-of-Patterns-Template");
		system.add(new PatternPart("Name: ", ""));
		system.add(new PatternPart("Template: ", ""));
		system.add(new PatternPart("Also-Known-As: ", ""));
		system.add(new PatternPart("Example: ", ""));
		system.add(new PatternPart("Contex: ", ""));
		system.add(new PatternPart("Problem: ", ""));
		system.add(new PatternPart("Solution: ", ""));
		system.add(new PatternPart("Structure: ", ""));
		system.add(new PatternPart("Dynamics: ", ""));
		system.add(new PatternPart("Implementation: ", ""));
		system.add(new PatternPart("Example-Resolved: ", ""));
		system.add(new PatternPart("Variants: ", ""));
		system.add(new PatternPart("Known-Uses: ", ""));
		system.add(new PatternPart("Consequences: ", ""));
		return system;
	}

	private Pattern createGangOfFourPattern() {
		Pattern gang = new Pattern();
		gang.setName("Gang-of-four-Pattern");
		gang.add(new PatternPart("Name: ", ""));
		gang.add(new PatternPart("Template: ", ""));
		gang.add(new PatternPart("Pattern-Classification: ", ""));
		gang.add(new PatternPart("Intent: ", ""));
		gang.add(new PatternPart("Also-Known-As: ", ""));
		gang.add(new PatternPart("Motivation: ", ""));
		gang.add(new PatternPart("Applicability: ", ""));
		gang.add(new PatternPart("Structure: ", ""));
		gang.add(new PatternPart("Participants: ", ""));
		gang.add(new PatternPart("Collaborations: ", ""));
		gang.add(new PatternPart("Consequences: ", ""));
		gang.add(new PatternPart("Implementation: ", ""));
		gang.add(new PatternPart("Sample-Code: ", ""));
		gang.add(new PatternPart("Known-Uses: ", ""));
		gang.add(new PatternPart("Related-Patterns: ", ""));
		return gang;
	}
	
	private Pattern createDeductiveMiniPattern() {
		Pattern deductiveMini = new Pattern();
		deductiveMini.setName("Deductive-Mini-Pattern");
		deductiveMini.add(new PatternPart("Name:  ", ""));
		deductiveMini.add(new PatternPart("Template:  ", ""));
		deductiveMini.add(new PatternPart("Problem:  ", ""));
		deductiveMini.add(new PatternPart("Solution:  ", ""));
		deductiveMini.add(new PatternPart("Benefits:  ", ""));
		deductiveMini.add(new PatternPart("Consequences:  ", ""));
		return deductiveMini;
	}

	private Pattern createInductiveMiniPattern() {
		Pattern mini = new Pattern();
		mini.setName("Inductive-Mini-Pattern");
		mini.add(new PatternPart("Name: ", ""));
		mini.add(new PatternPart("Template: ", ""));
		mini.add(new PatternPart("Context: ", ""));
		mini.add(new PatternPart("Forces: ", ""));
		mini.add(new PatternPart ("Solution: ", ""));
		return mini;
	}

	private Pattern createMicroPattern() {
		Pattern micro = new Pattern();
		micro.setName("MicroPattern");
		micro.add(new PatternPart("Name: ", ""));
		micro.add(new PatternPart("Template: ", ""));
		micro.add(new PatternPart("Problem: ", ""));
		micro.add(new PatternPart("Solution: ", ""));
		return micro;
	}
	/*
	private HashMap<String, Pattern> templates = new HashMap<String, Pattern>();
	
	public TemplateFactory(){
		Pattern micro = new Pattern();
		micro.add(new PatternPart("Name: ", ""));
		micro.add(new PatternPart("Template: ", ""));
		micro.add(new PatternPart("Problem: ", ""));
		micro.add(new PatternPart("Solution: ", ""));
		templates.put("MicroPattern", micro);
	}
	
	public Pattern createTemplate(String templateName){
		return templates.get(templateName).clone();
	}*/
}
