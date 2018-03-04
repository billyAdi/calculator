public boolean parentheses(String bacaan){
    Stack simbol = new Stack();
    for(int i = 0 ; i<bacaan.length();i++){
      if(bacaan.charAt(i)==(){
        simbol.push(bacaan.charAt(i);
        }
        else if(bacaan.charAt(i)==)){
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
