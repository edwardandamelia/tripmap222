package co.kr.mychoice.tripmap20.getdata;

public class ContsData {

    String trstr;

    String trarea;

    String trtyp;

    String trcde;

    String trconts;

    String userid;

    String trconts2;

    int point;

    String u_str;

    String flbt;

    String chk_plus;

    String gen;

    String str;

    int page_chk;

    String userstr;

    String place;

    String place2;

    int ids;

    String chk_str;

    String flbt_str;

    String conts;

    String account;

    String cte;

    String conts2;

    String chk;

    String logid;

    String str_username;

    String hm;

    String str_imgsrc;

    int fl_n;

    int fl_n2;

    String str_str;

    String receiveid;

    String uid;

    String chk_conts;

    String sendid;

    String imgfile;

    String imgfile2;

    String imgfile3;

    String imgfile4;

    String imgfile5;

    String imgfile_2;

    String imgfile2_2;

    String imgfile3_2;

    String imgfile4_2;

    String imgfile5_2;

    Double distance;

    String imgsrc;

    String imgsrc2;

    String imgsrc3;

    String imgsrc4;

    String imgsrc5;

    String insertchk;



    int plus;

    Double location;

    Double location2;



    String site;

    String regdate;

    public ContsData(String conts2,String place,String place2,Double location,Double location2, String imgfile, String imgfile2, String imgfile3,String imgfile4,String imgfile5,String imgfile_2, String imgfile2_2, String imgfile3_2,String imgfile4_2,String imgfile5_2, String uid) {

        this.conts = conts;
        this.conts2 = conts2;

        this.place = place;
        this.place2 = place2;

        this.location = location;
        this.location2 = location2;

        this.imgfile = imgfile;
        this.imgfile2 = imgfile2;
        this.imgfile3 = imgfile3;
        this.imgfile4 = imgfile4;
        this.imgfile5 = imgfile5;

        this.imgfile_2 = imgfile_2;
        this.imgfile2_2 = imgfile2_2;
        this.imgfile3_2 = imgfile3_2;
        this.imgfile4_2 = imgfile4_2;
        this.imgfile5_2 = imgfile5_2;

        this.uid = uid;

    }

    public ContsData(String str,String sendid,String receiveid,String logid,String imgfile,String hm,String regdate){

        this.str =str;
        this.sendid = sendid;
        this.receiveid = receiveid;
        this.hm = hm;
        this.imgfile = imgfile;
        this.logid = logid;
        this.regdate = regdate;

    }

    public void setChk_plus(String chk_plus) {
        this.chk_plus = chk_plus;
    }

    public String getTrstr() {
        return trstr;
    }

    public String getTrarea() {
        return trarea;
    }

    public String getTrtyp() {
        return trtyp;
    }

    public String getTrcde() {
        return trcde;
    }

    public String getTrconts() {
        return trconts;
    }

    public int getIds() {
        return ids;
    }

    public String getUserstr() {
        return userstr;
    }

    public String getFlbt() {
        return flbt;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public Double getDistance() {
        return distance;
    }

    public int getPlus() {
        return plus;
    }

    public String getChk_plus() {
        return chk_plus;
    }

    public String getInsertchk() {
        return insertchk;
    }

    public String getTrconts2() {
        return trconts2;
    }

    public String getPlace() {
        return place;
    }

    public String getImgfile2() {
        return imgfile2;
    }

    public String getImgfile3() {
        return imgfile3;
    }

    public String getImgfile4() {
        return imgfile4;
    }

    public String getImgfile5() {
        return imgfile5;
    }

    public String getImgsrc2() {
        return imgsrc2;
    }

    public String getImgsrc3() {
        return imgsrc3;
    }

    public String getImgsrc4() {
        return imgsrc4;
    }

    public String getImgsrc5() {
        return imgsrc5;
    }

    public String getU_str() {
        return u_str;
    }

    public String getConts() {
        return conts;
    }

    public String getChk_conts() {
        return chk_conts;
    }

    public void setChk_conts(String chk_conts) {
        this.chk_conts = chk_conts;
    }

    public String getImgfile_2() {
        return imgfile_2;
    }

    public String getImgfile2_2() {
        return imgfile2_2;
    }

    public String getImgfile3_2() {
        return imgfile3_2;
    }

    public String getImgfile4_2() {
        return imgfile4_2;
    }

    public String getImgfile5_2() {
        return imgfile5_2;
    }

    public String getStr() {
        return str;
    }

    public String getGen() {
        return gen;
    }

    public int getFl_n() {
        return fl_n;
    }

    public int getFl_n2() {
        return fl_n2;
    }

    public String getCte() {
        return cte;
    }

    public String getUserid() {
        return userid;
    }

    public String getFlbt_str() {
        return flbt_str;
    }

    public String getImgfile() {
        return imgfile;
    }

    public String getChk_str() {
        return chk_str;
    }

    public String getAccount() {
        return account;
    }

    public String getPlace2() {
        return place2;
    }

    public String getConts2() {
        return conts2;
    }

    public int getPoint() {
        return point;
    }

    public String getLogid() {
        return logid;
    }

    public String getHm() {
        return hm;
    }

    public String getReceiveid() {
        return receiveid;
    }

    public String getSendid() {
        return sendid;
    }

    public String getUid() {
        return uid;
    }

    public String getChk() {
        return chk;
    }

    public String getStr_username() {
        return str_username;
    }

    public String getStr_imgsrc() {
        return str_imgsrc;
    }

    public int getPage_chk() {
        return page_chk;
    }

    public String getStr_str() {
        return str_str;
    }

    public String getRegdate() {
        return regdate;
    }

    public Double getLocation() {
        return location;
    }

    public Double getLocation2() {
        return location2;
    }

    public String getSite() {
        return site;
    }
}
