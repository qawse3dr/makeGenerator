package makeGenerator;

import java.util.Scanner;
import java.io.File;
public class MakeConfig{

  private String customFunctions = "";
  private String customVars = "";
  private String customIfs = "";
  private String customInitFunctions = "";
  public MakeConfig(String url){
    Scanner in;

    //open file
    try{
      in = new Scanner(new File(url));
    } catch(Exception e){
      System.out.println("Failed to read config file");
      return;
    }

    String line;
    while(in.hasNextLine()){
      line = in.nextLine();

      //look for vars with var prefix
      if(line.startsWith("var")){
        //copies it over to custom vars
        customVars += line.substring(4, line.length()-1) + "\n";

      //look for custom ifs with @if and @endif
      }else if(line.startsWith("@if")){
        while(in.hasNextLine() && !(line = in.nextLine()).startsWith("@endif")){
          customIfs += line + "\n";
        }
        customIfs += "\n";
      }else if(line.startsWith("@fun")){
        //look for custom functions with @fun and @endfun
        while(in.hasNextLine() && !(line = in.nextLine()).startsWith("@endfun")){
          customFunctions += line + "\n";
        }
        customFunctions += "\n";
      }else if(line.startsWith("@initfun")){
        //look for custom functions with @fun and @endfun
        while(in.hasNextLine() && !(line = in.nextLine()).startsWith("@endinitfun")){
          customInitFunctions += line + "\n";
        }
        customInitFunctions += "\n";
      }else{
        if(!line.isEmpty()){
          System.out.println("Unknown cmd " + line);
        }
      }

    }



    //close file
    in.close();
  }

  public String getCustomFunctions(){
    return customFunctions;
  }

  public String getCustomVars(){
    return customVars;
  }

  public String getCustomIfs(){
    return customIfs;
  }

  public String getInitFunctions(){
    return customInitFunctions;
  }

}
