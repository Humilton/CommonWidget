package com.github.Humilton.util;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.util.Locale;

/**
 * Created by Hamilton on 2017/6/29.
 * Location Util
 */

public class LocUtil {
    public static final int IS_CHINA_SIM_CARD = 1;
    public static final int NOT_CHINA_SIM_CARD = -1;
    public static final int UNKNOWN_SIM_CARD = 0;

    private static String getSimOperator(Context c) {
        TelephonyManager tm = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            return tm.getSimOperator();
        } catch (Exception e) {

        }
        return null;
    }

    private static boolean isOperatorEmpty(String operator) {
        if (operator == null) return true;
        if (operator.equals("") || operator.toLowerCase(Locale.US).contains("null"))  return true;
        return false;
    }

    /** 判断是否是国内的 SIM 卡，优先判断注册时的mcc */
    public static int isChinaSimCard(Context c) {
        String mcc = getSimOperator(c);
        if (isOperatorEmpty(mcc)) {
            return UNKNOWN_SIM_CARD;
        } else {
            return mcc.startsWith("460") ? IS_CHINA_SIM_CARD : NOT_CHINA_SIM_CARD;
        }
    }
}
