package se_lab1;

/**
 * @author bubbl
 *
 */
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class TestQueryBridgeWords {

	//Step 1 get the test Object
	
	Lab1_test testLab = new Lab1_test("C:/Users/bubbl/Desktop/test.txt");
	
	//Step 2 begin test in some function
   
	@Test
	public void testCase1() {
		String word1 = "health";
		String word2 = "provider";
		String wantWord = "care";
		String wantRes = "The bridge words from "+word1+" to "+word2+" are: "+wantWord+".";
		String result = testLab.t.queryBridgeWords(word1, word2);
		assertEquals(wantRes,result);
	}
	
	@Test
	public void testCase2() {
		String word1 = "health";
		String word2 = "";
		String wantRes = "No "+word1+" or "+word2+" in the graph!";
		String result = testLab.t.queryBridgeWords(word1, word2);
		assertEquals(wantRes,result);
	}
	
	@Test
	public void testCase3() {
		String word1 = "health";
		String word2 = "!!!";
		String wantRes = "No "+word1+" or "+word2+" in the graph!";
		String result = testLab.t.queryBridgeWords(word1, word2);
		assertEquals(wantRes,result);
	}

	@Test
	public void testCase4() {
		String word1 = "health";
		String word2 = "specify";
		String wantRes = "No "+word1+" or "+word2+" in the graph!";
		String result = testLab.t.queryBridgeWords(word1, word2);
		assertEquals(wantRes,result);
	}
	
	@Test
	public void testCase5() {
		String word1 = "my";
		String word2 = "dual";
		String wantRes = "No bridge words from "+word1+" to "+word2+"!";
		String result = testLab.t.queryBridgeWords(word1, word2);
		assertEquals(wantRes,result);
	}

	@Test
	public void testCase6() {
		String word1 = "have";
		String word2 = "with";
		String wantWord1 = "worked";
		String wantWord2 = "had";
		String wantRes = "The bridge words from "+word1+" to "+word2+" are: "+wantWord1 + " and "  +wantWord2+".";
		String result = testLab.t.queryBridgeWords(word1, word2);
		assertEquals(wantRes,result);
	}
	
}