package makeGenerator;

import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.Map;
import java.util.ArrayList;
import java.io.File;
public class MakeFileTemplate{
  private String cFlags = "";
  private String includeDIR = "include";
  private String srcDir = "src";
  private String binDir = "bin";
  private String compiler = "gcc";
  private ArrayList<String> targets = new ArrayList<String>();
  private boolean debugMode = false;
  private boolean verboseMode = false;
  private boolean sharedLib = false;
  private String outputFile = "makefile";
  private String executableName = "a.out";
  /**Path to the file structure.
   *@default ./*/
  private String path = "./";

  /*Creates the template for the makefile*/
  public MakeFileTemplate(ArgsParser parser) throws InvalidOperation{

    for(char cmd : parser.getCommands().toCharArray()){
      switch(cmd){
        //debugMode
        case 'D':
          debugMode = true;
          break;
        //verbose
        case 'V':
          verboseMode = true;
          break;
        //shared lib
        case 'S':
          sharedLib = true;
          break;
        default:
          throw new  InvalidOperation(cmd);
      }
    }
    //flags
    for (Map.Entry<String, String> entry : parser.getFlags().entrySet()) {
      switch(entry.getKey()){
        //outfile
        case "o":
          outputFile = entry.getValue();
          break;
        //filePath to project
        case "p":
          path = entry.getValue();
          break;
        //flags
        case "f":
          cFlags = entry.getValue();
          break;
        //exe name
        case "e":
          executableName = entry.getValue();
          break;
        //compiler
        case "c":
          compiler = entry.getValue();
          break;
        //src dir
        case "s":
          srcDir = entry.getValue();
          break;
        case "b":
          binDir = entry.getValue();
          break;
        case "i":
          includeDIR = entry.getValue();
        default:
          throw new  InvalidOperation(entry.getKey().charAt(0));
      }
    }


  }

  private void getFileStruct(String path){


  }

  /**write to the dest file.*/
  public void write(){
    try{
      //opens file
      FileWriter fileWriter = new FileWriter(outputFile);
      PrintWriter printWriter = new PrintWriter(fileWriter);

      //global vars
      printWriter.printf("CC = %s\n",compiler);
      printWriter.printf("CFLAGS = %s\n",cFlags);
      printWriter.printf("objects = %s\n",String.join(" ",targets));
      printWriter.printf("srcDIR = %s/\n", srcDir);
      printWriter.printf("includeDIR = %s/\n", includeDIR);
      printWriter.printf("binDIR = %s/\n\n", binDir);

      //main executable
      printWriter.printf("$(binDIR)%s:$(objects)\n",executableName);
      printWriter.printf("\t$(CC) $(CFLAGS) -I$(includeDIR) %s $(objects) -o $@", (sharedLib)? "-shared": "");

      //write file
      printWriter.close();

      for
    } catch(Exception e){
      System.out.println(e + " Error Writing To File");
    }
  }
}
