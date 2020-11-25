package com.cdp.calc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class LinearActivity extends AppCompatActivity {
    private TextView resultText=null;
    private Button[] commantBtn=new Button[5];
    private Button[] numBtn=new Button[11];
    private Button clearBtn;
    private boolean firstFlag;
    private String lastCommand="";//lastCommand=null; lastCommant=" ";表示是否按了操作符，保存按了什么操作符
    private boolean clearFlag;//记录是否首次按操作符及按数字键时是否要清空显示框内的数字
    private double result;//保存计算结果

    private String num1="";//记录第一个操作数
    private String num2="";//记录第二个操作数
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear);
        initView();
        setEvent();
    }
    public void initView(){
        result=0;
        firstFlag=true;
        lastCommand="";
        clearFlag=false;
        resultText=findViewById(R.id.result_id);//结果输出
        resultText.setText("0");
        //操作按钮初始化
        commantBtn[0]=findViewById(R.id.plus);
        commantBtn[1]=(Button)findViewById(R.id.minus);
        commantBtn[2]=(Button)findViewById(R.id.multiple);
        commantBtn[3]=(Button)findViewById(R.id.divid);
        commantBtn[4]=(Button)findViewById(R.id.equalbt);
        //数字键按钮初始化
        numBtn[0]=(Button)findViewById(R.id.numb0);
        numBtn[1]=(Button)findViewById(R.id.numb1);
        //numBtn[1].setHeight(100);
        numBtn[1].setWidth(40);
        numBtn[2]=(Button)findViewById(R.id.numb2);
        numBtn[3]=(Button)findViewById(R.id.numb3);
        numBtn[4]=(Button)findViewById(R.id.numb4);
        numBtn[5]=(Button)findViewById(R.id.numb5);
        numBtn[6]=(Button)findViewById(R.id.numb6);
        numBtn[7]=(Button)findViewById(R.id.numb7);
        numBtn[8]=(Button)findViewById(R.id.numb8);
        numBtn[9]=(Button)findViewById(R.id.numb9);
        numBtn[10]=(Button)findViewById(R.id.dot);
        clearBtn=(Button)findViewById(R.id.clearbt);
    }

    public void setEvent(){
        clearBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {//清空按钮事件响应
                // TODO Auto-generated method stub
                resultText.setText("0");
                result=0;
                firstFlag=true;
                lastCommand="";//设置最后的按键命令
                num1="";//记录第一个操作数
                num2="";//记录第二个操作数
            }
        });
        NumberListener nl=new NumberListener();//数字按钮监听器
        ActionListener al=new ActionListener();//动作按钮监听器
        for(Button btn : numBtn){
            btn.setOnClickListener(nl);
        }
        for(Button abtn : commantBtn){
            abtn.setOnClickListener(al);
        }
    }

    public class ActionListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Button albtn=(Button)v;
            String inputCommant=albtn.getText().toString();//+
            if(firstFlag){//判断是否是第一次按计算器按钮
                if(inputCommant.equals("-")){//考虑到是负数
                    //resultText.setText("-");
                    num1=num1+inputCommant;
                    resultText.setText(num1);
                    firstFlag=false;
                }
            }
            else{
                if(num1.equals("-")){
                    return;
                }
                if(!num1.equals("")&&num2.equals("")){
                    lastCommand=inputCommant;
                    resultText.setText(num1+lastCommand);
                }
                if(!num1.equals("")&&!num2.equals("")){
                    calct(Double.parseDouble(num1),Double.parseDouble(num2));
                    resultText.setText(num1+lastCommand+num2+"="+result);
                }
            }
        }
        private void calct(double num1,double num2){
            if(lastCommand.equals("+")){
                result =num1+num2;//result=result+num;
            }else if(lastCommand.equals("-")){
                result=num1-num2;
            }else if(lastCommand.equals("*")){
                result =num1*num2;
            }else if(lastCommand.equals("/")){
                result=num1/num2;
            }
            DecimalFormat decimalFormat=new DecimalFormat("#.##");
            result=Double.parseDouble(decimalFormat.format(result));

        }
    }

    public class NumberListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Button nlbtn=(Button)v;
            String input=nlbtn.getText().toString();
            if(firstFlag){//
                if(input.equals(".")){//如果第一次是按"."按钮，则不需要相应
                    return;
                }
                num1=num1+input;
               /* if(resultText.getText().toString().equals("0")){//如果之前的按键结果为零
                    resultText.setText("");
                }*/
               resultText.setText(num1);
                firstFlag=false;
            }
            else{
                if(lastCommand.equals("")){
                    if(num1.indexOf(".")>-1 && input.equals(".")){//如果已按数字结果中有"."，则再按"."时不需要响应。.3453\0
                        return;
                    }
                    if(num1.equals("0")&&!input.equals(".")){//如果已按数字结果是"0"，而此次按键输入不是"."时，则不需要响应。
                        return;
                    }
                    if(num1.equals("-")&&input.equals(".")){//如果已按数字结果是"-"，而此次按键输入是"."时，则不需要响应。
                        return;
                    }
                    num1=num1+input;
                    resultText.setText(num1);
                }
                if(!lastCommand.equals("")){
                    if(num2.equals("")&&input.equals(".")){
                        return;
                    }
                    if(num2.contains(".") && input.equals(".")){//如果已按数字结果中有"."，则再按"."时不需要响应。
                        return;
                    }
                    if(num2.equals("0")&&!input.equals(".")){//如果已按数字结果是"0"，而此次按键输入不是"."时，则不需要响应。
                        return;
                    }
                    if(num2.equals("-")&&input.equals(".")){//如果已按数字结果是"-"，而此次按键输入是"."时，则不需要响应。
                        return;
                    }
                    num2=num2+input;
                    resultText.setText(num1+lastCommand+num2);
                }
            }
        }
    }
}