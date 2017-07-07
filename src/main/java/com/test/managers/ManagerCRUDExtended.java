/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.managers;

import com.test.entity.Company;

/**
 *
 * @author Buy
 */
public interface ManagerCRUDExtended extends ManagerCRUD{
    public void editParent(String companyToEdit,String newParentName) 
                throws Exception;
    public void rename(String curName, String newName);
    public void deleteTree(String rootNameToDelete);
    public String toStringTreeOf(Company company);
    public String getAllCompanyToString();
}
