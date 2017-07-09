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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@SpringBootApplication
public class Main {

//  @Value("${spring.datasource.url}")
//  private String dbUrl;

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
    
    result.setCode("Create method invoked");
    result.setMsg("Massage from create method!");
  return result;
  }
  
  @JsonView(Views.Public.class)
    @RequestMapping(value = "/edit")
    public AjaxResponseBody edit(@RequestBody SearchCriteria input) throws Exception {
        AjaxResponseBody result = new AjaxResponseBody();
        String companyToEdit = input.getCompanyName();
        String newParentname = input.getParentName();
        String newName = input.getNewName();
        int newearns = input.getEarnings();
        
        System.out.println(input);
                
        if(!companyToEdit.equals("")){
            if(!newParentname.equals(""))
                manager.editParent(companyToEdit, newParentname);
            if(newearns!=0)
                manager.editEarnings(companyToEdit, newearns);
            if(!(newName.trim().equals("")))
                manager.rename(companyToEdit, newName);
        result.setCode("EditCompany method ok!");
        }else{
             result.setCode("Edit compay method data!");
        }
        result.setMsg(manager.getAllCompanyToString());
        return result;
    }


    @JsonView(Views.Public.class)
    @RequestMapping(value = "/delete")
    public AjaxResponseBody deleteOne(@RequestBody SearchCriteria input) throws Exception {
        AjaxResponseBody result = new AjaxResponseBody();
        if (!input.getCompanyName().trim().equals("")) {
            manager.delete(input.getCompanyName());
            result.setCode("Ok!");
            result.setMsg(manager.getAllCompanyToString());
        }else{
            result.setCode("Wrong company name");
            result.setMsg(manager.getAllCompanyToString());
        }
        return result;
    }

    @JsonView(Views.Public.class)
    @RequestMapping(value = "/delete/tree")
    public AjaxResponseBody deleteTree(@RequestBody SearchCriteria input) throws Exception {
        AjaxResponseBody result= new AjaxResponseBody();
        if (!input.getCompanyName().equals("")){
            manager.deleteTree(input.getCompanyName());
            result.setCode("Deleted!");
        }
        result.setMsg(manager.getAllCompanyToString());
        return result;
    }
}
