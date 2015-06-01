/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.util;

import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Helper methods for annotation
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public final class AnnotationHelper {
  /**
   * Private constructor.
   */
  private AnnotationHelper() {
  }

  /**
   * Searches for classes with annotated with {@code annotationClass}. It will instantiate all found classes with the
   * default empty constructor and cast them to {@code annotatedBaseClass}. Further on the annotation a method called
   * {@code field} is invoked. The returned object is used as key in the returned map where the instances are the
   * values.
   *
   * @param annotationClass    The annotation class to search for.
   * @param field              The name of the method to invoke on the annotation.
   * @param fieldClass         The class returned by invoking the method named {@code field} on the annotation.
   * @param annotatedBaseClass The base class of the annotated classes.
   * @param <A>                The annotation to search for.
   * @param <T>                The base class of the annotated classes.
   * @param <F>                The type returned by invoking the method named {@code field} on the annotation.
   * @return A map between the returned object of the annotation and an instance of the annotated class.
   * @throws IllegalAccessException
   * @throws InstantiationException
   * @throws NoSuchMethodException
   * @throws InvocationTargetException
   */
  public static <A extends Annotation, T, F> Map<F, T> searchAndInstantiate(Class<A> annotationClass, String field, Class<F> fieldClass, Class<T> annotatedBaseClass) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
    final Map<F, T> map = new LinkedHashMap<>();
    final Reflections reflections = new Reflections("");
    final Method fieldMethod = annotationClass.getMethod(field);

    for (Class<?> annotatedClass : reflections.getTypesAnnotatedWith(annotationClass)) {
      final T adapterInstance = annotatedBaseClass.cast(annotatedClass.newInstance());
      final Annotation entry = annotatedClass.getAnnotation(annotationClass);
      map.put(fieldClass.cast(fieldMethod.invoke(entry)), adapterInstance);
    }

    return map;
  }

  /**
   * Searches for classes with annotated with {@code annotationClass}. On the annotation a method called
   * {@code field} is invoked. The returned object is used as key in the returned map where the found annotated classes
   * are the values
   *
   * @param annotationClass    The annotation class to search for.
   * @param field              The name of the method to invoke on the annotation.
   * @param fieldClass         The class returned by invoking the method named {@code field} on the annotation.
   * @param annotatedBaseClass The base class of the annotated classes.
   * @param <A>                The annotation to search for.
   * @param <T>                The base class of the annotated classes.
   * @param <F>                The type returned by invoking the method named {@code field} on the annotation.
   * @return A map between the returned object of the annotation and the annotated class.
   * @throws IllegalAccessException
   * @throws InstantiationException
   * @throws NoSuchMethodException
   * @throws InvocationTargetException
   */
  @SuppressWarnings("unchecked")
  public static <A extends Annotation, T, F> Map<F, Class<? extends T>> search(Class<A> annotationClass, String field, Class<F> fieldClass, Class<T> annotatedBaseClass) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
    final Map<F, Class<? extends T>> map = new LinkedHashMap<>();
    final Reflections reflections = new Reflections("");
    final Method fieldMethod = annotationClass.getMethod(field);

    for (Class<?> annotatedClass : reflections.getTypesAnnotatedWith(annotationClass)) {
      final Annotation entry = annotatedClass.getAnnotation(annotationClass);
      map.put(fieldClass.cast(fieldMethod.invoke(entry)), (Class<? extends T>) annotatedClass);
    }

    return map;
  }
}
