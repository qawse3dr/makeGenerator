package tester;

//import for junit
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;



//imports for testing
import makeGenerator.ArgsParser;





public class ArgsParserTest{

  @Test
  /**if it doesnt break it passes.*/
  public void launch(){
    System.out.println("Test Launching without Crashing");
    try{
      ArgsParser parser = new ArgsParser(new String[] {"-A","-O"});
      //Failed to parse
    } catch(Exception e){
      fail(e.toString());
    }
    assertTrue("fail",true);
  }
  @Test
  /**Tests if it parses the args correctly.*/
  public void parseCommand(){
    System.out.println("Test parse one command");
    try{
      ArgsParser parser = new ArgsParser(new String[] {"-A"});
      assertTrue(parser.getCommands().equals("A"));
    } catch(Exception e){
      fail(e.toString());
    }
  }

  @Test
  /**Tests if a flag is passed correctly*/
  public void parseFlag(){
    System.out.println("Test parse one command");
    try{
      ArgsParser parser = new ArgsParser(new String[] {"-o","output.txt"});
      parser.getFlags().forEach((key,value)-> {assertEquals("Wrong Key","o",key); assertEquals("Wrong Value","output.txt",value);});
    } catch(Exception e){
      fail(e.toString());
    }
  }
}
