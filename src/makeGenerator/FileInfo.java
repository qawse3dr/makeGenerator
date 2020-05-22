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
    this.path = path;
    this.srcName = file.getName();
    //assume you are using c
    this.objName = file.getName().replace(".c",".o");

    //opens file and looks for dependances
    Scanner in = null;
    try {
      in = new Scanner(file);
      while(in.hasNextLine()){
        String line = in.nextLine();
        //custom include
        if(line.startsWith("#include") && line.contains("\"")){
          //gets value after first "
          includes.add(line.split("\"")[1]);
          //gets the dependances from the header
          getInfoFromHeader(new File(mft.getPath()+mft.getIncludesDir()+"/"+line.split("\"")[1]),mft);
        }
      }
    }catch(Exception e){
      System.out.println(e);
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
          //gets value after first "
          includes.add(line.split("\"")[1]);
          //gets the dependances from the header
          getInfoFromHeader(new File(mft.getPath()+mft.getIncludesDir()+"/"+line.split("\"")[1]),mft);
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
