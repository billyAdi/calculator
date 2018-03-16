package com.example.user.myapplication;

import android.util.Log;

import java.util.ArrayList;
import java.util.Stack;

public class Hitungan{
    static boolean nextAngka=true;
    static boolean divZero=false;

    public Hitungan(){

    }

    public  double hitung(double angkaAwal, String operator, double angkaNext){
        //System.out.println("masuk itungan");
        double result = 0;
       // System.out.println(angkaAwal+"    aa"+angkaNext);
        if(angkaNext!=Integer.MIN_VALUE){
            if(operator.equals("+")){
                result = angkaAwal+angkaNext;
            }
            else if(operator.equals("-")){
                result = angkaAwal-angkaNext;
            }
            else if(operator.equals("/")){
                if(angkaNext==0){
                    divZero=true;
                }
                else {
                    result = angkaAwal / angkaNext;
                }
            }
            else if(operator.equals("*")){
                result = angkaAwal*angkaNext;
            }
            else{
                result=angkaAwal;
            }
        }
        else{
            result=angkaAwal;
        }
       // System.out.println(result+"result");
        return result;


    }

    public  String hasilHitung(ArrayList<String> str){
        // double temp = cekhitungHasil(str);

        String s=new String();
        for (int i=0;i<str.size();i++){
            //Log.d("Nopest nope", str.get(i));
            s+=str.get(i);
        }

        double temp=evalInfix(s);
        return  temp+"";
        /** if(!divZero){
         return  temp+"";
         }
         else{
         return "Dibagi 0";
         }
         */
    }

    public  double cekhitungHasil(ArrayList<String> str) {
        divZero=false;
        int i = 0;
        double angkaTersimpan = 0;
        double angkaYangAkanDihitung = Integer.MIN_VALUE;
        String operator = "";
        boolean adaIsi=false;
        for(int j = 0; j <str.size()&& !divZero;j++){
          //  System.out.println(str.get(j));
            if(!(str.get(j).equals("+")  || str.get(j).equals("-")  || str.get(j).equals("/") || str.get(j).equals("*"))){
                if(!adaIsi){
                    angkaTersimpan=Integer.parseInt(str.get(j));
                    adaIsi=true;
                }
                else{
                    angkaYangAkanDihitung=Integer.parseInt(str.get(j));
                    angkaTersimpan=hitung(angkaTersimpan,operator,angkaYangAkanDihitung);}
                angkaYangAkanDihitung=Integer.MIN_VALUE;
            }
            else{
                operator = str.get(j);

            }
        }
        return angkaTersimpan;
    }

    public  boolean isValid(ArrayList<String> str){
        nextAngka=true;
        String s=new String();
        for (int i=0;i<str.size();i++){
            s+=str.get(i);
        }
        boolean isBalance=isBalance(s);

        for(int i = 0;i<str.size();i++){
            if(str.get(i).equals("(")||str.get(i).equals(")")){
                continue;
            }
            else if (valid(str.get(i))){

            }
            else{
                return false;
            }
        }

        if(nextAngka){
            return false;
        }
        else{
            if(isBalance){return true;}
            else{return false;}
        }
    }

    public  boolean isBalance(String str){
        Stack<Character> stack=new Stack<Character>();
        for (int i=0;i<str.length();i++){
            char c=str.charAt(i);
            if(c=='('){
                stack.push(c);
            }
            else if(c==')'){
                if(stack.isEmpty() || stack.pop() != '(') {
                    return false;
                }
            }

        }
        return stack.empty();
    }

    public  boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public  boolean valid(String next){
        Log.d("Angka",next+" "+nextAngka);
        if(next.contains("+")|| next.contains("-") || next.contains("/") || next.contains("*")){
            if(nextAngka==true){
                Log.d("AB",next);
                return false;
            }
            else{
                Log.d("AW",next);
                nextAngka=true;
                return true;
            }
        }
        else{
            if(nextAngka==true){
                Log.d("WW",next);
                nextAngka=false;
                return true;
            }
            else{
                Log.d("WB",next);
                nextAngka=true;
                return false;
            }
        }
    }



    private static final String operators = "-+/*";
    private static final String operands = "0123456789|";

    public  double evalInfix(String infix) {
        return evaluatePostfix(convert2Postfix(infix));
    }

    public  String convert2Postfix(String infixExpr) {
        char[] chars = infixExpr.toCharArray();
        Stack<Character> stack = new Stack<Character>();
        StringBuilder out = new StringBuilder(infixExpr.length());

        for (char c : chars) {

            if (isOperator(c)) {

                out.append("|");
                while (!stack.isEmpty() && stack.peek() != '(') {
                    if (operatorGreaterOrEqual(stack.peek(), c)) {
                        out.append(stack.pop());

                    } else {
                        break;
                    }
                }
                stack.push(c);
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    out.append(stack.pop());
                }
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            } else if (isOperand(c)) {
                out.append(c);

            }
        }
        while (!stack.empty()) {
            out.append(stack.pop());

        }
        return out.toString();
    }

    public  double evaluatePostfix(String postfixExpr) {
        Log.d("Nopest Nope", "evaluatePostfix: "+postfixExpr);
        char[] chars = postfixExpr.toCharArray();
        String res = "";
        Stack<Double> stack = new Stack<Double>();
        for (char c : chars) {
            if (isOperand(c)) {
                if(c!='|'){
                    res=res+c;
                }
                else{
                    //stack.push((double)(c - '0'));

                    stack.push(Double.parseDouble(res));
                    res="";
                }

                Log.d("Nopest Nope", "*****: "+res);


            } else if (isOperator(c)) {
                //stack.push(Double.parseDouble(res));

                if(res!=""){
                    stack.push(Double.parseDouble(res));
                    res="";
                }
                double op1 = stack.pop();
                double op2 = stack.pop();
                double result;
                switch (c) {
                    case '*':
                        result = op1 * op2;
                        stack.push(result);


                        break;

                    case '/':
                        result = op2 / op1;
                        stack.push(result);

                        break;
                    case '+':
                        result = op1 + op2;
                        stack.push(result);

                        break;
                    case '-':
                        result = op2 - op1;
                        stack.push(result);

                        break;
                }
            }
        }
        return stack.pop();
    }
    private  int getPrecedence(char operator) {
        int ret = 0;
        if (operator == '-' || operator == '+') {
            ret = 1;
        } else if (operator == '*' || operator == '/') {
            ret = 2;
        }
        return ret;
    }
    private  boolean operatorGreaterOrEqual(char op1, char op2) {
        return getPrecedence(op1) >= getPrecedence(op2);
    }

    private  boolean isOperator(char val) {
        return operators.indexOf(val) >= 0;
    }

    private  boolean isOperand(char val) {
        return operands.indexOf(val) >= 0;
    }
}
