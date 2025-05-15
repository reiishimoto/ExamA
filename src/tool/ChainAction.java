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
 * <li>このアクションがチェーン内のどの位置にあるか (ChainLocate)</li>
 * <li>ルートでない場合、どのルートから繫がるか (rootClass)</li>
 * <li>不正な遷移が発生した場合のリダイレクト先 (redirectFor)</li>
 * </ul>
 *
 * <p>これらの情報は {@link FrontController#chainProcessing(HttpSession, Action)} によって評価され、<br>
 * 以下のように状態遷移が行われます：</p>
 * <ul>
 * <li>{@link ChainLocate#ROOT} の場合、{@link TempStrage} インスタンスを生成し、セッションに保存</li>
 * <li>{@link ChainLocate#MIDDLE} の場合、既存の {@link TempStrage} の整合性チェックを実施</li>
 * <li>{@link ChainLocate#END} の場合、処理完了後に {@link TempStrage} を破棄</li>
 * </ul>
 *
 * <p>ルートでない場合、以下のいずれかの条件が成立すると「不正な状態」と判断され、<br>
 * redirectFor に設定されたパスへリダイレクトされます。</p>
 * <ul>
 * <li>{@link TempStrage} が null である（セッションにデータが存在しない）</li>
 * <li>rootClass と {@link TempStrage#rootClass} が一致しない</li>
 * </ul>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ChainAction {
    public static final String KEY = "onetime_structure_key";

    /**
     * このアクションの位置を指定 default: {@link ChainLocate#MIDDLE}<br>
     * <ul>
     * <li>{@link ChainLocate#ROOT} - チェーンの開始点（TempStrage を生成）</li>
     * <li>{@link ChainLocate#MIDDLE} - チェーンの途中（整合性チェック）</li>
     * <li>{@link ChainLocate#END} - チェーンの終端（TempStrage を破棄）</li>
     * </ul>
     */
    ChainLocate locate() default ChainLocate.MIDDLE;

    /**
     * ルートでない場合、どのルートクラスから遷移するか default: {@link Action}<br>
     * TempStrage に保存されたルートクラスと一致する必要がある<br>
     * 一致しない場合は、{@link #redirectFor} に指定されたパスへリダイレクトされる
     */
    Class<? extends Action> rootClass() default Action.class;

    /**
     * 不正な遷移時のリダイレクト先 default: `"Menu.action"`<br>
     * {@link #rootClass} の整合性が取れない場合、このパスへリダイレクトされる
     */
    String redirectFor() default "Menu.action";
}
