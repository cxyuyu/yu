package webmagic_here;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//别忘了添加上JSON包哦!
public class StringToJSON {
	public void testArrayToJSON() {
		boolean[] boolArray = new boolean[] { true, false, true };
		JSONArray jsonArray = JSONArray.fromObject(boolArray);
		//System.out.println(jsonArray);
		// prints [true,false,true]
	}

	public void testListToJSON() {
		List list = new ArrayList();
		list.add("first");
		list.add("second");
		JSONArray jsonArray = JSONArray.fromObject(list);
		//System.out.println(jsonArray.toString());
		// prints ["first","second"]
	}

	public static String testMapToJSON(String officion_content, String title, String content, String time, String url,
			String Ner, String id) {
		Map map = new HashMap();
		map.put("id", id);
		map.put("title", title);
		map.put("officion_content", title);
		map.put("content", content);
		map.put("time", time);
		map.put("Ner", Ner);
		map.put("url", url);

		JSONObject jsonObject = JSONObject.fromObject(map);
		return jsonObject.toString();
	}

	public static void writeFile(String sets, long id) throws IOException {
		String fliename = get_file_my.crate_flie(id);
		
		if (fliename == null) {
			error_flie(sets,id);
		} else {
			FileWriter fw;
			fw = new FileWriter(fliename, true);
			PrintWriter out = new PrintWriter(fw);
			out.write(sets);
			out.close();
			fw.close();
			//System.out.print("fliename" + fliename+new Date());
		}
	}
	public static void error_flie(String sets, long id) throws IOException{
		String fliename=weixin_new_nerchange.fliead+"/0-error.json";
		FileWriter fw;
		fw = new FileWriter(fliename, true);
		PrintWriter out = new PrintWriter(fw);
		out.write(sets);
		out.close();
		fw.close();
	}
	
	public static void main(String[] args) throws IOException {
		FileWriter fw;
		fw = new FileWriter("/home/cxyu/webmagic/cxyu.json", true);
		StringToJSON s = new StringToJSON();
		s.writeFile(s.testMapToJSON("ss", "ss", "ss", "ss", "ss", "ss", "asd"),1);

	}

}