/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.entity;

import java.util.Objects;

/**
 *
 * @author Buy
 */
public abstract class BasisOfCompany {
    
    
    private long id;
    private String name;
    private int estimatedEarnings;
    

    public BasisOfCompany(String name, int earnings){
        this.estimatedEarnings = earnings;
        this.name = name;
    }

    public void setName(String newName){
        this.name = newName;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(int earnings){
        this.estimatedEarnings = earnings;
    }

    public int getEstimatedEarnings() {
        return estimatedEarnings;
    }
    
     @Override
    public String toString() {
        return name +" | " + estimatedEarnings ;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.name);
        hash = 43 * hash + this.estimatedEarnings;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BasisOfCompany other = (BasisOfCompany) obj;
        if (this.estimatedEarnings != other.estimatedEarnings) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
}
