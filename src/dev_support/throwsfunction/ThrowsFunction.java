package dev_support.throwsfunction;

@FunctionalInterface
public interface ThrowsFunction<T, R> {
	R apply(T t) throws Exception;
}
