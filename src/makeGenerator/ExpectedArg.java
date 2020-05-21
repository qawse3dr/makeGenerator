package makeGenerator;

/**Error ocours if value was expected but not given.*/

class ExpectedArg extends Exception{
  public String toString(){
    return "Error: Expected Value";
  }
}
