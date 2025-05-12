package dev_support.throwsfunction;

@FunctionalInterface
public interface ThrowsConsumer<T> {
	void accept(T t) throws Exception;
}
