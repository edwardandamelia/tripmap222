package co.kr.mychoice.tripmap20;

public class DataStr {

    int ids;
    String shopcde;
    String logid;
    String str;
    String sendstr;
    String sendid;
    String imgfile;
    String regdate;

    public DataStr(String shopcde, String logid, String str) {
        this.shopcde = shopcde;
        this.logid = logid;
        this.str = str;
    }

    public DataStr(int ids, String sendstr, String sendid, String imgfile, String regdate) {
        this.ids = ids;
        this.sendstr = sendstr;
        this.sendid = sendid;
        this.imgfile = imgfile;
        this.regdate = regdate;
    }

}
