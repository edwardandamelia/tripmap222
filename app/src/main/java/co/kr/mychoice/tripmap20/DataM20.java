package co.kr.mychoice.tripmap20;

public class DataM20 {

    public int ids;
    public String name;
    public String mid;
    public String gender;

    public String conts;
    public String imgtyp;
    public String imgfile;
    public String regdate;

    public Double location1;
    public Double location2;
    public int trids;
    public String trstr;
    public String trcte;
    public String trconts;

    public DataM20(int ids, String name, String mid, String conts,String gender, String imgtyp, String regdate){
        this.ids = ids;
        this.name = name;
        this.mid = mid;
        this.gender = gender;
        this.conts = conts;
        this.imgtyp = imgtyp;
        this.regdate = regdate;
    }

    public DataM20(int ids, String name, String mid ,String trstr, String gender,String imgtyp,String trconts,String trcte, int trids, Double location1, Double location2, String imgfile, String regdate){
        this.ids=ids;
        this.name=name;
        this.trstr=trstr;
        this.mid = mid;
        this.trconts=trconts;
        this.gender = gender;
        this.trids=trids;
        this.trcte=trcte;
        this.imgtyp = imgtyp;
        this.location1 = location1;
        this.location2 = location2;
        this.imgfile = imgfile;
        this.regdate = regdate;
    }

}
