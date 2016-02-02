package edu.gatech.oad.antlab.person;

/**
 *  A simple class for person 5
 *  returns their name and a
 *  modified string 
 *  
 *  @author Bob
 *  @version 1.1
 */
public class Person5 {
  /** Holds the persons real name */
  private String name;
  	/**
	 * The constructor, takes in the persons
	 * name
	 * @param pname the person's real name
	 */
  public Person5(String pname) {
    name = pname;
  }
  	/**
	 * This method should take the string
	 * input and return its characters rotated
	 * 2 positions.
	 * given "gtg123b" it should return
	 * "g123bgt".
	 *
	 * @param input the string to be modified
	 * @return the modified string
	 */
	private String calc(String input) {
	  char[] inputChar = input.toCharArray();
	  char[] newChar = new char[inputChar.length];
	  int counter = 0;
	  for (int i = 0; i < newChar.length; i++) {
	  	if (i == 1) {
	  		newChar[newChar.length - 1] = inputChar[i];
	  	} else if (i == 2) {
	  		newChar[newChar.length - 2] = inputChar[i];
	  	} else {
	  		newChar[counter] = inputChar[i];
	  		counter++;
	  	}
	  }
	  String answer = "";
	  for (int i = 0; i < newChar.length; i++) {
	  	answer = answer + newChar[i];
	  }

	  return answer;
	}
	/**
	 * Return a string rep of this object
	 * that varies with an input string
	 *
	 * @param input the varying string
	 * @return the string representing the 
	 *         object
	 */
	public String toString(String input) {
	  return name + calc(input);
	}

}
