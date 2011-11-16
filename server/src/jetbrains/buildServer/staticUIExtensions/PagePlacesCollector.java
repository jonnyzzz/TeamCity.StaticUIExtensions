package jetbrains.buildServer.staticUIExtensions;

import com.intellij.openapi.diagnostic.Logger;
import jetbrains.buildServer.web.openapi.PlaceId;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Eugene Petrenko (eugene.petrenko@gmail.com)
 *         Date: 16.11.11 18:14
 */
public class PagePlacesCollector {
  private static final Logger LOG = Logger.getInstance(PagePlacesCollector.class.getName());

  private Collection<PlaceId> myPlaces;

  @NotNull
  private Collection<PlaceId> listAllPagePlaces() {
    final Field[] fields = PlaceId.class.getDeclaredFields();
    List<PlaceId> result = new ArrayList<PlaceId>();

    for (Field field : fields) {
      final int modifiers = field.getModifiers();
      if (Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers) && Modifier.isPublic(modifiers) && PlaceId.class.equals(field.getType())) {
        field.setAccessible(true);
        try {
          result.add((PlaceId)field.get(null));
        } catch (IllegalAccessException e) {
          LOG.warn("Failed to read field of " + PlaceId.class);
        }
      }
    }
    return Collections.unmodifiableCollection(result);
  }


  @NotNull
  public Collection<PlaceId> getPlaceIds() {
    if (myPlaces == null) {
      myPlaces = listAllPagePlaces();
    }
    return myPlaces;
  }


}
