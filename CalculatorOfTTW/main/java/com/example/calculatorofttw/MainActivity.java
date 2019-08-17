package com.example.calculatorofttw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.EmptyStackException;
import java.util.Stack;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    Button left_bra;
    Button right_bra;
    Button btn_0;
    Button btn_1;
    Button btn_2;
    Button btn_3;
    Button btn_4;
    Button btn_5;
    Button btn_6;
    Button btn_7;
    Button btn_8;
    Button btn_9;
    Button btn_point;
    Button btn_add;
    Button btn_subt;
    Button btn_multi;
    Button btn_divide;
    Button btn_del;
    Button btn_clean;
    Button btn_equal;
    static EditText edit_text;
    boolean clear_flag;
    int point = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //拿到相应的组件
        btn_0 = findViewById(R.id.btn_0);
        btn_1 = findViewById(R.id.btn_1);
        btn_2 = findViewById(R.id.btn_2);
        btn_3 = findViewById(R.id.btn_3);
        btn_4 = findViewById(R.id.btn_4);
        btn_5 = findViewById(R.id.btn_5);
        btn_6 = findViewById(R.id.btn_6);
        btn_7 = findViewById(R.id.btn_7);
        btn_8 = findViewById(R.id.btn_8);
        btn_9 = findViewById(R.id.btn_9);
        btn_point = findViewById(R.id.btn_point);
        btn_add = findViewById(R.id.btn_add);
        btn_subt = findViewById(R.id.btn_subtract);
        btn_multi = findViewById(R.id.btn_multiply);
        btn_divide = findViewById(R.id.btn_divide);
        btn_del = findViewById(R.id.btn_del);
        btn_equal = findViewById(R.id.btn_equal);
        btn_clean = findViewById(R.id.btn_C);
        edit_text = findViewById(R.id.edit_text);
        left_bra = findViewById(R.id.btn_leftBra);
        right_bra = findViewById(R.id.btn_rightBra);

        btn_0.setOnClickListener(this);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);
        btn_8.setOnClickListener(this);
        btn_9.setOnClickListener(this);
        btn_point.setOnClickListener(this);
        btn_add.setOnClickListener(this);
        btn_subt.setOnClickListener(this);
        btn_multi.setOnClickListener(this);
        btn_divide.setOnClickListener(this);
        btn_del.setOnClickListener(this);
        btn_equal.setOnClickListener(this);
        btn_clean.setOnClickListener(this);
        left_bra.setOnClickListener(this);
        right_bra.setOnClickListener(this);



    }



    @Override
    public void onClick(View view) {
        String str = edit_text.getText().toString();    //获取每次输入前的表达式
        String currOpr;

        if(str.length() < 60){          //输入字符数量限制

            if("错误".equals(str)) {   //若上次计算结果错误，再次输入前清屏
                str = "";
            }

            switch (view.getId()){

                //按下清除按键直接返回
                case R.id.btn_C:
                    clear_flag = false;
                    str = "";
                    edit_text.setText("");
                    break;

                case R.id.btn_leftBra:
                case R.id.btn_rightBra:
                    edit_text.setText(str + ((Button) view).getText());
                    break;

                //按下数字按键
                case R.id.btn_0:

                    if("0".equals(str)) {
                        break;
                    }else{
                        edit_text.setText(str + ((Button) view).getText());
                        break;
                    }
                case R.id.btn_1:
                case R.id.btn_2:
                case R.id.btn_3:
                case R.id.btn_4:
                case R.id.btn_5:
                case R.id.btn_6:
                case R.id.btn_7:
                case R.id.btn_8:
                case R.id.btn_9:
                    if("0".equals(str)) {
                        str = "";
                        edit_text.setText(str + ((Button) view).getText());
                        break;
                    }else {
                        edit_text.setText(str + ((Button) view).getText());
                        break;
                    }

                case R.id.btn_point:
                    if(checkPoint(str) && !"".equals(str)){
                        edit_text.setText(str + ((Button) view).getText());
                        break;
                    }else{
                        Toast.makeText(MainActivity.this, "输入非法", Toast.LENGTH_SHORT).show();
                        break;
                    }

                    //处理加减乘除符号
                case  R.id.btn_multiply:
                case  R.id.btn_add:
                case  R.id.btn_subtract:
                case  R.id.btn_divide:

                    currOpr = ((Button) view).getText().toString();   //获取当前输入的运算符

                    //分析运算合法性并替换错误运算符
                    if(!isInputLegal(str, currOpr, view)){
                        break;
                    }else{
                        edit_text.setText(str + currOpr);
                        break;
                    }



                case R.id.btn_del:
                    if(str != null && !"".equals(str)){
                        edit_text.setText(str.substring(0, str.length() - 1));
                    }else{
                        break;
                    }
                    break;

                case R.id.btn_equal:
                    Log.d(TAG, "string: " + str);
                    if(isStringLegal(str)){
                        edit_text.setText(ReportUtil.ComputeString(str));
                    }else{
                        str = "错误";
                        edit_text.setText(str);
                    }
                    break;

            }
        }else{
            if(view.getId() == R.id.btn_del){
                edit_text.setText(str.substring(0, str.length() - 1));
            }else if(view.getId() == R.id.btn_equal){
                edit_text.setText(ReportUtil.ComputeString(str));
            }else if(view.getId() == R.id.btn_C){
                edit_text.setText("");
            }else{
                Toast.makeText(MainActivity.this, "输入超限", Toast.LENGTH_SHORT).show();
            }
        }


    }


    //输入等号时判断式子合法性(是否含有数字,括号是否匹配)
    private boolean isStringLegal(String str) {
        boolean isLegal = false;
        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) >= '0' && str.charAt(i) <= '9'){
                isLegal = true;
                break;
            }
        }
        if(str.contains("(") || str.contains(")")){
            return (isLegal && isBraMatch(str));
        }else{
            return isLegal;
        }
    }


    /**
     * 检测括号是否配对
     */
    boolean isBraMatch(String str) {
        StringBuilder sb = new StringBuilder(str);
        for(int i = 0; i < sb.length(); i++){
            if(sb.charAt(i) != '(' && sb.charAt(i) != ')'){
                sb.deleteCharAt(i--);
            }
        }
        str = sb.toString();
        char[] arr = str.toCharArray();
        Stack<Character> stack = new Stack<>();
        stack.push(arr[0]);
        /*
         * 从第二个字符开始，依次与栈中的字符匹配
         */
        for (int i = 1; i < arr.length; ++i) {
            if('(' == arr[i]) {                 //左括号入栈
                stack.push(arr[i]);
            }else {
                Character c1 = new Character(' ');
                if (!stack.isEmpty()) {
                    c1 = stack.peek();
                }
                Character c2 = arr[i];
                if ((c1.toString().equals("(") && c2.toString().equals(")"))) { //右括号和栈中左括号配对并让左括号出栈
                    stack.pop();
                } else {
                    stack.push(c2);
                }
            }
        }

        return stack.isEmpty();
    }


    private static int oprPos = -1;    //最近的符号索引

        private static boolean checkPoint(String str){

        boolean isLegal = false;
        int pointPos = -1;                       //最近的小数点的索引
        int len = str.length();

            //拿到最近的小数点的索引
            for(int i = str.length() - 1; i >= 0; i--){
                if('.' == str.charAt(i)){
                    pointPos = i;
                    break;
                }
            }

            if(haveOpr(str)){   //有运算符时判断和它最近小数点的索引
                if(oprPos > pointPos){
                    isLegal = true;
                }
                //小数点前不能是运算符
                if(str.charAt(len - 1) == '+' || str.charAt(len - 1) == '-' ||
                        str.charAt(len - 1) == '*' || str.charAt(len - 1) == '/'){
                    isLegal = false;
                }
            }else{
                if(pointPos == -1){
                    isLegal = true;
                }else{
                    isLegal = false;
                }
            }
            return isLegal;
        }

        private static boolean haveOpr(String str){
            boolean isHave = false;
            for (int i = str.length() - 1; i >= 0; i--) {
                if('+' == str.charAt(i) || '-' == str.charAt(i) || '*' == str.charAt(i) || '/' == str.charAt(i)){
                    isHave = true;
                    oprPos = i;
                    break;
                }
            }
            return isHave;
        }


        //判断当前输入是否合法,并将错误替换
        private static boolean isInputLegal(String str, String currOpr, View view){

            boolean isLegal = true;

            //无数字输入+*/时无效
            if("".equals(str) && ("+".equals(currOpr) || "*".equals(currOpr) || "/".equals(currOpr))){
                isLegal = false;
            }else if("".equals(str) && "-".equals(currOpr)) {
                edit_text.setText(str + currOpr);
                isLegal = false;
            }else{      //输入前运算式不为空串
                if('+' == str.charAt(str.length() - 1)) {    //判断最后一个运算符和当前输入的运算符,防止重复输入运算符
                    if("+".equals(currOpr)){
                        isLegal = false;
                    }else {
                        String tem = str.substring(0, str.length() - 1);
                        str = tem + ((Button) view).getText();
                        edit_text.setText(str);
                        isLegal = false;
                    }
                }else if('-' == str.charAt(str.length() - 1)) {    //判断最后一个运算符和当前输入的运算符
                    if("-".equals(currOpr)){
                        isLegal = false;
                    }else {
                        String tem = str.substring(0, str.length() - 1);
                        str = tem + currOpr;
                        edit_text.setText(str);
                        isLegal = false;
                    }
                }else if('*' == str.charAt(str.length() - 1)) {    //判断最后一个运算符和当前输入的运算符
                    if("*".equals(currOpr)){
                        isLegal = false;
                     }else if("-".equals(currOpr)){

                    }else {
                        String tem = str.substring(0, str.length() - 1);
                        str = tem + currOpr;
                        edit_text.setText(str);
                        isLegal = false;
                    }
                }else if('/' == str.charAt(str.length() - 1)) {    //判断最后一个运算符和当前输入的运算符
                    if("/".equals(currOpr)){
                        isLegal = false;
                    }else if("-".equals(currOpr)){

                    }else {
                        String tem = str.substring(0, str.length() - 1);
                        str = tem + currOpr;
                        edit_text.setText(str);
                        isLegal = false;
                    }
                }
            }
            return isLegal;
        }



}


