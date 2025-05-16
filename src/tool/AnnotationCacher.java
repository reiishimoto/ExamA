package tool;

import java.lang.annotation.Annotation;

import dev_support.util.CacheManager;

public class AnnotationCacher<A extends Annotation> {
	private Class<A> annotationClass;
	private CacheManager<Class<?>, A> cache = new CacheManager<>(40, 0.75, true);

	public AnnotationCacher(Class<A> clazz) {
		this.annotationClass = clazz;
	}

	public A get(Class<?> clazz) {
		return cache.computeIfAbsent(clazz, v -> {
			A annotation = clazz.getDeclaredAnnotation(annotationClass);
			return annotation != null ? annotation : null;
		});
	}
}
