package main.java.cxyu_spider;

import org.jsoup.nodes.Document;

/**
 * Created by cxyu on 17-6-5.
 */
//这里使主类导入此接口，使代码更为整洁
public interface Page_set {
    void process(Document document);
    //里面有提取部分，数据的保存部分save，包含了elastic search的部分。
}
