package mainEngine;

public class LatexDecoratorFactory implements DecoratorAbstractFactory {

	public PatternComponent createPatternDecorator(PatternComponent patternComponent) {
		return new Decorator("\\section{","}", patternComponent);
	}

	public PatternComponent createPartDecorator(PatternComponent patternComponent){
		return new Decorator("\\subsection{","}", patternComponent);
	}

}
