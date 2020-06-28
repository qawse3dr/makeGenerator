package makeGenerator;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class FileInfo {
  String flags = "";
  String objName = "";
  String path = "";
  String srcName = "";
  ArrayList<String> includes = new ArrayList<String>();

  /**Gets info Based on a given file.
   *
   * @param file given file that info will be gotten from
   * @param path given path to file
  */
  public FileInfo(File file,String path,MakeFileTemplate mft){

    //verbose output
    if(mft.verboseMode){
      System.out.println("Found: " + file.getName());
    }
    this.path = path;
    this.srcName = file.getName();
    //assume you are using c
    if(file.getName().endsWith(".c")){
      this.objName = file.getName().replace(".c",".o");
    } else if(file.getName().endsWith(".cpp")){
      this.objName = file.getName().replace(".cpp",".o");

    }

    //opens file and looks for dependances
    Scanner in = null;
    try {
      in = new Scanner(file);
      while(in.hasNextLine()){
        String line = in.nextLine();
        //custom include
        if(line.startsWith("#include") && line.contains("\"")){
          if(mft.verboseMode){
            System.out.println("\tFound Header: " + line.split("\"")[1]);
          }
          //makes sure it is not already in the include folder
          if(!includes.contains(line.split("\"")[1])){
            includes.add(line.split("\"")[1]);
          }
          //gets the dependances from the header
          getInfoFromHeader(new File(mft.getPath()+mft.getIncludesDir()+"/"+line.split("\"")[1]),mft);
        }
        else if(line.toLowerCase().startsWith("/*make")){
          if(mft.verboseMode){
            System.out.println("\tFound MakeGenerator cmds in " +srcName);
          }
          while(in.hasNextLine() && !line.trim().endsWith("*/")){
            line = in.nextLine();
            if(line.contains("flags:")){
              flags += line.split("flags:")[1];
              if(mft.verboseMode)System.out.println("Add flag " + line.split("flags:")[1] + " in " + srcName);
            } else if(line.contains("name:")){
              objName = line.split("name:")[1].trim();
              if(mft.verboseMode)System.out.println("new name " + line.split("flags:")[1] + " in " + srcName);
            }

          }
        }
      }
    }catch(Exception e){
      if(mft.debugMode){
        System.out.println(e);
      }
    } finally{
      if(in != null)in.close();
    }


  }

  /**used to get info from a header.*/
  private void getInfoFromHeader(File file,MakeFileTemplate mft){
    //opens file and looks for dependances
    Scanner in = null;
    try {
      in = new Scanner(file);
      while(in.hasNextLine()){
        String line = in.nextLine();
        //custom include
        if(line.startsWith("#include") && line.contains("\"")){
          if(mft.verboseMode){
            System.out.print("\tFound Header: " + line.split("\"")[1]);
            if(!includes.contains(line.split("\"")[1])) System.out.println(": Already Found");
            else System.out.println();
          }
          //makes sure it is not already in the include folder
          if(!includes.contains(line.split("\"")[1])){
            includes.add(line.split("\"")[1]);
            //gets the dependances from the header
            getInfoFromHeader(new File(mft.getPath()+mft.getIncludesDir()+"/"+line.split("\"")[1]),mft);
          }

        }
        else if(line.toLowerCase().startsWith("/*make")){
          if(mft.verboseMode){
            System.out.println("\tFound MakeGenerator cmds in " +srcName);
          }
          while(in.hasNextLine() && !line.trim().endsWith("*/")){
            line = in.nextLine();
            if(line.contains("flags:")){
              flags += line.split("flags:")[1];
              if(mft.verboseMode)System.out.println("Add flag " + line.split("flags:")[1] + " in " + srcName);
            } else if(line.contains("name:")){
              objName = line.split("name:")[1].trim();
              if(mft.verboseMode)System.out.println("new name " + line.split("flags:")[1] + " in " + srcName);
            }

          }
        }
      }
    }catch(Exception e){
      System.out.println(e);
    } finally{
      if(in != null)in.close();
    }
  }
  /**gets the objName of the file.
   *
   * @return the obj name
  */
  public String getObjName(){
    return path+objName;
  }

  /**gets the flags.
   *
   * @return gets the flags
  */
  public String getFlags(){
    return flags;
  }

  /**gets the include dependances.
   *
   * @return the includes as a string
  */
  public String getIncludes(){
    String includeString = "";
    for(String string : includes){
      includeString += "$(includeDIR)" + string + " ";
    }
    return includeString;
  }

  /**Gets the name of the src file.*/
  public String getSrcName(){
    return "$(srcDIR)" +path + srcName;
  }

}
