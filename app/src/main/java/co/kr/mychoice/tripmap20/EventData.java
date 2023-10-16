package co.kr.mychoice.tripmap20;

public class EventData {

    public String typ;
    public String str;
    public int ids;
    public Double location1;
    public Double location2;

    public EventData(String typ,String str) {

        this.typ = typ;
        this.str = str;
    }

    public EventData(String str,int ids) {

        this.str = str;
        this.ids = ids;
    }

    public EventData(String typ,Double location1,Double location2) {

        this.typ = typ;

        this.location1 = location1;

        this.location2 = location2;

    }

}
