package ua.jekrock;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.jekrock.comparer.ComparedElement;
import ua.jekrock.comparer.Comparer;

import java.io.File;
import java.util.List;

public class Main {
  private static Logger LOGGER = LoggerFactory.getLogger(Main.class);
  private static String originFilePath;
  private static String sampleFilePath;
  private static String originElementId;

  public static void main(String[] args) {
    try {
      originFilePath = args[0];
      sampleFilePath = args[1];
      originElementId = args[2];
      String analyzeResult = analyze();
      System.out.println(analyzeResult);
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
    }
  }

  private static String analyze() {
    final StringBuilder builder = new StringBuilder();
    final Document originDocument = Tools.getDocumentFromFile(new File(originFilePath)).get();
    final Document sampleDocument = Tools.getDocumentFromFile(new File(sampleFilePath)).get();

    final Element originElement = Tools.findElementById(originDocument, originElementId);

    final Elements sampleElementsByTag =
        Tools.findElementsByTag(sampleDocument, originElement.tagName());

    if (sampleElementsByTag.isEmpty()) {
      return "Can't find elements to compare in the sample document";
    }

    Comparer comparer = new Comparer(originElement, sampleElementsByTag);
    List<ComparedElement> matchedElements = comparer.getMatchedElements();

    // get only first matched element
    String pathToElement = Tools.getPathToElement(matchedElements.get(0).getElement());

    builder.append(pathToElement).append("\n\n").append("Decisions:\n");
    for (String s : matchedElements.get(0).getMatchedReasons()) {
      builder.append(s).append("\n");
    }
    return builder.toString();
  }
}
