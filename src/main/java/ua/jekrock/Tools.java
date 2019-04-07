package ua.jekrock;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Tools {
  private static Logger LOGGER = LoggerFactory.getLogger(Tools.class);

  private static String CHARSET_NAME = "utf8";

  public static Element findElementById(Document document, String targetElementId) {
    return document.getElementById(targetElementId);
  }

  public static Elements findElementsByQuery(Document document, String cssQuery) {
    return document.select(cssQuery);
  }

  public static Elements findElementsByTag(Document document, String elementTag) {
    return document.body().getElementsByTag(elementTag);
  }

  public static String getPathToElement(Element element) {
    final StringBuilder pathToElement = new StringBuilder();

    element.parents().stream()
        .map(Tools::formatElementTagName)
        .collect(Collectors.toCollection(LinkedList::new))
        .descendingIterator()
        .forEachRemaining(tag -> pathToElement.append(String.format("%s > ", tag)));

    pathToElement.append(formatElementTagName(element));

    return pathToElement.toString();
  }

  public static String formatElementTagName(Element element) {
    return String.format("%s[%s] ", element.tagName(), element.elementSiblingIndex());
  }

  public static Map<String, String> getElementAttributes(Element element) {
    return element.attributes().asList().stream()
        .collect(Collectors.toMap(Attribute::getKey, Attribute::getValue));
  }

  public static Optional<Document> getDocumentFromFile(File htmlFile) {
    try {
      Document doc = Jsoup.parse(htmlFile, CHARSET_NAME, htmlFile.getAbsolutePath());
      return Optional.of(doc);

    } catch (IOException e) {
      LOGGER.error("Error reading [{}] file", htmlFile.getAbsolutePath(), e);
      return Optional.empty();
    }
  }
}
