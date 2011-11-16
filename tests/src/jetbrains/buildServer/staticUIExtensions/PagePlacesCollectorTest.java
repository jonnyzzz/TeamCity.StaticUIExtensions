package jetbrains.buildServer.staticUIExtensions;

import jetbrains.buildServer.BaseTestCase;
import jetbrains.buildServer.web.openapi.PlaceId;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Collection;

/**
 * @author Eugene Petrenko (eugene.petrenko@gmail.com)
 *         Date: 16.11.11 18:24
 */
public class PagePlacesCollectorTest extends BaseTestCase {
  @Test
  public void testListPagePlaces() {
    final Collection<PlaceId> placeIds = new PagePlacesCollector().getPlaceIds();
    Assert.assertTrue(placeIds.size() > 0);
    Assert.assertTrue(placeIds.contains(PlaceId.ALL_PAGES_FOOTER));
    Assert.assertTrue(placeIds.contains(PlaceId.ALL_PAGES_HEADER));
  }
}
