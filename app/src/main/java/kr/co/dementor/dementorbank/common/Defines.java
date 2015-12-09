package kr.co.dementor.dementorbank.common;

import java.util.ArrayList;
import java.util.Arrays;

import kr.co.dementor.dementorbank.R;

public class Defines
{
    // Default Value
    public static final String DEFAULT_HOST    = "http://118.33.90.76:9101"; // 디멘터test서버.
    public static final int    DEFAULT_TIMEOUT = 10 * 1000; // millisecond
    // public static final String DEFAULT_HOST =
    // "http://mbetest.shinhan.com:38300"; // 신한서버.

    public static final int MAX_KEY_CAPACITY = 4;

    public static final int MAX_AUTH_ICON_CAPACITY = 25;

    public static final int MAX_DUMMY_ICON_CAPACITY = MAX_AUTH_ICON_CAPACITY - MAX_KEY_CAPACITY;

    public static final int WHITE_AREA = -1;

    public static final ArrayList<Integer> RES_ID_AUTH_HELP = new ArrayList<Integer>
            (
                    Arrays.asList
                            (R.drawable.login00,
                             R.drawable.login01,
                             R.drawable.login02,
                             R.drawable.login03,
                             R.drawable.login04)
            );

    public static final ArrayList<Integer> RES_ID_REGISTER_HELP = new ArrayList<Integer>
            (
                    Arrays.asList
                            (R.drawable.key00,
                             R.drawable.key01,
                             R.drawable.key02,
                             R.drawable.key03)
            );

    public static final ArrayList<Integer> RES_ID_NUM                = new ArrayList<Integer>
            (
                    Arrays.asList
                            (R.drawable.idm000132012,
                             R.drawable.idm001132012,
                             R.drawable.idm002132012,
                             R.drawable.idm003132012,
                             R.drawable.idm004132012,
                             R.drawable.idm005132012,
                             R.drawable.idm006132012,
                             R.drawable.idm007132012,
                             R.drawable.idm008132012,
                             R.drawable.idm009132012)
            );
    public static final ArrayList<Integer> RES_ID_ENG                = new ArrayList<Integer>
            (
                    Arrays.asList
                            (R.drawable.idm000041306,
                             R.drawable.idm001041306,
                             R.drawable.idm002041306,
                             R.drawable.idm003041306,
                             R.drawable.idm004041306,
                             R.drawable.idm005041306,
                             R.drawable.idm006041306,
                             R.drawable.idm007041306,
                             R.drawable.idm008041306,
                             R.drawable.idm009041306,
                             R.drawable.idm010041306,
                             R.drawable.idm011041306,
                             R.drawable.idm012041306,
                             R.drawable.idm013041306,
                             R.drawable.idm014041306,
                             R.drawable.idm015041306,
                             R.drawable.idm016041306,
                             R.drawable.idm017041306,
                             R.drawable.idm018041306,
                             R.drawable.idm019041306,
                             R.drawable.idm020041306,
                             R.drawable.idm021041306,
                             R.drawable.idm022041306,
                             R.drawable.idm023041306,
                             R.drawable.idm024041306,
                             R.drawable.idm025041306)
            );
    public static final ArrayList<Integer> RES_ID_HAN                = new ArrayList<Integer>
            (
                    Arrays.asList
                            (R.drawable.idm000101417,
                             R.drawable.idm001101417,
                             R.drawable.idm002101417,
                             R.drawable.idm003101417,
                             R.drawable.idm004101417,
                             R.drawable.idm005101417,
                             R.drawable.idm006101417,
                             R.drawable.idm007101417,
                             R.drawable.idm008101417,
                             R.drawable.idm009101417,
                             R.drawable.idm010101417,
                             R.drawable.idm011101417,
                             R.drawable.idm012101417,
                             R.drawable.idm013101417,
                             R.drawable.idm014101417,
                             R.drawable.idm015101417,
                             R.drawable.idm016101417,
                             R.drawable.idm017101417,
                             R.drawable.idm018101417,
                             R.drawable.idm019101417,
                             R.drawable.idm020101417,
                             R.drawable.idm021101417,
                             R.drawable.idm022101417,
                             R.drawable.idm023101417,
                             R.drawable.idm024101417,
                             R.drawable.idm025101417,
                             R.drawable.idm026101417,
                             R.drawable.idm027101417,
                             R.drawable.idm028101417)
            );
    public static final ArrayList<Integer> RES_ID_PRIVATE            = new ArrayList<Integer>
            (
                    Arrays.asList
                            (R.drawable.private_01,
                             R.drawable.private_02,
                             R.drawable.private_03,
                             R.drawable.private_04,
                             R.drawable.private_05,
                             R.drawable.private_06,
                             R.drawable.private_07,
                             R.drawable.private_08,
                             R.drawable.private_09,
                             R.drawable.private_10,
                             R.drawable.private_11,
                             R.drawable.private_12,
                             R.drawable.private_13,
                             R.drawable.private_14,
                             R.drawable.private_15,
                             R.drawable.private_16,
                             R.drawable.private_17,
                             R.drawable.private_18,
                             R.drawable.private_19,
                             R.drawable.private_20,
                             R.drawable.private_21,
                             R.drawable.private_22,
                             R.drawable.private_23,
                             R.drawable.private_24,
                             R.drawable.private_25,
                             R.drawable.private_26,
                             R.drawable.private_27,
                             R.drawable.private_28,
                             R.drawable.private_29,
                             R.drawable.private_30,
                             R.drawable.private_31,
                             R.drawable.private_32,
                             R.drawable.private_33)
            );
    public static final ArrayList<Integer> RES_ID_DEFAULT_HINT_IMAGE = new ArrayList<Integer>
            (
                    Arrays.asList
                            (R.drawable.hint_rock,
                             R.drawable.hint_key01,
                             R.drawable.hint_key02,
                             R.drawable.hint_key03)
            );
    public static final ArrayList<Integer> RES_ID_DRAG               = new ArrayList<Integer>
            (
                    Arrays.asList
                            (R.drawable.icon_money0,
                             R.drawable.icon_money1,
                             R.drawable.icon_money2,
                             R.drawable.icon_money3,
                             R.drawable.icon_money4)
            );
    public static final ArrayList<String>  SEND_TARGET_LIST          = new ArrayList<String>
            (
                    Arrays.asList
                            ("저축",
                             "관리비",
                             "부모님",
                             "월세",
                             "피아노"
                            )
            );

    public static enum RegistStatus
    {
        SELECTED_NONE(0), SELECTED_LOCK(1), SELECTED_KEY1(2), SELECTED_KEY2(3), SELECTED_KEY3(4);

        private int value;

        private RegistStatus(int value)
        {
            this.value = value;
        }

        public int getValue()
        {
            return value;
        }

        public RegistStatus nextState()
        {
            for (RegistStatus status : RegistStatus.values())
            {
                if (this.getValue() < status.getValue())
                {
                    return status;
                }
            }
            return this;
        }
    }

    public static enum AuthStatus
    {
        INSERT_NONE(0), INSERTED_KEY1(1), INSERTED_KEY2(2), INSERTED_KEY3(3);

        private int value;

        private AuthStatus(int value)
        {
            this.value = value;
        }

        public int getValue()
        {
            return value;
        }

        public AuthStatus nextState()
        {
            for (AuthStatus status : AuthStatus.values())
            {
                if (this.getValue() < status.getValue())
                {
                    return status;
                }
            }
            return this;
        }
    }

    public class ImagePosition
    {
        public static final int LOCK = 0;
        public static final int KEY1 = 1;
        public static final int KEY2 = 2;
        public static final int KEY3 = 3;
    }

    public class ArrowImagePosition
    {
        public static final int ARROW1 = 0;
        public static final int ARROW2 = 1;
        public static final int ARROW3 = 2;
    }


}
