import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Scanner;
public class ElGamal{
    static BigInteger p, g, beta, a,M,N,r,t,tmp;
    static Random sc = new SecureRandom();
    public static void main(String args[])throws IOException{
    	init();    	
    }
    public static void init() throws IOException{                
        a = new BigInteger("12345678901234567890");
        System.out.println("secretKey = " + a);
        p = BigInteger.probablePrime(64, sc);
        g = new BigInteger("3");
        beta = g.modPow(a, p);
        System.out.println("p = " + p);
        System.out.println("g = " + g);
        System.out.println("beta = " + beta);
                
        Random rand=new Random();
	Scanner scan=new Scanner(System.in);
	System.out.println("Party A:");
	BigInteger R=new BigInteger(rand.nextInt()+""),i;
	//System.out.print("Enter range n:");
	//int n=scan.nextInt();
	System.out.print("Enter M:");
	String s=scan.next();
	M = new BigInteger(s);
	
	System.out.println("Party B:");
	System.out.print("Enter N:");
	s=scan.next();	
	N = new BigInteger(s);
	//k= new BigInteger(64, sc);
        BigInteger h=new BigInteger(rand.nextInt()+"");
	tmp=(M.add(N)).subtract(R);
        //first step encryption party A
	E(tmp,h);
        //second step re-randomization party B
        BigInteger S=new BigInteger(rand.nextInt()+""),r1;
	r1=h.add(S);
        E(tmp,r1);
        //partial decryption-1 party A
        //System.out.println(D(a));
        //partial decryption-2 party B
        System.out.println(D(a).add(R));
   }
   public static void E(BigInteger M,BigInteger k){
   	//BigInteger M = new BigInteger(s);
        //BigInteger k = new BigInteger(64, sc);
        t = M.multiply(beta.modPow(k, p)).mod(p);
        r = g.modPow(k, p);
        //System.out.println("Plaintext = " + M);
        //System.out.println("k = " + k);
        //System.out.println("t = " + t);
        //System.out.println("r = " + r);
   }
   public static BigInteger D(BigInteger a){
   	BigInteger crmodp = r.modPow(a, p);
        BigInteger d = crmodp.modInverse(p);
        BigInteger ad = d.multiply(t).mod(p);
        //System.out.println("\n\n(g^a)^k mod p = " + crmodp);
        //System.out.println("(g^a)^-k mod p = " + d);
        //System.out.println("Decoded message: " + ad);
        return ad;
   }
        //System.out.print("Enter your Big Number message -->");
        //Scanner scan=new Scanner(System.in);
        //String s = scan.next();       

}
