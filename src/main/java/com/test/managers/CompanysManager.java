/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.managers;

import com.test.entity.Company;

import com.test.managers.dao.BasicDAO;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;

/**
 *
 * @author Buy
 */
@Resource
public class CompanysManager implements ManagerCRUDExtended {

    private final BasicDAO dao;

    public CompanysManager(BasicDAO dao) {
        this.dao = dao;
    }

    public Company simpleFind(String companyName) {
        return dao.getByName(companyName);
    }

    @Override
    public String toStringTreeOf(Company company) {
        String name = company.getName();
        company = dao.getByNameWithBuild(name);
        return update(company).treeToString("<br>");
    }

    @Override
    public void editParent(String companyToEdit, String newParentName) throws Exception {
        Company company = dao.getByName(companyToEdit);
        String oldPath = company.getPath();
        String newPath =  dao.getByName(newParentName).getPath()
                        + Company.PATH_SEPARATOR + companyToEdit;
        
        String newchildPath;
        for(Company child:dao.getChildsOf(companyToEdit)){
            newchildPath = child.getPath().replace(oldPath, newPath);
            dao.updateNameOrParent(child, "path", newchildPath);
        }
        dao.updateNameOrParent(company, "path", newPath);
    }
    
    @Override
    public void editEarnings(String company, int earnings){
        dao.updateEarnings(dao.getByName(company), earnings);
    }

    @Override
    public void rename(String currentName, String newName) {
        Company company = dao.getByName(currentName);
        String curPath = company.getPath();
        String newPath = curPath.replace(currentName, newName);
        dao.updateNameOrParent(company, "name", newName);
        replaceFromPathWith(company, newName + Company.PATH_SEPARATOR);
        dao.updateNameOrParent(company, "path", newPath);
    }

    @Override
    public Company createAndSaveCompany(String name, int earnings) throws Exception {
        if (dao.exist(name)) {
            throw new Exception("This name exist.");
        } else {
            Company company = new Company(name, earnings);
            dao.save(company);
            return company;
        }
    }

    @Override
    public Company createAndSaveCompany(
            String name, int earnings, String parentName)throws Exception {
        
        validateNames(name,parentName);
        if (dao.exist(parentName)) {
            Company company = new Company(name, earnings, dao.getByName(parentName));
            dao.save(company);
            return company;
        } else {
            throw new Exception("Parent company  not exist");
        }
    }

    @Override
    public String getAllCompanyToString() {
        StringBuilder sb = new StringBuilder();
        dao.getRoots().forEach((company) -> {
            sb.append(toStringTreeOf(company)).append("<br>");
        });
        return sb.toString();
    }

    @Override
    public void delete(String companyName) throws Exception{
        Company company = dao.getByName(companyName);
        replaceFromPathWith(company, "");
        dao.delete(company);
    }

    @Override
    public void deleteTree(String rootNameToDelete) throws Exception{
        dao.getChildsOf(rootNameToDelete).forEach((company) -> {
            try {
                dao.delete(company);
            } catch (Exception ex) {
                Logger.getLogger(CompanysManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        dao.delete(dao.getByName(rootNameToDelete));
    }

    private Company update(Company company) {
        this.countAndSetTotalEarnings(company);
        return company;
    }

    private int countAndSetTotalEarnings(Company company) {
        int childsEarnings = 0;
        if (!company.getChilds().isEmpty()) {
            childsEarnings = company.getChilds().stream().map((child)
                    -> countAndSetTotalEarnings(child))
                    .reduce(childsEarnings, Integer::sum);
            company.appendChildsEarnings(childsEarnings);
            return company.getTotalEarnings();
        }
        return company.getEarnings();
    }

    private void replaceFromPathWith(Company companyToReplace, String substitute) {
        if (dao.getChildsOf(companyToReplace.getName()).isEmpty()) {
            return;
        }
        String path = companyToReplace.getName() + Company.PATH_SEPARATOR;
        String newPath;
        for (Company child : dao.getChildsOf(companyToReplace.getName())) {
            newPath = child.getPath().replace(path, substitute);
            dao.updateNameOrParent(child, "path", newPath);
        }
    }

    private void validateNames(String name, String parentName) throws Exception{
        if (name.equals(parentName)) 
            throw new Exception("Confuse name");
    }

}
