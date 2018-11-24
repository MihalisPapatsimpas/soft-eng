package mainEngine;

public class PatternLanguage extends PatternComposite{

	@Override
	public void decorateComponents(LatexDecoratorFactory latexDecoratorFactory) {
		int length = getSize();
		for(int i = 0; i < length; i++){
			getComponentList().set(i , latexDecoratorFactory.createPatternDecorator(getChild(i)));
		}
	}

}
