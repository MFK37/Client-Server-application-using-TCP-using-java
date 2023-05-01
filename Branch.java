
public class Branch {
	
	public String branchID;
	public String address;
	public String branchM;
	public int branchSales;
	public double branchRating;
	
	public Branch (String bID, String address, String bManager, int bSales, double bRating) {
		this.branchID = bID;
		this.address = address;
		this.branchM = bManager;
		this.branchSales = bSales;
		this.branchRating = bRating;
	}
}
