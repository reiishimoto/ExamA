package dev_support.util;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

import dev_support.annotation.NoNull;

/**
 * `ExceptUtils` は例外処理を補助するユーティリティクラスです。
 *
 * このクラスは、主に以下の機能を提供します：
 * <ul>
 *   <li>ラムダ式の例外処理簡略化 (`exceptionHandle`)</li>
 *   <li>条件付き例外スロー (`throwIfCondition`)</li>
 *   <li>`null` チェックと例外スロー (`nullCheck`)</li>
 * </ul>
 *
 * <b>制限事項：</b>
 * <ul>
 *   <li>このクラスは例外処理の補助を目的としており、エラーロギング機能は提供しません。</li>
 *   <li>リフレクションを使用するため、セキュリティ設定によっては動作が制限される場合があります。</li>
 * </ul>
 */
public final class ExceptUtils {

	private ExceptUtils() {
		throw new IllegalStateException("ExceptUtilsクラスはインスタンス化ができません");
	}

	/**
	 * 受け取ったラムダ式を実行するメソッド。呼出元の throws 宣言を省略できるようにする。
	 * 例外が発生した場合は、`RuntimeException` にラップしてスローする。
	 *
	 * <b>使用例:</b><br>
	 * {@code
	 * String result = ExceptUtils.exceptionHandle(() -> {
	 *     return someDangerousOperation();
	 * });}
	 *
	 * @param process 実行するラムダ式（例: `() -> { return someOperation(); }`）
	 * @return ラムダ式の結果
	 */
	public static <T> T exceptionHandle(Callable<T> process) {
		try {
			return process.call();
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 条件に応じて例外をスローするメソッド。
	 * `condition` が `true` の場合、`exceptionSupplier` から例外を取得し、スローする。
	 *
	 * <b>使用例:</b><br>
	 * {@code
	 * ExceptUtils.throwIfCondition(age < 0, () ->
	 *     new IllegalArgumentException("引数 age には負の値は渡せません"));
	 * }
	 *
	 * @param condition 例外をスローするかどうかの条件
	 * @param exceptionSupplier 例外の供給元
	 */
	public static void throwIfCondition(boolean condition, Supplier<? extends Exception> exceptionSupplier) {
		if (condition) {
			Throwable ex = exceptionSupplier.get();

			if (ex instanceof RuntimeException) {
				throw (RuntimeException) ex;
			} else {
				throw new RuntimeException(ex);
			}
		}
	}

	/**
	 * メソッドの引数に `null` が含まれていないかチェックする。
	 * `@NoNull` が付いている引数に `null` が渡された場合は `IllegalArgumentException` をスローする。<br>
	 *
	 * <b>使用例:</b><br>
	 * public void method(@NoNull String name, String email, int age) &#123;<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;ExceptUtils.nullCheck(name, email, age);<br>
	 * &#125;
	 *
	 * @param args チェック対象メソッドの全引数
	 * @throws RuntimeException `@NoNull` アノテーション付きの変数が `null` だった場合、IllagalArgumentExceptionをスロー
	 */
	public static void nullCheck(Object... args) throws RuntimeException {
		boolean hasNull = false;
		for (Object arg : args) {
			if (arg == null) {
				hasNull = true;
				break;
			}
		}
		if (!hasNull) return;

		StackTraceElement caller = Thread.currentThread().getStackTrace()[2];
		String methodName = caller.getMethodName();
		String className = caller.getClassName();

		Class<?> clazz;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		Method method = findMethod(clazz, methodName, args);

		Parameter[] parameters = method.getParameters();
		for (int i = 0; i < parameters.length; i++) {
			if (parameters[i].isAnnotationPresent(NoNull.class) && args[i] == null) {
				throw new IllegalArgumentException(
					className + " のメソッド `" + methodName + "` に渡された `第" + (i + 1) + "引数 (" + parameters[i].getType().getSimpleName() + ")` の値が null です！"
				);
			}
		}
	}


	/**
	 * 指定されたクラスのメソッドを検索する。
	 * @param clazz クラス情報
	 * @param methodName 検索するメソッド名
	 * @param args メソッドの引数（個数と型をチェックするために利用）
	 * @return 見つかったメソッド
	 * @throws RuntimeException メソッドが存在しない場合
	 */
	private static Method findMethod(Class<?> clazz, String methodName, Object... args) {
		for (Method method : clazz.getDeclaredMethods()) {
			if (method.getName().equals(methodName) && method.getParameterCount() == args.length) {
				return method;
			}
		}
		throw new RuntimeException("メソッド `" + methodName + "` が見つかりません。");
	}
}
