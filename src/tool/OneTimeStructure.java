package tool;

public class OneTimeStructure {
    private final String sender;
    private final Object data;

    public OneTimeStructure(String current, Object data) {
        this.sender = current;
        this.data = data;
    }

    public <T> T retrieve(String previous, Class<T> type) {
        if (!previous.equals(sender)) {
            throw new IllegalStateException("アクション " + sender + " からの予期しないアクセス");
        }
        return type.cast(data); // 安全なキャスト
    }

    public String getSender() {
    	return sender;
    }
}
