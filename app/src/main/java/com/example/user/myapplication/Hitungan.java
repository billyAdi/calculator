package com.example.user.myapplication;

import android.util.Log;

import java.util.ArrayList;

public class Hitungan{
    static boolean nextAngka=true;
    static boolean divZero=false;
    public Hitungan(){

    }

    public static double hitung(double angkaAwal, String operator, double angkaNext){
        System.out.println("masuk itungan");
        double result = 0;
        System.out.println(angkaAwal+"    aa"+angkaNext);
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
        System.out.println(result+"result");
        return result;


    }

    public static String hasilHitung(ArrayList<String> str){
        double temp = cekhitungHasil(str);
        if(!divZero){
            return  temp+"";
        }
        else{
            return "Dibagi 0";
        }

    }

    public static double cekhitungHasil(ArrayList<String> str) {
        divZero=false;
        int i = 0;
        double angkaTersimpan = 0;
        double angkaYangAkanDihitung = Integer.MIN_VALUE;
        String operator = "";
        boolean adaIsi=false;
        for(int j = 0; j <str.size()&& !divZero;j++){
            System.out.println(str.get(j));
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

    public static boolean isValid(ArrayList<String> str){
        nextAngka=true;
        for(int i = 0;i<str.size();i++){
            if (valid(str.get(i))){

            }
            else{
                return false;
            }
        }
        if(nextAngka){
            return false;
        }
        else{
            return true;
        }
    }

    public static boolean valid(String next){
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
}
/**boolean nextAngka=true;

public boolean parentheses(String bacaan){
    Stack simbol = new Stack();
    for(int i = 0 ; i<bacaan.length();i++){
      if(bacaan.charAt(i)=='('){
        simbol.push(bacaan.charAt(i);
        }
        else if(bacaan.charAt(i)==')'){
          if(simbol.isEmpty()){
            return false;
          }
          else{
            simbol.pop();
          }
      }
    }
    if(simbol.isEmpty()){
    return true;
    }
    else{
      return false;
      }
    
    
}
         

         
         
*/
