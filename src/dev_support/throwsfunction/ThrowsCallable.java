package dev_support.throwsfunction;

public interface ThrowsCallable<T> {
	T call() throws Exception;
}
