/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.managers.dao;

import com.test.entity.Company;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Buy
 */
//@Resource
public interface BasicDAO {

    void save  (Company company)throws Exception;
    
    void delete(Company company) throws Exception;
    
    List<Company> getRoots();
    
    public Company getByName(String companyName);
    
    Company getByNameWithBuild(String name);
    
    void updateNameOrParent(Company toUpdate, String key, String newData);
    
    void updateEarnings(Company company, int newEarnings);
    
    boolean exist(String companyName);
    
    Collection<Company> getChildsOf(String companyName);
}
