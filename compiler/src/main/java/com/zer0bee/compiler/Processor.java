package com.zer0bee.compiler;

import com.zer0bee.annotations.BindView;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

public class Processor extends AbstractProcessor {

  private Filer filer;
  private Messager messager;
  private Elements elementUtils;

  @Override public synchronized void init(ProcessingEnvironment processingEnvironment) {
    super.init(processingEnvironment);
    filer = processingEnvironment.getFiler();
    messager = processingEnvironment.getMessager();
    elementUtils = processingEnvironment.getElementUtils();
  }

  /**
   * @param annotations It provides a list of annotations as elements that are contained in the Java file
   * being processed.
   * @param roundEnvironment It provides access to the processing environment with utils to querying
   * elements. Two main functions we will use from this environment are: processingOver(A mean to
   * know if its the last round of processing) and getRootElements(It provides a list of elements
   * that will get processed. Some of these elements will contain the annotation that we are
   * interested.)
   */
  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
    return false;
  }

  @Override public Set<String> getSupportedAnnotationTypes() {
    return new TreeSet<>(Arrays.asList(
        BindView.class.getCanonicalName()
    ));
  }
}
