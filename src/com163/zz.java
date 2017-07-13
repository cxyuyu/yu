package com163;


import java.util.ArrayList;
import java.util.List;
import  java.util.regex.*;
/**
 * Created by cxyu on 17-5-13.
 */
public class zz {
    public static  void main(String[] args){

        get_comment("\"commentId\":83518709,\"content\":" +
                "\"还曾经屠杀海参崴的华人和朝鲜人\",\"createTime\":\"2017-05-11 18:50:58\",\"favCount\":4,\"ip\":\"111.127.*.*\",\"isDel\":false,\"postId\":\"CK643EK3000187VE_83518709\",\"productKey\":\"a2869674571f77b5a0867c3d71db5856\",\"shareCount\":1,\"siteName\":\"网易\",\"source\":\"ph\",\"unionState\":false,\"user\":{\"location\":\"内蒙古呼和浩特市\",\"userId\":0},\"vote\":3838},\"83518882\":{\"against\":4,\"anonymous\":false,\"buildLevel\":1,\"commentId\":83518882,\"content\":\"中日韩应该联合起来整死俄国\",\"createTime\":\"2017-05-11 18:52:02\",\"favCount\":1,\"ip\":\"101.26.*.*\",\"isDel\":false,\"postId\":\"CK643EK3000187VE_83518882\",\"productKey\":\"a2869674571f77b5a0867c3d71db5856\",\"shareCount\":0,\"siteName\":\"网易\",\"source\":\"ph\",\"unionState\":false,\"user\":{\"avatar\":\"http://mobilepics.nosdn.127.net/ATJTmNRMMip8Qa9Z673P2lXosCHl0RzO510041306\",\"id\":\"eWluZ3ppemFpc2h1b2h1YUAxNjMuY29t\",\"location\":\"河北省邯郸市\",\"nickname\":\"最初也是最后的爱\",\"redNameInfo\":[],\"userId\":79419490,\"vipInfo\":\"\"},\"vote\":1062},\"83520135\":{\"against\":1,\"anonymous\":false,\"buildLevel\":1,\"commentId\":83520135,\"content\":\"什么时候我能能上非死不可？\",\"createTime\":\"2017-05-11 18:51:49\",\"favCount\":1,\"ip\":\"49.95.*.*\",\"isDel\":false,\"postId\":\"CK643EK3000187VE_83520135\",\"productKey\":\"a2869674571f77b5a0867c3d71db5856\",\"shareCount\":0,\"siteName\":\"网易\",\"source\":\"ph\",\"unionState\":false,\"user\":{\"avatar\":\"\",\"id\":\"bTE3NzE2MTU4Mjk1QDE2My5jb20=\",\"location\":\"江苏省南京市\",\"nickname\":\"m177****8295\",\"redNameInfo\":[],\"userId\":106513367,\"vipInfo\":\"\"},\"vote\":2551},\"83521047\":{\"against\":46,\"anonymous\":false,\"buildLevel\":2,\"commentId\":83521047,\"content\":\"如果同样的事发生在美国，评论里的调调马上就转向了。说什么腾讯到人家地盘不遵守人家规矩什么的\",\"createTime\":\"2017-05-11 18:53:03\",\"favCount\":0,\"ip\":\"123.152.*.*\",\"isDel\":false,\"postId\":\"CK643EK3000187VE_83521047\",\"productKey\":\"a2869674571f77b5a0867c3d71db5856\",\"shareCount\":0,\"siteName\":\"网易\",\"source\":\"ph\",\"unionState\":false,\"user\":{\"avatar\":\"http://cms-bucket.nosdn.127.net/712ce951681a494a9251aeb09cda74fe20161214202925.jpg\",\"id\":\"dGVycnkwMTMzMkAxNjMuY29t\",\"location\":\"浙江省宁波市\",\"nickname\":\"树底乘凉\",\"redNameInfo\":[],\"userId\":63352274},\"vote\":68},\"83524211\":{\"against\":0,\"anonymous\":false,\"buildLevel\":3,\"commentId\":83524211,\"content\":\"让你移民俄罗斯或者美国二选一，你怎么选？\",\"createTime\":\"2017-05-11 ");
    }



    public static List<String> get_comment(String INPUT){
        List<String> all=new ArrayList<String>();
        String REGEX = "\"content\".*?\"createTime\":";
        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(INPUT); // 获取 matcher 对象
        int count = 0;
        while(m.find()) {
            count++;
            System.out.println("Match number "+count);
            System.out.println("start(): "+m.start());
            System.out.println("end(): "+m.end());
            String comment=INPUT.substring(m.start()+11,m.end()-15);
            System.out.println(comment);
            all.add(comment);
        }
        return all;
    }


}
