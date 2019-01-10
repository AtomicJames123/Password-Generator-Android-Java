import java.security.SecureRandom;
import java.util.*;

public class PasswordCreator {
	private static final String Lower = "abcdefghijklomnpqrstuvwxyz";
	private static final String Upper = "ABCDEFGHIJKLOMNPQRSTUVWXYZ";
	private static final String SpecialCharacters = "!@#$%&*()_+-=[]|,./?><";
	private String Values = "0123456789";
	private boolean HasLower = false;
	private boolean HasUpper = false;
	private boolean HasSpecialCharacters = false;
	private int StartingValue;
	private int NumberOfLower = 26;
	private int NumberOfUpper = 26;
	private int NumberOfSpecial = 23;
	
	
	private int size = 0;
	
	public PasswordCreator(int length) {
		this.HasLower = true;
		this.HasUpper = true;
		this.HasSpecialCharacters = false;
		StartingValue = 10;
		size = length;
	}
	
	boolean setSpecial(boolean value) {
		return (HasSpecialCharacters = value);
	}
	
	boolean setLower(boolean value) {
		return (HasLower = value);
	}
	
	boolean setUpper(boolean value) {
		return (HasUpper = value);
	}
	
	int setSize(int value) {
		return (size = value);
	}
	
	public String Generator() {
		if (HasSpecialCharacters) {
			Values = Values + SpecialCharacters;
			StartingValue = StartingValue + NumberOfSpecial;
		}
		
		if (HasLower) {
			Values = Values + Lower;
			StartingValue = StartingValue + NumberOfLower;
		}
		
		if (HasUpper) {
			Values = Values + Upper;
			StartingValue = StartingValue + NumberOfUpper;
		}
		
		StringBuilder sb = new StringBuilder();
		
		Random random = new SecureRandom();
		
		for (int i = 0; i < size; i++) {
			sb.append(Values.charAt(random.nextInt(Values.length())));
		}
		
		Values = "123456789";
		
		return sb.toString();	
	}
}
