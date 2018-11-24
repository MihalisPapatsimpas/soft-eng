package mainEngine;

public class Pattern extends PatternComposite{

	/*public Pattern clone(){
		return new Pattern();
	}*/
	
	public void decorateComponents(LatexDecoratorFactory latexDecoratorFactory){
		int length = getSize();
		for(int i = 0; i < length; i++){
			getComponentList().set(i, latexDecoratorFactory.createPartDecorator(getChild(i)));
		}
	}

	
}
