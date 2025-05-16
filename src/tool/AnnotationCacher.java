package tool;

import java.lang.annotation.Annotation;

import dev_support.util.CacheManager;

public class AnnotationCacher<A extends Annotation> {
	private final Class<A> annotationClass;
	private final CacheManager<Class<?>, A> cache;

	public AnnotationCacher(Class<A> clazz) {
		this.annotationClass = clazz;
		cache = new CacheManager<>(40, 0.75, true);
	}

	public A get(Class<?> clazz) {
		return cache.computeIfAbsent(clazz, v -> v.getDeclaredAnnotation(annotationClass));
	}
}
