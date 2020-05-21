package makeGenerator;

class InvalidSyntax extends Exception{
  /**the Syntex err*/
  String error;

  public InvalidSyntax(String error){
    this.error = error;
  }
  public String toString(){
    return "InvalidSyntex on " + error;
  }
}
