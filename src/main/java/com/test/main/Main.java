/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.test.main;

import com.fasterxml.jackson.annotation.JsonView;
import com.test.datamodels.AjaxResponseBody;
import com.test.datamodels.SearchCriteria;
import com.test.datamodels.Views;
import com.test.managers.ManagerCRUDExtended;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@SpringBootApplication
public class Main {

  @Autowired
  private ManagerCRUDExtended manager;

  public static void main(String[] args) throws Exception {
    SpringApplication.run(Main.class, args);
  }

  @RequestMapping("/")
  String index() {
    return "index";
  }
  
  @JsonView(Views.Public.class)
  @RequestMapping("/create")
  public @ResponseBody AjaxResponseBody createCompany(@RequestBody SearchCriteria input) {
    AjaxResponseBody result = new AjaxResponseBody();
        try{
            checkInputAndCreateCompany(input);
            result.setCode("Company added");
        }catch(Exception e){
            result.setCode(e.getMessage());
        }
    result.setMsg(manager.getAllCompanyToString());
    return result;
  }
  
  @JsonView(Views.Public.class)
    @RequestMapping(value = "/edit")
    public @ResponseBody AjaxResponseBody edit(@RequestBody SearchCriteria input) throws Exception {
        AjaxResponseBody result = new AjaxResponseBody();
        String companyToEdit = input.getCompanyName();
        String newParentname = input.getParentName();
        String newName = input.getNewName();
        int newearns = input.getEarnings();
        
        System.out.println(input);
                
        if(!companyToEdit.equals("")){
            try{
            if(!newParentname.equals(""))
                manager.editParent(companyToEdit, newParentname);
            if(newearns!=0)
                manager.editEarnings(companyToEdit, newearns);
            if(!(newName.trim().equals("")))
                manager.rename(companyToEdit, newName);
            }catch(Exception e ){
                result.setCode(e.getMessage());
            }
        result.setCode("EditCompany method ok!");
        }else{
             result.setCode("Edit data wrong!");
        }
        result.setMsg(manager.getAllCompanyToString());
        return result;
    }


    @JsonView(Views.Public.class)
    @RequestMapping(value = "/delete")
    public @ResponseBody AjaxResponseBody deleteOne(@RequestBody SearchCriteria input) {
        AjaxResponseBody result = new AjaxResponseBody();
        if (input.getCompanyName().trim().equals("")) {
            result.setCode("Wrong company name");
        }else{
            try{
                manager.delete(input.getCompanyName());
                result.setCode("Ok!");
            }catch(Exception e){
                result.setCode(e.getMessage());
            }
        }
        result.setMsg(manager.getAllCompanyToString());
        return result;
    }

    @JsonView(Views.Public.class)
    @RequestMapping(value = "/delete/tree")
    public @ResponseBody AjaxResponseBody deleteTree(@RequestBody SearchCriteria input) {
        AjaxResponseBody result= new AjaxResponseBody();
        if (input.getCompanyName().equals("")){
            result.setCode("Name can't be empty");
        } else {
            try {
                manager.deleteTree(input.getCompanyName());
                result.setCode("Deleted!");
            } catch (Exception ex) {
                result.setCode(ex.getMessage());
            }
      }
        result.setMsg(manager.getAllCompanyToString());
        return result;
    }

    private void checkInputAndCreateCompany(SearchCriteria input) throws Exception{
        
        String name = input.getCompanyName();
        int earns = input.getEarnings();
        String parentName = input.getParentName();

        if (!name.equals("") && !parentName.equals("")) {
            manager.createAndSaveCompany(name, earns, parentName);
        } else if (!name.trim().equals("")) {
            manager.createAndSaveCompany(name, earns);
        }
    }
    
}
