package jetbrains.buildServer.staticUIExtensions;

import jetbrains.buildServer.staticUIExtensions.model.*;
import jetbrains.buildServer.util.FileUtil;
import jetbrains.buildServer.util.StringUtil;
import jetbrains.buildServer.web.openapi.PlaceId;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Eugene Petrenko (eugene.petrenko@gmail.com)
 *         Date: 16.11.11 18:50
 */
public class ConfigurationReader {
  private final PagePlacesCollector myCollector;

  public ConfigurationReader(@NotNull final PagePlacesCollector collector) {
    myCollector = collector;
  }

  @NotNull
  public Collection<Rule> parseConfiguration(@NotNull final File config) throws ConfigurationException {
    try {
      final Element root = FileUtil.parseDocument(config);
      return processXml(root);
    } catch (JDOMException e) {
      throw new ConfigurationException("Failed to parse configuration file: " + e.getMessage(), e);
    } catch (IOException e) {
      throw new ConfigurationException("Failed to read configuration file: " + e.getMessage(), e);
    }
  }

  @NotNull
  private Collection<Rule> processXml(@NotNull final Element root) throws ConfigurationException {
    final List rules = root.getChildren("rule");
    final Collection<Rule> result = new ArrayList<Rule>();
    for (Object rule : rules) {
      final Element xmlRule = (Element) rule;

      final String html = xmlRule.getAttributeValue("html-file");
      final String js = xmlRule.getAttributeValue("js-file");
      final String css = xmlRule.getAttributeValue("css-file");

      final StaticContent content = new StaticContent(html, js, css);
      if (!content.isValid()) {
        throw new ConfigurationException("Rule does not contain any file to include. " + xmlRule.toString());
      }

      final String placeId = xmlRule.getAttributeValue("place-id");

      final PlaceId place = myCollector.findByName(placeId);
      if (placeId == null) {
        throw new ConfigurationException("Rule contains unparseable place-id: " + placeId);
      }

      final List<UrlMatcher> matchers = new ArrayList<UrlMatcher>();
      for (Object url : xmlRule.getChildren("url")) {
        final Element xmlUrl = (Element) url;
        final String startsWith = xmlUrl.getAttributeValue("startsWith");
        if (StringUtil.isEmptyOrSpaces(startsWith)) {
          throw new ConfigurationException("Unknown url matcher: " + xmlUrl);
        }

        matchers.add(new StartsWithMatcher(startsWith.trim()));
      }

      result.add(new Rule(new OrMatcher(matchers), place, content));
    }
    return result;
  }
}
