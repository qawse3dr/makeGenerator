package makeGenerator;

import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.Map;
import java.util.ArrayList;
import java.io.File;
public class MakeFileTemplate{
  private String cFlags = "";
  private String includeDIR = "includes";
  private String srcDir = "src";
  private String binDir = "bin";
  private String compiler = "gcc";
  private ArrayList<FileInfo> targets = new ArrayList<FileInfo>();
  private ArrayList<String> dirs = new ArrayList<String>();
  public boolean debugMode = false;
  public boolean verboseMode = false;
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
          System.out.println("DEBUG ON");
          break;
        //verbose
        case 'V':
          verboseMode = true;
          System.out.println("VERBOSE ON");
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
          path = entry.getValue() + "/";
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
          break;
        default:
          throw new  InvalidOperation(entry.getKey().charAt(0));
      }
    }

    //Maps out the file Struct for src
    try{
      getFileStruct(new File(path+srcDir+"/"),null);
    }catch(Exception e){
      System.out.println(e);
    }


  }

  /**Maps out the file structure.
   *
   * @param curPath the file that holds the current file
   * @param pathString the relative path to the src directory
  */
  private void getFileStruct(File curPath, String pathString){
    if(!curPath.exists()){
      return;
    }
    else if(curPath.isDirectory()){
      if(pathString != null){
        if(verboseMode)System.out.println("Dir Found: " + pathString+curPath.getName()+"/");
        dirs.add(pathString+curPath.getName()+"/");
      }
      for(File file : curPath.listFiles()){

        getFileStruct(file,(pathString == null)?"":pathString+curPath.getName()+"/");

      }
    } else{
      targets.add(new FileInfo(curPath,pathString,this));
    }

  }

  /**write to the dest file.*/
  public void write(){
    try{
      //opens file
      FileWriter fileWriter = new FileWriter(path+outputFile);
      PrintWriter printWriter = new PrintWriter(fileWriter);

      //global vars
      printWriter.printf("CC = %s\n",compiler);
      printWriter.printf("CFLAGS = %s\n",cFlags);
      printWriter.printf("srcDIR = %s/\n", srcDir);
      printWriter.printf("includeDIR = %s/\n", includeDIR);
      printWriter.printf("binDIR = %s/\n\n", binDir);
      printWriter.printf("objects = ");
      for(FileInfo info : targets){
        printWriter.printf("$(binDIR)%s ",info.getObjName());
      }
      printWriter.printf("\n\n");

      //main executable
      printWriter.printf("all:init $(binDIR)%s\n\n",executableName);

      printWriter.printf("init:\n");
      for(String dir : dirs){
        printWriter.printf("\tmkdir -p $(binDIR)" + dir + "\n");
      }
      printWriter.printf("\n");

      printWriter.printf("$(binDIR)%s:$(objects)\n",executableName);
      printWriter.printf("\t$(CC) $(CFLAGS) -I$(includeDIR) %s $(objects) -o $@", (sharedLib)? "-shared": "");

      //write the targets
      for(FileInfo info : targets){
        printWriter.printf("\n$(binDIR)%s: %s %s\n", info.getObjName(), info.getSrcName(), info.getIncludes());
        printWriter.printf("\t$(CC) -c $(CFLAGS) %s -I$(includeDIR) %s -o $@\n",info.getFlags(), info.getSrcName());

      }
      //write the clean function
      printWriter.printf("\n\nclean:\n");
      printWriter.printf("\trm -rf $(binDIR)*\n");
      //write run and memtest
      printWriter.printf("\nrun: all\n");
      printWriter.printf("\t./$(binDIR)%s\n",executableName);

      printWriter.printf("\nmemtest: all\n");
      printWriter.printf("\tvalgrind ./$(binDIR)%s\n",executableName);
      //write file
      printWriter.close();


    } catch(Exception e){
      System.out.println(e + " Error Writing To File");
    }
  }

  //getter and setters
  public void setPath(String path){
    this.path = path;
  }
  public String getPath(){
    return path;
  }

  public String getIncludesDir(){
    return includeDIR;
  }
}
