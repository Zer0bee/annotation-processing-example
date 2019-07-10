package com.zer0bee.compiler.utils;

import java.util.HashSet;
import java.util.Set;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

public class ProcessingUtils {
  private ProcessingUtils() {
  }

  public static Set<TypeElement> getTypeElementsToProcess(Set<? extends Element> elements,
      Set<? extends Element> supportedAnnotations) {
    Set<TypeElement> typeElements = new HashSet<>();
    for (Element element : elements) {
      if (element instanceof TypeElement) {
        boolean found = false;
        /*
         * Enclosed elements are the elements that are contained in the given element. In our case the element will be Activity (TypeElement) and the Enclosed elemetents will be tvContent, onCreate, bt1Click, bt2Click and other inherited members.
         */
        for (Element subElement : element.getEnclosedElements()) {
          /*
           * AnnotationMirror will provide all the annotations used on the subElement. Example: @Override for onCreate, @BindView for tvContent and @OnClick for the bt1Click.
           */
          for (AnnotationMirror mirror : subElement.getAnnotationMirrors()) {
            for (Element annotation : supportedAnnotations) {
              if (mirror.getAnnotationType().asElement().equals(annotation)) {
                typeElements.add((TypeElement) element);
                found = true;
                break;
              }
            }
            if (found) break;
          }
          if (found) break;
        }
      }
    }
    return typeElements;
  }
}
