package root.application.Controller.Adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import root.application.View.Dialog.CreateMindMapDialog.EditBackgroundFragment;
import root.application.View.Dialog.CreateMindMapDialog.EditBitmapFragment;
import root.application.View.Dialog.CreateMindMapDialog.EditForegroundFragment;
import root.application.View.Dialog.CreateMindMapDialog.EditTextFragment;

/**
 * Created by root on 30.12.15.
 */
public class CreateMindMapDialogSectionsPagerAdapter extends FragmentStatePagerAdapter {
    public CreateMindMapDialogSectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:  return new EditTextFragment();

            case 1:  return new EditForegroundFragment();

            case 2:  return new EditBackgroundFragment();

            case 3:  return new EditBitmapFragment();

            default: return null;

        }

    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "ТЕКСТ";
            case 1:
                return "ШРИФТ";
            case 2:
                return "ФОН";
            case 3:
                return "ИЗОБРАЖЕНИЕ";
            default:
                return null;

        }

    }
}
