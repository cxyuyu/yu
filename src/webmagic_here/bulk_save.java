package webmagic_here;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;
import org.omg.CORBA.DATA_CONVERSION;

import cn.edu.fudan.flow.NamedIdentityRecognizerStart;

public class bulk_save extends Thread {
	// client的错误与bulk的错误都在这里处理
	public void run() {
		while (true) {
			// 设置while以防止client崩溃
			List<Map<String, String>> one_list = null;
			try {
				Settings settings = Settings.builder().put("cluster.name", "DeepSearch&")
						.put("xpack.security.user", "elastic:YangZC*#03").build();
				TransportClient client = null;
				client = new PreBuiltXPackTransportClient(settings).addTransportAddress(
						new InetSocketTransportAddress(InetAddress.getByName("124.127.117.108"), 9300));
				
				//批量存入部分
				while (true) {
					// 防止bulk崩溃
					try {
						if(bulk_data.data_save.size()>0)
						one_list = bulk_data.data_save.remove(0);
						if(one_list!=null)//当没有数值时为空，
						if(one_list.size()!=0)//最后几万防止为零
						if (bulk_data.data_save.size() > 0)
							bulk.save_bulk(client, one_list);
						else
							Thread.sleep(1000);
						// 结束标志
						if (bulk_data.jishu == bulk_data.files_size && bulk_data.data_save.size() == 0)
							break;
					} catch (Exception e) {
						bulk_data.data_save.add(one_list);
						e.printStackTrace();
					}
				}
				client.close();
				// 结束标志
				if (bulk_data.jishu == bulk_data.files_size && bulk_data.data_save.size() == 0)
					break;
			} catch (Exception e) {
				bulk_data.data_save.add(one_list);
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {

	}
}
