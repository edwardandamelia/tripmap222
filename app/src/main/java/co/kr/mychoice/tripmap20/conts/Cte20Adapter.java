package co.kr.mychoice.tripmap20.conts;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import co.kr.mychoice.tripmap20.Tr3Fragment;

public class Cte20Adapter extends FragmentPagerAdapter {
    ArrayList<Fragment> frgs;

    public Cte20Adapter(@NonNull FragmentManager fm) {
        super(fm);
        frgs = new ArrayList<>();
        frgs.add(new Cte20Fragment());
        frgs.add(new Cte22Fragment());
    }

    @Override
    public CharSequence getPageTitle(int position) {

        if(position==0){
            return "Destination";
        }else if(position==1){
            return "Traveler";
        }else{
            return "";
        }

    }

    @Override
    public Fragment getItem(int position) {
        return frgs.get(position);
    }

    @Override
    public int getCount() {
        return frgs.size();
    }
}
