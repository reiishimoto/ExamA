package tool;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.servlet.http.HttpSession;

/**
 * <p>ChainActionアノテーションは、セッションによって値の受け渡しをする{@link TempStrage}および、<br>
 * {@link FrontController}の管理をサポートするカスタムアノテーションです</p>
 *
 * <p>このアノテーションは以下の情報を設定できます</p>
 * <ul>
 * <li>当アクションがルートであるか(isRoot)
 * <li>ルートでない場合、どのルートから繫がるか(rootClass)</li>
 * <li>不正な遷移が発生した場合のリダイレクト先(redirectFor)</li>
 * <li>当アクションがチェインの終端であるか</li>
 * </ul>
 *
 * <p>これらの情報は{@link FrontController#chainProcessing(HttpSession, Action)}によって評価され、
 * rootの場合は{@link TempStrage}インスタンスを生成、endの場合は破棄します</p>
 * rootでない場合、以下のいずれかの条件が成立すると「不正な状態」と判断され、
 * redirectForに設定されたパスへリダイレクトされます。<br>
 * 1.TempStrageがnullである（セッションにデータが存在しない）<br>
 * 2.rootClassと{@link TempStrage#rootClass}が一致しない
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ChainAction {
	public static final String KEY = "onetime_structure_key";

	/**
	 * このアクションがルートであるか default: false<br>
	 * trueの場合にはTempStrageインスタンスを生成し、セッションに保存<br>
	 * falseの場合はTempStrageの整合性の確認が行われる
	 */
	boolean isRoot() default false;

	/**
	 * ルートでない場合、どのルートクラスから遷移するか default: Action.class<br>
	 * TempStrage に保存されたルートクラスと一致する必要がある<br>
	 * 一致しない場合は、redirectFor で指定されたパスへリダイレクトされる
	 */
	Class<? extends Action> rootClass() default Action.class;

	/**
	 * 不正な遷移時のリダイレクト先 default: "Menu.action"<br>
	 * rootClass の整合性が取れない場合、このパスへリダイレクトされる
	 */
	String redirectFor() default "Menu.action";

	/**
	 * チェーンの終端であるか default: false<br>
	 * trueの場合、処理完了後にTempStrageをセッションから削除し、チェインを終了する
	 */
	boolean isEnd() default false;
}
