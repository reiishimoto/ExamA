package dev_support.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME) // 実行時にリフレクションで解析可能
public @interface TestMethod {
	String[] cases() default {"未テスト"};
}
