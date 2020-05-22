package tester;

//for testing
import makeGenerator.*;

//import for junit
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

public class MakeFileTemplateTest{

  MakeFileTemplate mft;

  @Test
  /**Tests a valid input giving output file*/
  public void validOutputFile(){
    System.out.println("test parsing of valid -o ouput");
    //passes if it doesnt crash
    try{
      mft = new MakeFileTemplate(new ArgsParser(new String[]{"-o","output.txt"}));


    }catch(Exception e){
      fail("Failed to parse -o output.txt");
    }
  }

  @Test
  //Tests writing to a file output.txt
  public void testWriteNoCrash(){
    System.out.println("Tests write doesnt crash");
    try{
      mft = new MakeFileTemplate(new ArgsParser(new String[]{"-o","output.txt"}));

      mft.write();
    }catch(Exception e){
      fail(e +"Failed to write to file");
    }

  }

  @Test


}
