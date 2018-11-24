package mainEngineTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CreatePatternLanguage.class, EditPattern.class,
		RemovePatter.class, SavePatternLanguageToTextFile.class, LoadFromTextFile.class,
		SaveDecoratedPatternLanguage.class, LoadDecoratedPatternLanguage.class })
public class AllTests {

}
