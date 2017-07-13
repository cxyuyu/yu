package zu_code2;

/**
 * Created by cxyu on 17-6-16.
 */
public class Start_get {
    public static void main(String args[]){
        get get=new get();
        get_url get_url=new get_url();
        for(int i=0;i<1;i++){
        Thread one=new Thread(get);
        one.start();}
        for(int i=0;i<10;i++){
            Thread one=new Thread(get_url);
            one.start();}
    }
}
