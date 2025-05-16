package tool;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Completion {
	private static final Map<String, Completion> pageInfo = new ConcurrentHashMap<>();
	private static final String url = "operate_done.jsp";

	private String title;
	private String message;
	private List<LinkData> links = new ArrayList<>();

	private Completion(){}

	public static Completion getData(String linkName, Callable<Map<String, String>> infomations) {
		return pageInfo.computeIfAbsent(linkName, v -> {
			Map<String, String> infoMap;
			try {infoMap = infomations.call();} catch (Exception e) {throw new RuntimeException(e);}
			Completion instance = new Completion();
			instance.title = infoMap.remove("title");
			instance.message = infoMap.remove("message");
			for (Map.Entry<String, String> link: infoMap.entrySet()) {
				instance.links.add(new LinkData(link.getKey(), link.getValue()));
			}
			return instance;
		});
	}

	public void forward(HttpServletRequest req, HttpServletResponse res) throws Exception {
		req.setAttribute("title", title);
		req.setAttribute("message", message);
		req.setAttribute("links", links);
		req.getRequestDispatcher(url).forward(req, res);
	}

	public static Map<String, String> createInfo(String title, String message, String... links) {
		if (links.length %2 != 0) throw new IllegalArgumentException("引数linksをkey, valueの形に解決できません。引数長は偶数としてください");

		Map<String, String> map = new LinkedHashMap<>();
		map.put("title", title);
		map.put("message", message);
		for (int i=0; i<links.length;) {
			map.put(links[i++], links[i++]);
		}
		return map;
	}
}
