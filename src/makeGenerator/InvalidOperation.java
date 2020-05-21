package makeGenerator;

class InvalidOperation extends Exception{
  char operation = 0;

  public InvalidOperation(char operation){
    this.operation = operation;
  }
  @Override
  public String toString(){
    return "Invalid Operation " + operation;
  }
}
