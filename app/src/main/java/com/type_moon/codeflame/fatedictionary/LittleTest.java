package com.type_moon.codeflame.fatedictionary;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.type_moon.codeflame.fatedictionary.Character.CharacterDataBase;

/**
 * 计划：
 * 功能：
 * 1、测试：题目有四个类型（生年、卒年、籍贯 分数，正确一次加10分，错一次减5分，最后记录前三个分数（有余力时实现）；
 * 函数：
 * String[] getCandidateAnswers(String dataName,String answer);返回准备好的候选答案;
 *
 * Created by ZhangMingHua on 2017/11/10.
 */

public class LittleTest extends AppCompatActivity{
    /*游标，用于存查询的返回结果*/
    private Cursor data;
    /*资料总数*/
    private int PERSONNUMBER;
    /*问题：在initData方法中合成*/
    private String question;
    /*答案*/
    private String answer;
    /*候选答案:三个*/
    private String[] candidateAnswers;
    /*各个问题的 ID*/
    private boolean isOkay;
    final int BIRTH = 0;
    final int DEATH = 1;
    final int ORIGO = 2;
    final int ARMY = 3;
    /*对应问题所需要的数据名称*/
    private String[] dataName = new String[]{"birth","death","origo","army"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.little_test);
        initData();
        runTest();
    }
    public void initData(){
        /*初始化候选答案*/
        this.candidateAnswers = new String[3];
        /*获取游标数据：整个表中的人物数据*/
        data = CharacterDataBase.getInstances(LittleTest.this).query();
        /*这里获取sanguo表中共存可几个人物的数据：获取行数*/
        this.PERSONNUMBER = data.getCount();

        /*随机出所要选择的人物ID：这个ID也是游标的绝对位置，从0开始*/
        int dataPosition = (int) (Math.random() * PERSONNUMBER);

        /*定位到要获取数据的位置*/
        data.moveToPosition(dataPosition);
        /*获取名字（name）所在的列，返回列的位置*/
        int nameCulnmsPsotion = data.getColumnIndex("name");
        /*这里根据随机出来的ID而选出名字*/
        String name = data.getString(nameCulnmsPsotion);
        /*随机出要问哪一个问题*/
        int QUESTIONNUMBER = 4;
        int questionID = (int) (Math.random() * QUESTIONNUMBER);
        /*获取相应的答案*/
        this.answer = data.getString(data.getColumnIndex(dataName[questionID]));
        /*设置问题*/
        readyQuestion(name, questionID);
        /*准备候选答案*/
        readyCandidateAnswers(dataName[questionID]);
    }
    public void runTest(){
        /*获取控件的视图*/
        TextView questionView = findViewById(R.id.QuestionText);

        RadioButton caseA = findViewById(R.id.CaseA);
        RadioButton caseB = findViewById(R.id.CaseB);
        RadioButton caseC = findViewById(R.id.CaseC);

        final Button submitButton = findViewById(R.id.SubmitAnswer);
        final Button exitTestButton = findViewById(R.id.exitTest);

        final RadioGroup answerCaseGroup = findViewById(R.id.AnswerCase);

        /*设置控件的文字信息*/
        questionView.setText(question);
        caseA.setText(candidateAnswers[0]);
        caseB.setText(candidateAnswers[1]);
        caseC.setText(candidateAnswers[2]);
        /*用来存用户的答案*/
        final String[] userAnswer = new String[]{"NoAnswer"};
        /*获取用户选择的答案*/
        answerCaseGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                /*用户选择的RadioButtion id*/
                int chosedRadioButtonID = group.getCheckedRadioButtonId();
                RadioButton chosedRadioButton = findViewById(chosedRadioButtonID);
                userAnswer[0] = chosedRadioButton.getText().toString();
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton tempRadioButton = answerCaseGroup.findViewById(answerCaseGroup.getCheckedRadioButtonId());
                /*比较答案是否正确*/
                if(userAnswer[0].equals("NoAnswer")){
                    Snackbar.make(submitButton, "你还没有选择答案！", Snackbar.LENGTH_SHORT).show();
                }else if(userAnswer[0].equals(answer)){
                    Snackbar.make(submitButton, "你的答案是正确的！", Snackbar.LENGTH_SHORT).show();
                    tempRadioButton.setChecked(false);
                    //正确后继续运行测试，只到用户推出程序。
                    initData();
                    runTest();
                }else{
                    tempRadioButton.setChecked(false);
                    Snackbar.make(submitButton, "你选择了错误的答案！", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        //设置退出按钮
        exitTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder exitTest = new AlertDialog.Builder(LittleTest.this);
                exitTest.setTitle("是否退出？");
                exitTest.setPositiveButton("退出测试", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*推出测试*/
                        exitLillteTest();
                    }
                });
                exitTest.setNegativeButton("继续测试", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Snackbar.make(exitTestButton, "你选择了继续测试", Snackbar.LENGTH_SHORT).show();
                    }
                });
                exitTest.show();
            }
        });
    }

    /**
     * 这个函数保证候选答案中的正确答案插入的位置是随机的。
     * @param candidateAnswersT 候选答案数组，三个元素。
     */
    public void setCandidateAnswers(String[] candidateAnswersT){
        /*随机出一个位置，并把正确答案放在该位置*/
        int position = (int)(Math.random() * 3);
        this.candidateAnswers[position] = candidateAnswersT[0];
        int temp = 1;
        for(int i = 0 ;i < 3 ; i++){
            if(position != i){
                this.candidateAnswers[i] = candidateAnswersT[temp];
                temp++;
            }
        }
    }

    /**
     * 这个函数用于从游标中提取信息。
     * @param dataName 传入属性的名称返回相应的数据。
     * @return 返回一个对应属性的数据。
     */
    public String getData(String dataName){
        data.moveToPosition((int)(Math.random() * PERSONNUMBER));
        return data.getString(data.getColumnIndex(dataName));
    }

    public void readyQuestion(String personName, int questionID){
        isOkay = true;
        switch (questionID){
            case BIRTH:
                question = personName + "在哪一年出生？";
                break;
            case DEATH:
                question = personName + "在哪一年死亡？";
                break;
            case ORIGO:
                question = personName+"的籍贯是？";
                break;
            case ARMY:
                question = personName  + "所效忠的势力是？";
                break;
            default:
                isOkay = false;
                break;
        }
    }
    /**
     *准备候选答案。
     * @param dataName 是要操作那个数据
     */
    public void readyCandidateAnswers(String dataName){
        /*定义一个数组，用于暂存候选答案*/
        String[] tempCandidateAnswers = new String[3];
        /*存随机出来的数据*/
        String tempData;
        tempCandidateAnswers[0]  = this.answer;
        /*准备其他两个候选答案*/
        /*第一个*/
        tempData = getData(dataName);
        while(tempData.equals(tempCandidateAnswers[0])){
            tempData = getData(dataName);
        }
        tempCandidateAnswers[1] = tempData;
        tempData = getData(dataName);
        /*使用其他的判断方式*/
        isOkay = false;
        while (!isOkay){
            isOkay = true;
            for(int i=0;i<2;i++){
                if(tempData.equals(tempCandidateAnswers[i])){
                    isOkay = false;
                }
            }
            if(isOkay){
                break;
            }
            tempData = getData(dataName);
        }
        tempCandidateAnswers[2] = tempData;
        for(int i=0;i<3;i++){
            if(tempCandidateAnswers[i].equals("0")){
                tempCandidateAnswers[i] = "未知";
            }
        }
        if(this.answer.equals("0")){
            this.answer = "未知";
        }
        setCandidateAnswers(tempCandidateAnswers);
    }

    public void exitLillteTest(){
        Intent mainPage = new Intent();
        mainPage.setClass(LittleTest.this,MainActivity.class);
        startActivity(mainPage);
    }
}
