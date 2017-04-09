/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.util.ArrayList;

/**
 *
 * @author Keith
 */
public interface Account {

    public int getAcctNo(); // – to return the account number

    public String getName(); // – to return the account name

    public double getBalance(); // – to return the current account balance

    public void setCharge(double amt, String desc); // – to process a charge

    public void setPayment(double amt); // – to process a payment

    public void setInterest(double rate); // – to process an interest earned event

    public String getErrMsg(); // - to return an error message

    public String getActionMsg(); // – to return a message from a resulting account action

    public String getTypeCd(); // – to return the 2-character code associated with the account

    public String getTypeDesc(); // – to return the general account type description
    
    public ArrayList<String> getLog(); // - to return the activity log for the account

}
