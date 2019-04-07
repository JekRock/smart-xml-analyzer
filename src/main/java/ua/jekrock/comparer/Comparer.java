package ua.jekrock.comparer;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ua.jekrock.Tools;

import java.util.*;
import java.util.stream.Collectors;

public class Comparer implements AttributeComparer {
  private List<ComparedElement> matchedElements;
  private final Element originElement;
  private final Elements sampleElements;

  public Comparer(Element originElement, Elements sampleElements) {
    this.originElement = originElement;
    this.sampleElements = sampleElements;
    matchedElements = new ArrayList<>();
  }

  @Override
  public List<ComparedElement> getMatchedElements() {
    for (Element element : sampleElements) {
      matchedElements.add(compareElements(originElement, element));
    }
    matchedElements.sort(Comparator.comparingInt(ComparedElement::getMatchedAttributesCounter));
    return matchedElements.stream()
        .filter(
            comparedElement ->
                comparedElement.getMatchedAttributesCounter()
                    == matchedElements
                        .get(matchedElements.size() - 1)
                        .getMatchedAttributesCounter())
        .collect(Collectors.toList());
  }

  private ComparedElement compareElements(Element origin, Element sample) {
    final Map<String, String> originAttributes = Tools.getElementAttributes(origin);
    final Map<String, String> sampleAttributes = Tools.getElementAttributes(sample);

    ComparedElement comparedElement = new ComparedElement(sample);

    if (origin.text().equals(sample.text())) {
      comparedElement.incrementMatchedCounter();
      comparedElement.addMatchingReason(String.format("Matched text \"%s\"", sample.text()));
    }

    originAttributes.keySet().stream()
        .filter(sampleAttributes::containsKey)
        .forEach(
            key -> {
              final String originValue = originAttributes.get(key);
              final String sampleValue = sampleAttributes.get(key);

              if (originValue.equals(sampleValue)) {
                comparedElement.incrementMatchedCounter();
                comparedElement.addMatchingReason(
                    String.format("Matched attribute \"%s\" with value \"%s\"", key, sampleValue));
              }
            });
    return comparedElement;
  }
}
