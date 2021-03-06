package makeGenerator;
import java.util.HashMap;

/**FORMATING FOR ARGS
 -UPPER  = A stand alone command for example V for Verbos or D for Debug
 -lower is define as an option and will be followed by a value for example (-o fileName) gives the output file
 upper options can be chainned like this -DV or seperate like -D -V
 but lowecase can not for example -os is not valid
/**Parsers command line args given as a string array*/
public class ArgsParser{

  /**holds all upper case commands into a single string.*/
  private String commands = "";
  /**holds flags that take a value.*/
  private HashMap<String,String> flags = new HashMap<String,String>();

  /**converts command line args to flags and commands.
   *
   * @param args given argements from cli.
  */
  public ArgsParser(String[] args) throws ExpectedArg,InvalidSyntax{


    //if it true that means that it is expecting a value as the arg
    boolean isValue = false;
    //use to hold the operation for the flags
    String operation = "";

    for(String string : args){


      if(isValue){
        flags.put(operation,string);
        isValue = false;

      }
      else if(string.charAt(0) == '-' && string.length() > 1){
        for(int i = 1; i < string.length(); i++){

          //lower case can only be in index 1 else its an error
          if(Character.isLowerCase(string.charAt(i)) && i == 1){


            isValue = true;
            operation = string.substring(i);
            break;


          } else if(Character.isUpperCase(string.charAt(i))){
            commands += string.charAt(i);
          //invalid syntex
          } else{
            throw new InvalidSyntax(string);
          }
        }
      } else{
        throw new InvalidSyntax(string);
      }
    }

    //error there should have been another value
    if(isValue){
      throw new ExpectedArg();
    }

  }

  public String getCommands(){
    return commands;
  }

  public HashMap<String,String> getFlags(){
    return flags;
  }


}
