package se_lab1;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestRandomWalk {

	//Step 1 get the test Object
	
	Lab1_test testLab = new Lab1_test("C:/Users/bubbl/Desktop/test.txt");
	
	//Step 2 begin test in some function
	
	@Test
	public void testCase1() {
		String res = testLab.tc.randomWalk();
		System.out.println(res);
	}

}
