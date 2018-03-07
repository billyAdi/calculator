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