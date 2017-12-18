package com.tuubarobot.bluetoothcommunication;

/**
 * Created by YF-04 on 2017/10/25.
 */

public class Constants {

    public static final String QUESTION="question";
    public static final String ANSWER="answer";
    /**
     *  动作序列 。
     * eg : 83:3400#,#66:3000#,#67:3000
     */
    public static final String ACTION_CODE="action_code";
    public static final String KIND="Kind";

    /**
     *  表情序列
     *  eg:
     */
    public static final String EXPRESSION_CODE="expression_code";

    public static final String BLUETUUTH_DEVICE="bluetooth_device";
    public static final String BLUETUUTH_DEVICE_LIST="bluetooth_device_list";

    public static final int BLUETOOTH_CONNECT_SUCCESS=100;
    public static final int BLUETOOTH_CONNECT_FAILED=200;
    public static final int BLUETOOTH_CONNECT_LOST=300;

    public static final int VIEW_VISIBLE=254856;
    public static final int VIEW_INVISIBLE=64646;
    public static final int VIEW_GONE=325482;


    /**
     * Kind（类型）标识和正文 分开的分隔符
     */
    public static final String  SEPARATOR_BETWEEN_KIND_AND_BYDY_TEXT="##";
    //mohuaiyuan 20171216 ：新版本不在使用
//    public static final String  SEPARATOR_BETWEEN_SPEECH_AND_ACTION="#s_a#";
//    public static final String  SEPARATOR_BETWEEN_ACTION_AND_EXPRESSION="#a_e#";

    /**
     *  正文（除了Kind 标识之外的部分）分开的分隔符。
     *  eg: 小图不是火星人#%#83:3400#,#66:3000#,#67:3000
     */
    public static final String SEPARATOR_BODY_TEXT_APART="#%#";

    public static final String  SEPARATOR_BETWEEN_ACTION_AND_ACTION="#,#";
    public static final String  SEPARATOR_BETWEEN_EXPRESSION_AND_EXPRESSION="#_#";



    public static final String KIND_ACTION="Action";
    public static final String KIND_DANCE="Dance";
    public static final String KIND_FUNCTION="Function";
    public static final String KIND_SPEECH="Speech";


    public static final String FUNCTION_CLOSE_CHAY="close_chat";
    public static final String FUNCTION_OPEN_CHAY="open_chat";
    public static final String FUNCTION_FALL_ASLEEP="fall_asleep";
    public static final String FUNCTION_AWAKEN="awaken";



    public static final String CUSTOM_SCENE_DIR="TuubaCustomScene";
    public static final String MUSIC_ACTION_FILE_NAME="musicActionInfo";
    public static final String STORY_ACTION_FILE_NAME="storyActionInfo";
    public static final String DANCE_ACTION_FILE_NAME="danceActionInfo";
    public static final String BASIC_ACTION_INFO_FILE_NAME="basicActionInfo";
    public static final String CUSTOM_DIALOG_ACTION_INFO_FILE_NAME="customDialogActionInfo";
    public static final String CUSTOM_DIALOG_EXPRESSION_INFO_FILE_NAME="customDialogExpressionInfo";

    /**
     * 机器人tts 一秒钟读的汉字个数
     */
    public static final int THE_NUMBER_OF_WORDS_PER_SECOND=4;

    public static final String FRAGMENT_CATEGORY="fragment_category";
    public static final String FRAGMENT_CATEGORY_DANCE="fragment_dance";
    public static final String FRAGMENT_CATEGORY_BASIC_ACTION="fragment_basic_action";
    public static final String FRAGMENT_CATEGORY_CUSTOM_DIALOG="fragment_custom_dialog";


}
