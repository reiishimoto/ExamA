package dev_support.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev_support.annotation.NoNull;
import dev_support.annotation.Nullable;
import dev_support.annotation.TestMethod;

/**
 * Testerクラスは、対象インスタンスの@TestMethodが付与されたpublicメソッドに対して、
 * ランダムな引数を生成しテスト実行を行うためのユーティリティクラスです。
 * <p>
 * 各テストメソッドについて、事前に登録されたテストケースからランダムに引数を生成し、
 * 生成された引数でリフレクションを用いてメソッドを実行します。
 * </p>
 * <p>
 * ※ 注意: 型に応じた引数のテストケースが設定されていない場合、対象メソッドのテストはスキップされます。
 * </p>
 */
public class Tester {

  /** クラス毎のテスト用引数リストを保持するマップ */
  private final Map<Class<?>, Object[]> argumentsMap = new HashMap<>();

  /**
   * 各Parameterに対して推奨されるテストケースをキャッシュするマネージャ。
   * キャッシュサイズは最大40件に制限。
   */
  private final CacheManager<Parameter, Object[]> suggestedCase = new CacheManager<>(40);

  /** コンストラクタ */
  public Tester() {}

  /**
   * 指定されたインスタンス内の全てのpublicメソッドのうち、@TestMethodが付与されたものを検出し、
   * 各メソッドに対して指定回数分テストを実行します。
   * <p>
   * 実行プロセスは以下の通りです：
   * </p>
   * <ol>
   *   <li>テスト対象のメソッドごとに、メソッドのパラメータに対して登録されたテストケースからランダムな引数を生成</li>
   *   <li>生成された引数を用いてリフレクションによりメソッドを実行</li>
   *   <li>メソッド実行中に例外が発生した場合は捕捉し、{@link ExceptInfo} オブジェクトとして記録</li>
   *   <li>パラメータ <code>breakIfExcept</code> が true の場合、例外発生時にそのメソッドの以降のテストを中断</li>
   * </ol>
   *
   * @param testInstance テスト対象のインスタンス。@TestMethodが付与されたメソッドを含むもの。
   * @param testTimes 各テストメソッド毎に実行するテスト回数。例: 5 を指定すれば、各メソッドが5回実行される。
   * @param breakIfExcept 例外が発生した場合、以降の同一メソッドのテスト実行を中断するかを示すフラグ。
   * @param <T> テスト対象インスタンスの型
   * @return テスト実行中に捕捉された例外情報のリスト。例外が一度も発生しなかった場合は空のリストを返す。
   */
  public <T> List<ExceptInfo> startTest(T testInstance, int testTimes, boolean breakIfExcept) {
    List<ExceptInfo> exceptions = new ArrayList<>();

    // 対象インスタンスの全てのpublicメソッドを走査
    for (Method method : testInstance.getClass().getMethods()) {
      // @TestMethodが付与されているメソッドのみテスト対象とする
      if (!method.isAnnotationPresent(TestMethod.class)) continue;

      System.out.println("テスト開始: " + method.getName());
      for (int i = 0; i < testTimes; i++) {
        Object[] arguments = getRandomArguments(method.getParameters());

        // 引数のテストケースが不足している場合は、そのメソッドのテストをスキップ
        if (arguments == null) {
          break;
        }

        try {
          // 指定された引数を用いて対象メソッドを実行
          method.invoke(testInstance, (Object[]) arguments);
        } catch (InvocationTargetException e) {
          // InvocationTargetExceptionの場合、元の例外を取得して記録する
          Throwable realException = e.getCause();
          exceptions.add(new ExceptInfo(realException, arguments, method.getName()));
          if (breakIfExcept) break;
        } catch (Exception e) {
          // その他の例外も記録する
          exceptions.add(new ExceptInfo(e, arguments, method.getName()));
          if (breakIfExcept) break;
        }
      }
    }
    return exceptions;
  }

  /**
   * 指定されたパラメータ配列に対して、登録済みのテストケースからランダムな引数を生成し、
   * その引数の配列を返します。
   * <p>
   * 各パラメータへは個別に {@link #getRandomArgument(Parameter)} が適用されます。
   * </p>
   * <p>
   * なお、1つでも引数のテストケースが用意されていない場合は、nullを返しそのメソッドのテストをスキップします。
   * </p>
   *
   * @param parameters テスト対象メソッドのパラメータ情報の配列
   * @return 各パラメータに対応するランダムな引数の配列。テストケース不足があればnullを返す。
   */
  private Object[] getRandomArguments(Parameter[] parameters) {
    Object[] args = new Object[parameters.length];
    List<String> missingTypes = new ArrayList<>();

    for (int i = 0; i < parameters.length; i++) {
      Object argument = getRandomArgument(parameters[i]);
      if (argument == TuneCases.NO_CASE) {
        missingTypes.add(parameters[i].getType().getSimpleName()); // 未登録の型を記録
      }
      args[i] = argument;
    }

    // ✅ テストケース不足時に、未登録の型一覧を警告として表示
    if (!missingTypes.isEmpty()) {
      System.err.println("警告: 以下の型に対するテストケースが設定されていません → " + String.join(", ", missingTypes));
      return null;
    }

    return args;
  }

  /**
   * 指定されたパラメータに対して、登録されたテストケースからランダムに選択した引数値を返します。
   * <p>
   * 処理の流れは以下の通りです：
   * </p>
   * <ol>
   *   <li>キャッシュ(suggestedCase)から該当パラメータのテストケース候補を取得。</li>
   *   <li>キャッシュに候補が存在しない場合、パラメータの型に対応するテストケースをargumentsMapから取得。</li>
   *   <li>パラメータに付与されたアノテーション（@Nullable、@NoNull）に応じて、候補テストケースをフィルタリング。</li>
   *   <li>フィルタリングされたテストケース候補をキャッシュに保存し、ランダムに1件選択して返す。</li>
   * </ol>
   * <p>
   * 対応するテストケースが存在しない場合は、特別な値 {@code TuneCases.NO_CASE} を返します。
   * </p>
   *
   * @param parameter テスト対象のパラメータ
   * @return ランダムに選ばれたテストケースの値、またはテストケースが存在しない場合は {@code TuneCases.NO_CASE}
   */
  private Object getRandomArgument(Parameter parameter) {
    Object[] argSuggest = suggestedCase.retrieve(parameter);

    if (argSuggest == null) {
      TuneCases cases = TuneCases.DEFAULT;

      // @Nullableが付与されている場合、nullを含むテストケースを利用可能に
      if (parameter.isAnnotationPresent(Nullable.class)) {
        cases = TuneCases.NULLABLE;
      }

      // @NoNullが付与されている場合、nullを除外するテストケースを利用
      if (parameter.isAnnotationPresent(NoNull.class)) {
        cases = cases == TuneCases.NULLABLE ? TuneCases.DEFAULT : TuneCases.NO_NULL;
      }

      argSuggest = argumentsMap.get(parameter.getType());
      if (argSuggest == null) return TuneCases.NO_CASE;

      // フィルタケースに従い、テストケース候補を調整
      switch (cases) {
        case NO_NULL:
          argSuggest = Arrays.stream(argSuggest).filter(v -> v != null).toArray();
          break;
        case NULLABLE:
          List<Object> list = new ArrayList<>(Arrays.asList(argSuggest));
          if (!list.contains(null)) list.add(null);
          argSuggest = list.toArray();
          break;
        default:
          break;
      }
      // 調整後の候補をキャッシュに保存
      suggestedCase.put(parameter, argSuggest);
    }
    // 候補からランダムに1件選択して返す
    return argSuggest[(int) (Math.random() * argSuggest.length)];
  }

  /**
   * 指定したクラスに対するテストケース（引数候補）をリスト形式で登録します。
   * <p>
   * 登録されたテストケースは、テスト時に対象型の引数候補として利用され、
   * 引数生成時にランダムに選ばれます。
   * </p>
   *
   * @param clazz 対象の引数クラス（例: String.class, Integer.class など）
   * @param cases 使用するテストケースが格納されたリスト。リスト内の各要素は {@code clazz} のインスタンスである必要があります。
   * @param <C> 引数のクラス型
   */
  public <C> void setTestArguments(Class<C> clazz, List<C> cases) {
    argumentsMap.put(clazz, cases.toArray());
  }

  /**
   * 指定したクラスに対するテストケース（引数候補）を配列形式で登録します。
   * <p>
   * 登録されたテストケースは、テスト時に対象型の引数候補として利用され、
   * 引数生成時にランダムに選ばれます。
   * </p>
   *
   * @param clazz 対象の引数クラス（例: String.class, Integer.class など）
   * @param cases 使用するテストケースが格納された配列。各要素は {@code clazz} のインスタンスである必要があります。
   * @param <C> 引数のクラス型
   */
  public <C> void setTestArguments(Class<C> clazz, C... cases) {
    argumentsMap.put(clazz, cases);
  }

  /** テストケースフィルタリング用の調整タイプを定義する内部列挙型 */
  private static enum TuneCases {
    DEFAULT, NO_NULL, NULLABLE, NO_CASE;
  }

  /**
   * テスト実行中に発生した例外情報を保持するための内部クラスです。
   * <p>
   * 当該クラスは、発生した例外オブジェクト、実行時に使用された引数、
   * 及び例外が発生したテストメソッド名を記録します。
   * </p>
   */
  public static class ExceptInfo {
    private final Throwable exception;
    private final Object[] arguments;
    private final String methodName;

    /**
     * ExceptInfoのインスタンスを生成します。
     *
     * @param exception 発生した例外（通常は、メソッド実行中にキャッチされた例外の原因となる{@code Throwable}）
     * @param arguments テスト実行時に使用された引数の配列
     * @param methodName 例外が発生したテストメソッドの名前
     */
    ExceptInfo(Throwable exception, Object[] arguments, String methodName) {
      this.exception = exception;
      this.arguments = arguments;
      this.methodName = methodName;
    }

    @Override
    public String toString() {
      return "例外発生: " + exception.getClass().getSimpleName() + "\t" +
             "メッセージ: " + exception.getMessage() + "\n" +
             "メソッド: " + methodName + "\t引数: " + Arrays.toString(arguments) + "\n";
    }
  }
}
