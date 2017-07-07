package com.test.datamodels;

public class SearchCriteria {

	String companyName;
	int earnings;
        String parentName;
        String newName;

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getParentName() {
        return parentName;
    }

    public String getNewName() {
        return newName;
    }

    public void setEarnings(int earnings) {
        this.earnings = earnings;
    }

    public int getEarnings() {
        return earnings;
    }

    @Override
    public String toString() {
        return "------------Company name : "+companyName+" ; "+	earnings+" ; "
        + parentName+ " ;New Name is-----> "+ newName;
    }
    
    

}
