package tool;

import java.sql.SQLException;

public class ExceptionHandler {
    public static void handleException(Exception e) {
        // `RuntimeException` の内部 `Exception` を取得
        if (e instanceof RuntimeException && e.getCause() instanceof Exception) {
            e = (Exception) e.getCause(); // ラップされた例外を展開
        }

    	System.err.println("=========================================================================");
        System.err.println("例外: 処理中に例外が発生しました: " + e.getClass().getSimpleName());

        if (e instanceof NullPointerException) {
            System.err.println("例外: Null値の変数が使用されました。対象の変数を確認してください。");
            printStackTraceLimited(e, 2); // 最初の 2 行のみ表示

        } else if (e instanceof ClassNotFoundException) {
            System.err.println("例外: クラスが見つかりません。クラスパスの設定を確認してください。");
            System.err.print("例外: 見つからなかったクラス → ");
            System.out.println(e.getMessage());
            return; // **スタックトレースは不要なので処理終了**

        } else if (e instanceof IllegalArgumentException) {
            System.err.println("例外: 不適切な引数が渡されました。引数を確認してください。");
            System.err.println("例外: 問題のある引数 → " + e.getMessage());
            printStackTraceLimited(e, 5); // 影響範囲を見やすく

        } else if (e instanceof SQLException) {
            SQLException sqlEx = (SQLException) e;
            while (sqlEx.getNextException() != null) {
                sqlEx = sqlEx.getNextException();
            }
            System.err.println("例外: SQLの実行中に問題が発生しました。クエリの構文や接続状態を確認してください。");
            System.err.println("例外: 最終的な SQL エラー → " + sqlEx.getMessage());
            System.err.println("例外: SQLState → " + sqlEx.getSQLState());
            printFilteredStackTrace(e, "Dao", 3); // **DAOのみ表示**
        } else {
            System.err.println("例外: 予期しない例外が発生しました。詳細を確認してください。");
//            printStackTraceLimited(e, 5); // 通常の例外処理
            e.printStackTrace();
        }
    }

    // **限定的なスタックトレースを表示するメソッド**
    private static void printStackTraceLimited(Exception e, int limit) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        System.err.println("例外: エラー発生箇所:");
        for (int i = 0; i < stackTrace.length && i < limit; i++) {
            System.err.println("例外: " + stackTrace[i].getClassName() +
                               "." + stackTrace[i].getMethodName() +
                               " (" + stackTrace[i].getFileName() + ":" + stackTrace[i].getLineNumber() + ")");
        }
    }

    // **特定クラス（例: DAO）のみ表示するメソッド**
    private static void printFilteredStackTrace(Exception e, String keyword, int range) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        int keywordIndex = -1;

        // 指定キーワードの位置を検索
        for (int i = 0; i < stackTrace.length; i++) {
            if (stackTrace[i].getClassName().contains(keyword)) {
                keywordIndex = i;
                break;
            }
        }

        // キーワードが見つかった場合、その後ろのスタックトレースを表示
        if (keywordIndex != -1) {
            System.err.println("例外: " + keyword + " 関連のエラー発生箇所:");

            int end = Math.min(stackTrace.length, keywordIndex + range + 1); // 配列長を超えないように調整

            for (int i = keywordIndex; i < end; i++) {
                System.err.println("例外: " + stackTrace[i].getClassName() +
                                   "." + stackTrace[i].getMethodName() +
                                   " (" + stackTrace[i].getFileName() + ":" + stackTrace[i].getLineNumber() + ")");
            }
        } else {
            System.err.println("例外: '" + keyword + "' の関連エントリが見つかりませんでした。");
        }
    }
}
