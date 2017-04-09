/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

/**
 *
 * @author Keith
 */
public class MoneyMarket extends AssetAccount {
    // look at savings
    public static final String TYPECD = "MM";
    public static final String TYPEDESC = "Money Market";
    
    private double chargeFee = 25;
    
    private int chargeAcct;
    // need to know how many charges have occurred sinc last time
    
    public MoneyMarket(String nm, double sbal) {
        super(MoneyMarket.TYPECD, nm, sbal);
                this.chargeAcct = 0;
    }
    
    
    @Override
    public void setCharge(double amt, String desc) {
        super.setCharge(amt, desc);
        if(super.getErrMsg().isEmpty()) {
            chargeAcct++;
            if(chargeAcct >= 4) {
                // new charge for transaction fee
                super.setCharge(chargeFee, "Transaction fee - over 3 withdrawal limit.");
            }
        }
    }
        
            
    public String getTypeCd() {
        return MoneyMarket.TYPECD;
    }
    
    public String getTypeDesc() {
        return MoneyMarket.TYPEDESC;
    }
        // let assetaccount handle the charge
        // how to handle charge if it exceeds account balance
    }
