package com.zer0bee.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.zer0bee.annotations.BindView;
import com.zer0bee.compiler.utils.ProcessingUtils;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

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
   * @param annotations It provides a list of annotations as elements that are contained in the Java
   * file
   * being processed.
   * @param roundEnv It provides access to the processing environment with utils to querying
   * elements. Two main functions we will use from this environment are: processingOver(A mean to
   * know if its the last round of processing) and getRootElements(It provides a list of elements
   * that will get processed. Some of these elements will contain the annotation that we are
   * interested.)
   */
  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    System.out.println("Custom processor started");
    if (!roundEnv.processingOver()) {
      // find all the classes that uses the supported annotations
      Set<TypeElement> typeElements = ProcessingUtils.getTypeElementsToProcess(
          roundEnv.getRootElements(),
          annotations);
      // for each such class create a wrapper class for binding
      for (TypeElement typeElement : typeElements) {
        String packageName = elementUtils.getPackageOf(typeElement).getQualifiedName().toString();
        String typeName = typeElement.getSimpleName().toString();

        ClassName className = ClassName.get(packageName, typeName);
        ClassName generatedClassName = ClassName
            .get(packageName, NameStore.getGeneratedClassName(typeName));

        // define the wrapper class
        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(generatedClassName)
            .addModifiers(Modifier.PUBLIC);

        // add constructor
        classBuilder.addMethod(MethodSpec.constructorBuilder()
            .addModifiers(Modifier.PUBLIC)
            .addParameter(className, NameStore.Variable.ANDROID_ACTIVITY)
            .addStatement("$N($N)",
                NameStore.Method.BIND_VIEWS,
                NameStore.Variable.ANDROID_ACTIVITY)
            .build());

        // add method that maps the views with id
        MethodSpec.Builder bindViewsMethodBuilder = MethodSpec
            .methodBuilder(NameStore.Method.BIND_VIEWS)
            .addModifiers(Modifier.PRIVATE)
            .returns(void.class)
            .addParameter(className, NameStore.Variable.ANDROID_ACTIVITY);

        for (VariableElement variableElement : ElementFilter.fieldsIn(
            typeElement.getEnclosedElements())) {
          BindView bindView = variableElement.getAnnotation(BindView.class);
          if (bindView != null) {
            bindViewsMethodBuilder.addStatement("$N.$N = ($T)$N.findViewById($L)",
                NameStore.Variable.ANDROID_ACTIVITY,
                variableElement.getSimpleName(),
                variableElement,
                NameStore.Variable.ANDROID_ACTIVITY,
                bindView.value());
          }
        }

        classBuilder.addMethod(bindViewsMethodBuilder.build());

        // write the defines class to a java file
        //It will generate the source code in the folder. /app/build/generated/source/apt/debug
        try {
          JavaFile.builder(packageName,
              classBuilder.build())
              .build()
              .writeTo(filer);
        } catch (IOException e) {
          messager.printMessage(Diagnostic.Kind.ERROR, e.toString(), typeElement);
        }
      }
    }
    return false;
  }

  @Override public Set<String> getSupportedAnnotationTypes() {
    return new TreeSet<>(Arrays.asList(
        BindView.class.getCanonicalName()
    ));
  }
}
