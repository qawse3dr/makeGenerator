package makeGenerator;


//All code will execute from this class
public class Main{

  public static void main(String[] args){
    for(String str : args){
      System.out.println(str);
    }

    //tries to parse arguments
    ArgsParser argsParser;
    try{
      argsParser = new ArgsParser(args);
    } catch(Exception e){
      System.out.println(e);
      return;
    }

    //create and write the makefile
    MakeFileTemplate mft = new MakeFileTemplate(argsParser);
    mft.write();
  }
}
