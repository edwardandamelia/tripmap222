package co.kr.mychoice.tripmap20;

import android.location.Location;

public class Datas implements  Comparable<Datas>{

    public int idsids;
    public String trstr;
    public String sadr;
    public String sadr2;
    public String stel;
    public String trconts;
    public String trcte;
    public String userid;
    public int plus;
    public String str;
    public String sendid;
    public Double point;
    public String imgfile;
    public Double location1;
    public Double location2;
    public String regdate;
    public Double distance;

    public Datas(int idsids) {
        this.idsids = idsids;
    }

    public Datas(int idsids, String str, String sendid, String regdate) {

        this.idsids = idsids;
        this.str = str;
        this.sendid = sendid;
        this.regdate = regdate;

    }

    public Datas(int idsids, String trstr, String sadr, String stel, String trconts, String trcte){
        this.idsids = idsids;
        this.trstr = trstr;
        this.sadr = sadr;
        this.stel = stel;
        this.trcte = trcte;
        this.trconts = trconts;
    }

    public Datas(int idsids,String trstr,String userid,String sadr,String sadr2,int plus,Double point,String imgfile,Double distance,Double location1,Double location2,String regdate){
        this.idsids = idsids;
        this.trstr = trstr;
        this.sadr = sadr;
        this.sadr = sadr2;
        this.imgfile = imgfile;
        this.point = point;
        this.plus = plus;
        this.userid = userid;
        this.location1 = location1;
        this.location2 = location2;
        this.regdate = regdate;
        this.distance = distance;
    }

    public Datas(int idsids, String trstr, String trconts, String trcte, Double point, int plus) {
        this.idsids = idsids;
        this.trstr = trstr;
        this.trcte = trcte;
        this.trconts = trconts;
        this.point = point;
        this.plus = plus;

    }


    public Datas(int idsids, String trstr,String userid,  String sadr, String trcte, int plus, Double point, String imgfile, Double distance, String regdate) {
        this.idsids = idsids;
        this.trstr = trstr;
        this.userid = userid;
        this.sadr = sadr;
        this.trcte = trcte;
        this.plus = plus;
        this.point = point;
        this.imgfile = imgfile;
        this.regdate = regdate;
        this.distance = distance;
    }


    public Datas(int idsids, String trstr,String userid,  String sadr, String trcte, int plus, Double point, String imgfile, Double location1,Double location2, String regdate) {
        this.idsids = idsids;
        this.trstr = trstr;
        this.userid = userid;
        this.sadr = sadr;
        this.trcte = trcte;
        this.plus = plus;
        this.point = point;
        this.imgfile = imgfile;
        this.location1 = location1;
        this.location2 = location2;
        this.regdate = regdate;

    }

    private Float getDistance(Double lat1, Double lng1, Double location1, Double location2) {
        android.location.Location loc1 = new android.location.Location("");
        loc1.setLatitude(lat1);
        loc1.setLongitude(lng1);
        android.location.Location loc2 = new Location("");
        loc2.setLatitude(location1);
        loc2.setLongitude(location2);
        Float distanceInMeters = loc1.distanceTo(loc2);
        return distanceInMeters/1000;
    }


    public Datas(int idsids, String trstr,String userid, String sadr, String sadr2, String trcte, int plus, Double point, String imgfile, Double location1, Double location2, String regdate) {
        this.idsids = idsids;
        this.trstr = trstr;
        this.userid = userid;
        this.sadr = sadr;
        this.sadr2 = sadr2;
        this.trcte = trcte;
        this.plus = plus;
        this.point = point;
        this.imgfile = imgfile;
        this.location1 = location1;
        this.location2 = location2;
        this.regdate = regdate;
    }






    @Override
    public int compareTo(Datas dts) {

        return this.distance > dts.distance ? 1 : (this.distance < dts.distance ) ? -1 : 0;

    }

}
