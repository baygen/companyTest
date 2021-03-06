/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.entity;

import java.util.Collection;
import java.util.HashSet;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 *
 * @author Buy
 */
@Document(collection = "testCompany")
public class Company {

    public static final String PATH_SEPARATOR = ".";
    @Id
    private String id;
    @Field
    private String name;
    private Integer earnings;
    private String path;
    @Transient
    private int totalEarnings;

    @Transient
    public final Collection<Company> childs = new HashSet<>();

    public Company() {
    }

    public Company(String name, int earnings) {
        this.earnings = earnings;
        this.name = name;
        this.path = name;
        this.totalEarnings = 0;
    }

    public Company(String name, int earnings, Company parent) {
        this(name, earnings);
        this.path = parent.getPath() + PATH_SEPARATOR + name;
    }

    public String getPath() {
        return path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEarnings() {
        return earnings;
    }

    public void setEarnings(Integer earnings) {
        this.earnings = earnings;
    }

    public Collection<Company> getChilds() {
        return childs;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (name != null ? name.hashCode() + earnings.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Company)) {
            return false;
        }
        Company other = (Company) object;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer(name + " | " + earnings);
        return (totalEarnings == 0)?sb.toString():sb.append( " | ").append(totalEarnings).toString();
    }

    public String treeToString(String separator) {
        final StringBuilder sb = new StringBuilder();
        sb.append(" ").append(indent()).append(this.toString())
                .append(separator)
                .append(childsToString(separator));
        return sb.toString();
    }

    public int getTotalEarnings() {
        return totalEarnings;
    }

    public void appendChildsEarnings(int childEarnings) {
        this.totalEarnings = this.earnings + childEarnings;
    }

    private String indent() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < path.toCharArray().length; i++) 
            if (path.toCharArray()[i] == Company.PATH_SEPARATOR.charAt(0)) 
                sb.append("-");
        return sb.toString();
    }

    private String childsToString(String separator) {
        if (getChilds().isEmpty()) {
            return "";
        } else {
            StringBuffer sb = new StringBuffer();
                for (Company child : childs) 
                    sb.append(child.treeToString(separator));
            return sb.toString();
        }
        
    }

}
