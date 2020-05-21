package makeGenerator;


//All code will execute from this class
public class Main{

  public static void main(String[] args){

    //tries to parse arguments
    ArgsParser argsParser;
    try{
      argsParser = new ArgsParser(args);
    } catch(Exception e){
      System.out.println(e);
      return;
    }

    try{
      //create and write the makefile
      MakeFileTemplate mft = new MakeFileTemplate(argsParser);
      mft.write();
    }catch(Exception e){
      System.out.println(e);
      return;
    }
  }
}
