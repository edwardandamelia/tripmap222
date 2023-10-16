package co.kr.mychoice.tripmap20.getdata;

public class MemberData {

    String id;
    String name;
    String pwd;
    String hp;
    String imgfile;
    String imgtyp;
    String cte;
    String regdate;
    int point;
    String u_str;

    String insertchk;

    public MemberData(String str,String typ) {
        if(typ.equals("id")) {
            this.id = str;
        }

        if(typ.equals("hpstr")) {
            this.hp = str;
        }

        if(typ.equals("userstr")) {
            this.name = str;
        }
    }



    public MemberData(String str,String pwd,String typ) {

        if(typ.equals("log")) {

            this.id = str;

            this.pwd = pwd;

        }
    }


    public MemberData(String id,String pwd,String hp_str,String userstr) {

        this.id = id;

        this.name = userstr;

        this.hp = hp_str;

        this.pwd = pwd;

    }



    public String getName() {

        return name;
    }


    public String getPwd() {
        return pwd;
    }

    public String getU_str() {
        return u_str;
    }

    public String getId() {
        return id;
    }

    public String getImgtyp() {
        return imgtyp;
    }

    public String getCte() {
        return cte;
    }

    public String getImgfile() {

        return imgfile;
    }

    public int getPoint() {
        return point;
    }



    public String getHp() {
        return hp;
    }

    public String getRegdate() {

        return regdate;
    }



    public String getInsertchk() {

        return insertchk;
    }

}
