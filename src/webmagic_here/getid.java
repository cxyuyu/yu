package webmagic_here;

import redis.clients.jedis.Jedis;

public class getid {
	static Jedis jedis = new Jedis("localhost", 6379);
	public static synchronized long addid() {
//		if(jedis.get("id")==null)
//			jedis.set("id", "0");
		return jedis.incr("id");
	}
	public static synchronized long cutid() {
//		if(jedis.get("id")==null)
//			jedis.set("id", "0");
		String id=jedis.get("id");
		long l = Long.parseLong(id);
		l =l-1;
		jedis.set("id", String.valueOf(l));
		return l;
	}
	public static void main(String args[]){
		long sd=121123123;
		System.out.println(String.valueOf(cutid()));
		
	}
}
