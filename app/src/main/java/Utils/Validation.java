package Utils;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.luk.puda.momey_manager.R;


public class Validation {
    public static class HideWarningListener implements TextWatcher {
        private EditText editText;
        private ImageView imageView;

        public HideWarningListener(EditText e, ImageView i) {
            this.editText = e;
            this.imageView = i;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (!editText.getText().toString().trim().isEmpty()) {
                if(imageView.getVisibility() == View.VISIBLE) {
                    imageView.setVisibility(View.INVISIBLE);
                    editText.setHint("");
                    editText.setHintTextColor(null);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

    public static boolean validateEmpty(Context c, EditText e, ImageView i){

        if (e.getText().toString().trim().isEmpty()) {
            if(i.getVisibility() == View.INVISIBLE) {
                i.setVisibility(View.VISIBLE);
                e.setHint(c.getString(R.string.empty_field));
                e.setHintTextColor(ContextCompat.getColor(c, R.color.ErrorDark));
            }
            return false;
        }

        return true;
    }

    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean isValidPhoneNumber(String phone) {
        String Regex = "^((90[0-9]{3})?0|\\+358\\s?)(?!(100|20(0|2(0|[2-3])|9[8-9])|300|600|700|708|75(00[0-3]|(1|2)\\d{2}|30[0-2]|32[0-2]|75[0-2]|98[0-2])))(4|50|10[1-9]|20(1|2(1|[4-9])|[3-9])|29|30[1-9]|71|73|75(00[3-9]|30[3-9]|32[3-9]|53[3-9]|83[3-9])|2|3|5|6|8|9|1[3-9])\\s?(\\d\\s?){4,7}\\d$";
        String Regex2 = "^(?!(100|20(0|2(0|[2-3])|9[8-9])|300|600|700|708|75(00[0-3]|(1|2)\\d{2}|30[0-2]|32[0-2]|75[0-2]|98[0-2])))(4|50|10[1-9]|20(1|2(1|[4-9])|[3-9])|29|30[1-9]|71|73|75(00[3-9]|30[3-9]|32[3-9]|53[3-9]|83[3-9])|2|3|5|6|8|9|1[3-9])\\s?(\\d\\s?){4,7}\\d$";
        if (phone.length() == 0){
            return true;
        }else if (phone.length() >=7 && phone.length() <= 14 ){
            return android.util.Patterns.PHONE.matcher(phone).matches();
        } else {
            return false;
        }
    }

    public static void showErrorIcon (ImageView i) {
        if(i.getVisibility() == View.GONE) {
            i.setVisibility(View.VISIBLE);
        }
    }

    public static void hideErrorIcon (ImageView i) {
        if(i.getVisibility() == View.VISIBLE) {
            i.setVisibility(View.GONE);
        }
    }

    public static void showErrorText (TextView t) {
        if(t.getVisibility() == View.GONE) {
            t.setVisibility(View.VISIBLE);
        }
    }

    public static void hideErrorText (TextView t) {
        if(t.getVisibility() == View.VISIBLE) {
            t.setVisibility(View.GONE);
        }
    }

}
