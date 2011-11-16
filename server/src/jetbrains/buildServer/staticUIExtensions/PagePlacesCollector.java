package jetbrains.buildServer.staticUIExtensions;

import com.intellij.openapi.diagnostic.Logger;
import jetbrains.buildServer.web.openapi.PlaceId;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @author Eugene Petrenko (eugene.petrenko@gmail.com)
 *         Date: 16.11.11 18:14
 */
public class PagePlacesCollector {
  private static final Logger LOG = Logger.getInstance(PagePlacesCollector.class.getName());

  private Map<String, PlaceId> myPlaces;

  @NotNull
  private Map<String, PlaceId> listAllPagePlaces() {
    final Field[] fields = PlaceId.class.getDeclaredFields();
    Map<String, PlaceId> result = new TreeMap<String, PlaceId>();

    for (Field field : fields) {
      final int modifiers = field.getModifiers();
      if (Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers) && Modifier.isPublic(modifiers) && PlaceId.class.equals(field.getType())) {
        field.setAccessible(true);
        try {
          result.put(field.getName(), (PlaceId) field.get(null));
        } catch (IllegalAccessException e) {
          LOG.warn("Failed to read field of " + PlaceId.class);
        }
      }
    }
    return Collections.unmodifiableMap(result);
  }


  @NotNull
  public Map<String, PlaceId> getPlaceIds() {
    if (myPlaces == null) {
      myPlaces = listAllPagePlaces();
    }
    return myPlaces;
  }

  @Nullable
  public PlaceId findByName(@Nullable String placeId) {
    if (placeId == null) return null;
    placeId = placeId.trim();

    for (Map.Entry<String, PlaceId> e : getPlaceIds().entrySet()) {
      if (e.getKey().equalsIgnoreCase(placeId)) {
        return e.getValue();
      }
    }

    return null;
  }
}
