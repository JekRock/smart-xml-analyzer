package ua.jekrock.comparer;

import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class ComparedElement {
  private final Element element;
  private int matchedAttributesCounter;
  private List<String> matchedReasons;

  public ComparedElement(Element element) {
    this.element = element;
    this.matchedAttributesCounter = 0;
    matchedReasons = new ArrayList<>();
  }

  public ComparedElement(Element element, int matchedAttributesCounter) {
    this.element = element;
    this.matchedAttributesCounter = matchedAttributesCounter;
    matchedReasons = new ArrayList<>();
  }

  public void incrementMatchedCounter() {
    matchedAttributesCounter++;
  }

  public Element getElement() {
    return element;
  }

  public int getMatchedAttributesCounter() {
    return matchedAttributesCounter;
  }

  public void addMatchingReason(String reason) {
    matchedReasons.add(reason);
  }

  public List<String> getMatchedReasons() {
    return matchedReasons;
  }
}
