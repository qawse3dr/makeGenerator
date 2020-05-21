package tester;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TesterMain{


  //Tester main used to run the test harness
  public static void main(String[] args){
    System.out.println("Make Generator Test Harness");
    System.out.println("----------------------------\n");

    System.out.println("ArgsParser's Test");
    System.out.println("-----------------");
    Result res = JUnitCore.runClasses(ArgsParserTest.class);

    System.out.println("\nNumber of Failed Tests = " + res.getFailureCount());
    if(res.getFailureCount() > 0){
      System.out.println("Failed Test");
      System.out.println("-----------");
      for(Failure fail: res.getFailures()){
        System.out.println(fail.toString());
      }
    }



  }
}
