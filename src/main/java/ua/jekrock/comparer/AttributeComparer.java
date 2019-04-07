package ua.jekrock.comparer;

import org.jsoup.nodes.Element;

import java.util.List;

public interface AttributeComparer {
  List<ComparedElement> getMatchedElements();
}
