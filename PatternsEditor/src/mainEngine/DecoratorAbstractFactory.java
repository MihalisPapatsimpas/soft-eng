package mainEngine;

public abstract interface DecoratorAbstractFactory {
	public abstract PatternComponent createPatternDecorator(PatternComponent patternComponent);
	public abstract PatternComponent createPartDecorator(PatternComponent patternComponent);
}
