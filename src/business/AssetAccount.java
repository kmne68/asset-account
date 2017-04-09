/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author Keith
 */
abstract public class AssetAccount implements Account {
    
    private int AcctNo;
    private double balance;
    private String actmsg,errmsg;
    private String typeCode;
    private String nm;
    NumberFormat c = NumberFormat.getCurrencyInstance();
    
    public AssetAccount(int accountNumber) {
        
    }
    
    public AssetAccount(String code, String name, double balance) {
        
        this.AcctNo = 0;
        this.actmsg = "";
        this.errmsg = "";
        this.balance = 0;
        this.typeCode = code;
        
        while (this.AcctNo == 0) {
            try {
                this.AcctNo = (int) (Math.random() * 1000000);
                BufferedReader in = new BufferedReader(
                        new FileReader(this.typeCode + this.AcctNo + ".txt"));
                in.close();
                this.AcctNo = 0;
            } catch (IOException e) {
                //'good' result: account does not yot exist....
                this.nm = name;
                this.balance = balance;
                writestatus();
                if (this.errmsg.isEmpty()) {
                   actmsg = this.typeCode + " Account " +
                            this.nm + " " + this.AcctNo + " opened.";
                   writelog(actmsg);
                }   
                if (!this.errmsg.isEmpty()) {
                    this.balance = 0;
                    this.AcctNo = -1;
                    this.typeCode = "";
                }
            } catch (Exception e) {
                errmsg = "Fatal error in " + typeCode + " Account constructor: " + e.getMessage();
                this.AcctNo = -1;
                this.typeCode = "";
            }
        }//end of while
    }
    
        private void writestatus() {
        try {
            PrintWriter out = new PrintWriter(
                    new FileWriter(Savings.TYPECD + this.AcctNo + ".txt"));
            out.println(this.nm);
            out.println(this.balance);
            out.close();
        } catch (IOException e) {
            errmsg = "Error writing status file for " + this.typeCode + " account: "
                    + this.AcctNo;
        } catch(Exception e) {
            errmsg = "General error in status update: " + e.getMessage();
        }
    } //end of writestatus
    
    protected void writelog(String msg) {
        try {
            Calendar cal = Calendar.getInstance();
            DateFormat df = DateFormat.getDateTimeInstance();
            String ts = df.format(cal.getTime());
            PrintWriter out = new PrintWriter(
                              new FileWriter(Savings.TYPECD + "L" +
                                      this.AcctNo + ".txt",true));
            out.println(ts + ": " + msg);
            out.close();
        } catch (IOException e) {
            errmsg = "Error writing log file for " + this.typeCode +
                    " account " + this.AcctNo + e.getMessage();
        } catch (Exception e) {
            errmsg = "General error in write log: " + e.getMessage();
        }
    } //end of writelog
    
    
    @Override
    public void setCharge(double amt, String desc) {
        errmsg = "";
        actmsg = "";
        
        if (this.AcctNo <= 0) {
            errmsg = "Charge attempt on non-active account.";
            return;
        }
        
        if (amt <= 0) {
           actmsg = "Charge of " + c.format(amt) + " for " + desc +
                    " declined - illegal amount not positive. ";
           writelog(actmsg);
        } else if( amt > this.balance) {
           actmsg = "Charge of " + c.format(amt) + " for " + desc +
                    " declined - insufficeint funds "; 
           writelog(actmsg);
        } else {
           this.balance -= amt;
           writestatus();
           if (this.errmsg.isEmpty()) {
               actmsg = "Charge of " + c.format(amt) + " for " + desc +
                        " posted.";
               writelog(actmsg);
           }else {
               this.balance += amt; //back out operation
           }
        }
    } //end of setcharge
    
    @Override
     public void setPayment(double amt) {
        errmsg = "";
        actmsg = "";
        
        if (this.AcctNo <= 0) {
            errmsg = "Deposit attempt on non-active account.";
            return;
        }
        
        if (amt <= 0) {
            actmsg = "Payment of " + c.format(amt) + 
                    " declined - amount must be positive.";
            writelog(actmsg); 
        } else {
            this.balance  += amt;
            writestatus();
            if (this.errmsg.isEmpty()) {
                actmsg = "Deposit of " + c.format(amt) + " posted.";
                writelog(actmsg);
            }else {
                this.balance -= amt;
            }
        }
   } //end of payment
    
    @Override
    public void setInterest(double ir){
       errmsg = "";
       actmsg = "";
       double intearn;
       NumberFormat p = NumberFormat.getPercentInstance();
       
       if (this.AcctNo <= 0) {
           errmsg = "Interest attempt on non-active account.";
           return;
       }
       
       if (ir <= 0 || ir > 1.0) {
           actmsg = "Interest rate of " + p.format(ir) + 
                   " declined - rate not positive. or too large ";
           writelog(actmsg); 
       } else {
           intearn = this.balance * ir/12.00;
           this.balance += intearn;
           writestatus();
           if (this.errmsg.isEmpty()) {
               actmsg = "Interest earned = " + c.format(intearn) + " for " +
                       "month at annual rate of: " + p.format(ir);
                       
               writelog(actmsg);
           }else {
               this.balance -= intearn; //back out operation
           }
       } //end of interest charge method
    }

    @Override
    public int getAcctNo() {
        return this.AcctNo;
    }
    
    @Override
    public String getName() {
        return this.nm;
    }

    @Override
    public double getBalance() {
        return this.balance;
    }    

    public String getErrMsg() {
        return this.errmsg;
    }
    
    @Override
    public String getActionMsg() {
        return this.actmsg;
    }
    
    protected void setActionMsg(String msg) {
        this.actmsg = msg;
    }   
    
    @Override
    public ArrayList<String> getLog() {
       ArrayList<String> h = new ArrayList<>();
       errmsg = "";
       actmsg = "";
       String t;
       
       if (this.AcctNo <= 0) {
            errmsg = "Charge attempt on non-active account.";
            return h;
        }
       
       try {
           BufferedReader in = new BufferedReader(
                               new FileReader("CCL" + this.AcctNo + ".txt"));
           t = in.readLine();
           
           while (t != null) {
              h.add(t);
              t = in.readLine();
           }
           in.close();
           actmsg = "History returned for account: " + this.AcctNo;
       } catch (Exception e) {
           errmsg = "Error reading log file: " + e.getMessage();
       }
       return h;
   
    }
    
    
    @Override
    abstract public String getTypeCd();
    
    @Override
    abstract public String getTypeDesc();
}