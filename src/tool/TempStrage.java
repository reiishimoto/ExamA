package tool;

import java.util.HashMap;
import java.util.Map;

public class TempStrage{
    private final Map<String, Object> map = new HashMap<>();

    public TempStrage(String current, Object data) {
        map.put(current, data);
    }

    public void store(String key, Object value) {
    	map.put(key, value);
    }

    public <T> T retrieve(String key, Class<T> type) {
        return type.cast(map.get(key)); // 安全なキャスト
    }

    public boolean isSendFrom(String key) {
    	return map.containsKey(key);
    }
}
