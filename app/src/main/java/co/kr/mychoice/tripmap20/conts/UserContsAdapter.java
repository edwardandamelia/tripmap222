package co.kr.mychoice.tripmap20.conts;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import co.kr.mychoice.tripmap20.Tr2Fragment;
import co.kr.mychoice.tripmap20.Tr3Fragment;

public class UserContsAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> frgs;

    public UserContsAdapter(@NonNull FragmentManager fm) {
        super(fm);
        frgs = new ArrayList<>();
        frgs.add(new ContsFragment());
        frgs.add(new Conts2Fragment());
        frgs.add(new Conts3Fragment());
        frgs.add(new Conts5Fragment());
    }

    @Override
    public CharSequence getPageTitle(int position) {

        if(position==0){
            return "About";
        }else if(position==1){
            return "Post";
        }else if(position==2){
            return "Follow";
        }else if(position==3){
            return "Followers";
        }else{
            return "";
        }

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return frgs.get(position);
    }

    @Override
    public int getCount() {
        return frgs.size();
    }
}
