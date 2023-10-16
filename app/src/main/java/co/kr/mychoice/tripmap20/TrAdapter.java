package co.kr.mychoice.tripmap20;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class TrAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> frgs;

    public TrAdapter(@NonNull FragmentManager fm) {
        super(fm);
        frgs = new ArrayList<>();
        frgs.add(new Tr2Fragment());
        frgs.add(new Tr3Fragment());
    }

    @Override
    public CharSequence getPageTitle(int position) {

        if(position==0){
            return "Traveller";
        }else if(position==1){
            return "Travel Info";
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
