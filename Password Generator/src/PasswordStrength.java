import java.math.BigInteger;

public class PasswordStrength {
	private BigInteger PerSecond;
	private BigInteger Second;
	private BigInteger Minute;
	private BigInteger Hours;
	private BigInteger Days;
	
	public PasswordStrength() {
	
	}
	
	BigInteger TimeToCrackPassword(int length, int NumberOfValues) {
		Integer size,values;
		size = new Integer(length);
		values = new Integer(NumberOfValues);
		
		PerSecond = new BigInteger("1000000000000");
		Second = new BigInteger("60");
		Minute = new BigInteger("60");
		Hours = new BigInteger("24");
		Days = new BigInteger("365");
		
		BigInteger base = BigInteger.valueOf(values.intValue());
		
		//System.out.println(base);
		
		base = base.pow(size);
		
		//System.out.println(base);
		
		base = base.divide(PerSecond);
		//System.out.println(base + " seconds");
		
		base = base.divide(Second);
		//System.out.println(base + " minutes");
		
		base = base.divide(Minute);
		//System.out.println(base + " hours");
		
		base = base.divide(Hours);
		//System.out.println(base + " days");
		
		base = base.divide(Days);
		//System.out.println(base + " years");
		
		return base;
	}
}
