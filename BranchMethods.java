
public class BranchMethods {
	
	private Branch branch[];
	private int nbBranch;
	
	public BranchMethods () {
		branch = new Branch[10];
		nbBranch = 0;
	}
	public int getNbBranch() {
		return nbBranch;
	}
	public boolean addBranch (Branch b) {
		if (nbBranch == branch.length) 
			return false;
		else {
			branch[nbBranch] = b;
			nbBranch++;
			return true;
		}
	}
	public boolean removeBranch (String branchID) {
		if (nbBranch == 0)
			return false;
		
		else if (nbBranch == 1) {
			if (branch[0].branchID.equals(branchID)) {
				branch[0] = null;
				nbBranch--;
				return true;
			}
			else
				return false;
		}
		
		else {
			for (int i = 0 ; i < nbBranch ; i++) {
				if (branch[i].branchID.equals(branchID)) {
					branch[i] = branch[nbBranch-1];
					branch[nbBranch-1] = null;
					nbBranch--;
					return true;
					
				}
			}
			return false;
		}
	}
	public boolean updateBranch (Branch b, String id) {
		
		if (searchID(id) != -1) {
			branch[searchID(id)] = b;
			return true;
		}
		return false;
		
	}
	public String getBranchID (int idx) {
		return branch[idx].branchID;
	}
	public String getAddress (int idx) {
		return branch[idx].address;
	}
	public String getBranchManager (int idx) {
		return branch[idx].branchM;
	}
	public int getBranchSales (int idx) {
		return branch[idx].branchSales;
	}
	public double getRating (int idx) {
		return branch[idx].branchRating;
	}
	public void printAll() {
		for (int i = 0 ; i < nbBranch ; i++) {
			System.out.println("Branch ID: " + branch[i].branchID + " // Branch manager: " 
						       + branch[i].branchM + " // Branch sales: " + branch[i].branchSales);
		}
	}
	public int searchID (String id) {
	
		for (int i = 0 ; i < nbBranch ; i++) {
			if (branch[i].branchID.equalsIgnoreCase(id))
				return i;
		}
		return -1;
	}
	
}