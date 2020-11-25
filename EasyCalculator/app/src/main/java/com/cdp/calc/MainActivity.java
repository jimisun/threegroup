package com.cdp.calc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    Button[] number_bt=new Button[11];//用来接收从界面获取的数字按钮
    Button[] oprt_bt=new Button[5];//用来保存从界面获取的操作符按钮
    TextView result_et;
    Button clear_bt;
    Button deletebt;
    boolean firstFlag=true;
    boolean clearFlag=false;
    String lastCommand="";
    double result=0;
    String numb1="";
    String numb2="";
    String citye="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {//类似Main函数
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constrain);//3+78=81
        number_bt[0]=findViewById(R.id.cnumb0);//Map<String,Object>
        number_bt[1]=findViewById(R.id.numb1);
        number_bt[2]=findViewById(R.id.numb2);
        number_bt[3]=findViewById(R.id.numb3);
        number_bt[4]=findViewById(R.id.numb4);
        number_bt[5]=findViewById(R.id.numb5);
        number_bt[6]=findViewById(R.id.numb6);
        number_bt[7]=findViewById(R.id.numb7);
        number_bt[8]=findViewById(R.id.numb8);
        number_bt[9]=findViewById(R.id.numb9);
        number_bt[10]=findViewById(R.id.cdot);
        oprt_bt[0]=findViewById(R.id.plus);
        oprt_bt[1]=findViewById(R.id.minus);
        oprt_bt[2]=findViewById(R.id.multiple);
        oprt_bt[3]=findViewById(R.id.cdivid);
        oprt_bt[4]=findViewById(R.id.cequal);
        result_et=findViewById(R.id.result_id);
        clear_bt=findViewById(R.id.cclearbt);
        deletebt=findViewById(R.id.delete);
        result_et.setText("");
        NumberClickListener numberClickListener=new NumberClickListener();
        for(Button tempbt:number_bt){
            tempbt.setOnClickListener(numberClickListener);
        }
        OpratorClickListener opratorClickListener=new OpratorClickListener();
        for(Button tempbt:oprt_bt){
            tempbt.setOnClickListener(opratorClickListener);
        }
        clear_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstFlag=true;
                clearFlag=false;
                lastCommand="";
                result=0;
                numb1="";
                numb2="";
                result_et.setText("");
            }
        });
        deletebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resultstr=result_et.getText().toString();//zhuo
                if(resultstr.equals("")){
                  return;
                }
                String laststr=resultstr.substring(resultstr.length()-1,resultstr.length());
                //String rightstr=resultstr.substring(0,resultstr.length()-1);
               /* for(Button tempbt:oprt_bt){
                    if(laststr.equals(tempbt.getText().toString())){
                        lastCommand="";
                        result_et.setText(numb1);
                        return;
                    }
                }*/
               if(!lastCommand.equals("")&&numb2.equals("")){
                   lastCommand="";
                   result_et.setText(numb1);
                   return;
               }
                if(lastCommand.equals("")){
                    numb1=numb1.substring(0,numb1.length()-1);
                    result_et.setText(numb1);
                    return;
                }
                if(!lastCommand.equals("")&&!numb2.equals("")){
                    numb2=numb2.substring(0,numb2.length()-1);
                    result_et.setText(numb1+lastCommand+numb2);
                }

            }
        });
    }

    class NumberClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Button inputbt=(Button)v;//表示获得了某个按钮 Map<String,Object> mm=new HashMap();
            String inputstr=inputbt.getText().toString();//表示按钮上的内容6
            String resultstr=result_et.getText().toString();//表示获取输入框中的内容resultstr=52.0
            if(firstFlag){
                if(inputstr.equals(".")){
                    return;
                }
                numb1=numb1+inputstr;
                result_et.setText("");
                result_et.setText(numb1);
                firstFlag=false;
            }else{
                if(lastCommand.equals("")){//用户记录第一操作数
                    if(numb1.indexOf(".")>0 && inputstr.equals(".")){//8.907
                        return;
                    }
                    if(numb1.equals("0")&&!inputstr.equals(".")){
                        return;
                    }
                    if(numb1.equals("-")&&inputstr.equals(".")){
                        return;
                    }
                    if(numb1.equals("-0")&&!inputstr.equals(".")){
                        return;
                    }
                    numb1=numb1+inputstr;
                    result_et.setText(numb1);
                }
                if(!lastCommand.equals("")){//用来记录第二个操作数
                    if(numb2.equals("")&&inputstr.equals(".")){
                        return;
                    }
                    if(numb2.contains(".") && inputstr.equals(".")){//8.907
                        return;
                    }
                    if(numb2.equals("0")&&!inputstr.equals(".")){
                        return;
                    }
                    if(numb2.equals("-")&&inputstr.equals(".")){
                        return;
                    }
                    if(numb2.equals("-0")&&!inputstr.equals(".")){
                        return;
                    }
                    numb2=numb2+inputstr;
                    result_et.setText(numb1+lastCommand+numb2);
                }
            }
           // result_et.setText(result_et.getText().toString()+inputstr);
        }
    }

    class OpratorClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Button inputbt=(Button)v;//表示获得了某个按钮 Map<String,Object> mm=new HashMap();
            String inputstr=inputbt.getText().toString();//表示按钮上的内容
            String resultstr=result_et.getText().toString();//表示获取输入框中的内容
            if(firstFlag){
                if(inputstr.equals("-")){
                    numb1=numb1+inputstr;
                    result_et.setText(numb1);
                    firstFlag=false;//为什么这里要赋值为false:防止下次还走if
                }
            }else{
                if(numb1.equals("-")){
                    return;
                }
                if(numb2.equals("")){
                    lastCommand=inputstr;
                    result_et.setText(numb1+lastCommand);
                }
                if(inputstr.equals("=")&&!numb2.equals("")){
                    calc(Double.parseDouble(numb1),Double.parseDouble(numb2));
                    result_et.setText(numb1+lastCommand+numb2+"="+result);
                    lastCommand="";
                }
            }
        }

        private void calc(double dnumb1,double dnumb2) {
            if(lastCommand.equals("+")){
                result=dnumb1+dnumb2;//result=result+parseDouble;
            }
            if(lastCommand.equals("-")){
                result=dnumb1-dnumb2;//result=result-parseDouble;
            }
            if(lastCommand.equals("*")){
                result=dnumb1*dnumb2;//result=result*parseDouble;
            }
            if(lastCommand.equals("/")){
                result=dnumb1/dnumb2;//result=result/parseDouble;
            }
            DecimalFormat df=new DecimalFormat("#.##");
            result=Double.parseDouble(df.format(result));

        }
    }
}