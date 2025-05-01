package dev_support.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// パラメータに付与するアノテーション
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME) // 実行時にリフレクションで解析可能
public @interface NoNull {
}
