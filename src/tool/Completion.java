package tool;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Completion {
	private static final Map<String, Completion> pageInfo = new ConcurrentHashMap<>();
	private static final String url = "operate_done.jsp";

	private String title;
	private String message;
	private LinkData[] links;

	private Completion(){}

	public static Completion getData(String linkName, Supplier<Map<String, String>> infomations) {
		return pageInfo.computeIfAbsent(linkName, v -> {
			Map<String, String> infoMap = infomations.get();
			Completion instance = new Completion();
			instance.title = infoMap.remove("title");
			instance.message = infoMap.remove("message");
			List<LinkData> list = new ArrayList<>();
			for (Map.Entry<String, String> link: infoMap.entrySet()) {
				list.add(new LinkData(link.getKey(), link.getValue()));
			}
			instance.links = list.toArray(new LinkData[list.size()]);
			return instance;
		});
	}

	public static Completion getDisposable(String linkName, Supplier<Map<String, String>> infomations) {
		Map<String, String> infoMap = infomations.get();
		Completion instance = new Completion();
		instance.title = infoMap.remove("title");
		instance.message = infoMap.remove("message");
		List<LinkData> list = new ArrayList<>();
		for (Map.Entry<String, String> link: infoMap.entrySet()) {
			list.add(new LinkData(link.getKey(), link.getValue()));
		}
		instance.links = list.toArray(new LinkData[list.size()]);
		return instance;
	}

	public void forward(HttpServletRequest req, HttpServletResponse res) throws Exception {
		req.setAttribute("title", title);
		req.setAttribute("message", message);
		req.setAttribute("links", links);
		req.getRequestDispatcher(url).forward(req, res);
	}

	public static Supplier<Map<String, String>> createInfo(String title, String message, String... links) {
		if (links.length %2 != 0) throw new IllegalArgumentException("引数linksをkey, valueの形に解決できません。引数長は偶数としてください");

		return () -> {
			Map<String, String> map = new LinkedHashMap<>();
			map.put("title", title);
			map.put("message", message);
			for (int i=0; i<links.length;) {
				map.put(links[i++], links[i++]);
			}
			return map;
		};
	}
}
