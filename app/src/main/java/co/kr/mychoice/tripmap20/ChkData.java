package co.kr.mychoice.tripmap20;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ChkData {

    public static String logchk ="20";

    public static String logid="";

    public static ArrayList<Integer> pay_conts_cde = new ArrayList<>();

    public static ArrayList<Integer> pay_shopcde = new ArrayList<>();

    public static ArrayList<String> pay_conts_str = new ArrayList<>();

    public static ArrayList<Integer> pay_conts_prc = new ArrayList<>();

    public static ArrayList<Integer> pay_conts_n = new ArrayList<>();

    public static String member = "";

    public static String userid="";

    public static String user_str="";

    public static String img_url="";


    public static String send_user_str="";

    public static String u_str="";

    public static String m_cte ="";

    public static String u_conts2="";


    public static int position;

    public static int chk_idsids;

    public static int chk_shopcde;

    public static String cte_str="";

    public static String cte_str2="";

    public static Double location1=0.0;

    public static Double location2=0.0;

    public static String sitecte="";

    public static int point=0;

    public static String dl_chk = "";

    public static String send_user_id="";

    public static String cte3_frg="";

    public static String regdate;

    public static int shop_frg=0;

    public static String cte5_frg="";


    public static String formatNumber(int number){
        return NumberFormat.getNumberInstance(Locale.getDefault()).format(number);
    }
    public static String formatNumber(String number){
        return NumberFormat.getNumberInstance(Locale.getDefault()).format(Integer.parseInt(number));
    }

}
