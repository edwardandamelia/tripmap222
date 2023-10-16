package co.kr.mychoice.tripmap20;

public class Datashop {

    public int idsids;
    public String sname;
    public String sadr;
    public String sadr2;
    public String stel;
    public String sconts;
    public String scte;
    public int plus;
    public Double point;
    public String imgfile;
    public Double location1;
    public Double location2;
    public String regdate;

    public Datashop(int idsids) {
        this.idsids = idsids;
    }

    public Datashop(int idsids,String sname, String sadr, String stel,String sconts,String scte,Double point, int plus) {
        this.idsids = idsids;
        this.sname = sname;
        this.sadr = sadr;
        this.stel = stel;
        this.scte = scte;
        this.sconts = sconts;
        this.point = point;
        this.plus = plus;
    }


    public Datashop(int idsids, String sname, String sadr, String sadr2, int plus,Double point, String imgfile, Double location1, Double location2, String regdate) {
        this.idsids = idsids;
        this.sname = sname;
        this.sadr = sadr;
        this.sadr2 = sadr2;
        this.plus = plus;
        this.point = point;
        this.imgfile = imgfile;
        this.location1 = location1;
        this.location2 = location2;
        this.regdate = regdate;
    }

    public Datashop(int idsids, String sname, String sadr, String sadr2, String scte,int plus,Double point, String imgfile, Double location1, Double location2, String regdate) {
        this.idsids = idsids;
        this.sname = sname;
        this.sadr = sadr;
        this.sadr2 = sadr2;
        this.scte = scte;
        this.plus = plus;
        this.point = point;
        this.imgfile = imgfile;
        this.location1 = location1;
        this.location2 = location2;
        this.regdate = regdate;
    }

}
