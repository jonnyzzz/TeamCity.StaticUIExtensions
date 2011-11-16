package jetbrains.buildServer.staticUIExtensions;

import jetbrains.buildServer.BaseTestCase;
import jetbrains.buildServer.staticUIExtensions.model.Rule;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collection;

/**
 * @author Eugene Petrenko (eugene.petrenko@gmail.com)
 *         Date: 16.11.11 19:17
 */
public class ConfigurationReaderTest extends BaseTestCase {
  private PagePlacesCollector myCollector;
  private ConfigurationReader myReader;

  @BeforeMethod
  @Override
  protected void setUp() throws Exception {
    super.setUp();
    myCollector = new PagePlacesCollector();
    myReader = new ConfigurationReader(myCollector);
  }

  @Test
  public void test_read_configuration_1() throws ConfigurationException {
    final Collection<Rule> rules = myReader.parseConfiguration(Paths.getConfigsFile("config_01.xml"));
    Assert.assertEquals(rules.size(), 4);

    System.out.println(rules);
  }
}
