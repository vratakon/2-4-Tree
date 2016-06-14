package algos.Proj2.TwoFour;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

public class TwoFourMain {
static TwoFourTree tree=new TwoFourTree();			
static Random rd=new Random();	

static int n;	
static int Inc_counter=0; 
static int insertCount=0;
static int deleteCount=0;
static int searchCount=0;
static int SubOperations_total=0;
static int splitcount=0;
static int fusioncount=0;
static int transfercount=0;
	
public static void main(String[] args) {
BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
if(args.length == 0)
{
System.out.print("Enter the value of n: ");
try {
n=Integer.parseInt(reader.readLine());
} catch (Exception e) {
System.out.println("Not a valid input!\nPlease try again..");
System.exit(1);
}
}
else
	n=Integer.parseInt(args[0]);

long startTime = 0;
try {
for(int i=0; i < n; i++) 
tree.add(rd.nextInt(n));

Random distribution=new Random();
startTime=System.nanoTime();
while(Inc_counter<2*n)
{
int subOperations=0;			
double p, d;				
d=(double) (distribution.nextInt(2*n) + 1);
p=d/(2*n);					
int randomValue=rd.nextInt(2*n);

if(p <= 0.4)
{
subOperations=tree.insert(randomValue);
insertCount++;
splitcount=subOperations;
}
else if(p > 0.4 && p <= 0.65){
subOperations=tree.delete(randomValue);
deleteCount++;
fusioncount=subOperations;
}
else{
subOperations=tree.search(randomValue);
searchCount++;
transfercount=subOperations;
}
Inc_counter++;
}

long endTime=System.nanoTime()-startTime;
System.out.println("");
System.out.println("Total No. of Insert operations performed on "+2*n+"[2n] numbers with probability of 0.4: "+ insertCount);
System.out.println("	-->Total sub-operations for Splits: "+ splitcount);
System.out.println("Total No. of Delete operations performed on "+2*n+"[2n] numbers with probability of 0.25: "+ deleteCount);
System.out.println("	-->Total sub-operations for fusions/transfers: "+ fusioncount);
System.out.println("Total No. of Search operations performed on "+2*n+"[2n] numbers with probability of 0.35: "+ searchCount);
System.out.println("	-->Total sub-operations for comparisions: "+ tree.comparecount);
int SubOperations_total=splitcount+fusioncount+tree.comparecount;
System.out.println("Total sub-operations (Splits,Fusions/Transfers & comparision operations): "+SubOperations_total);
System.out.println("");
System.out.print("In-order printout sequence is: ");
System.out.println(tree.inOrderElements(tree.getRootNode()));
System.out.println("Total Execution Time:" + endTime + " nanoseconds");

}catch(Exception e)
{
	main(new String[] {n+""});
}
}
}
