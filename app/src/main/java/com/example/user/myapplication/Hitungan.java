package com.example.user.myapplication;

import java.util.ArrayList;

public class Hitungan{
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
                result = angkaAwal/angkaNext;
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

    public static double hasilHitung(ArrayList<String> str) {
        int i = 0;
        double angkaTersimpan = 0;
        double angkaYangAkanDihitung = Integer.MIN_VALUE;
        String operator = "";
        boolean adaIsi=false;
        for(int j = 0; j <str.size();j++){
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
         
        public boolean valid(char next){
          if(next=='+'|| next=='-' || next == '/' || next == '*'){
                if(nextAngka==true){
                    return false;   
                }
              else{
                nextAngka=true;
                  return true;
              }
          }
            else{
                if(nextAngka==true){
                    nextAngka=false;
                    return true;
                }
                }
        }
         
         
*/