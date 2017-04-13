
package business;

/**
 *
 * @author 
 */
public class Checking extends AssetAccount {
    public static final String TYPECD = "CK";
    public static final String TYPEDESC = "Checking Account";

    public Checking(int acctno) {
        super(Checking.TYPECD, acctno);
    }
    
    public Checking(String nm, double sbal){
        super(Checking.TYPECD, nm, sbal);        
    }    
    
    @Override
    public void setInterest(double ir){
        String msg = "Interest request: No action - checking accounts do not earn interest";
        super.setActionMsg(msg);
        super.writelog(msg);
    }
    
    @Override
    public String getTypeCd() {
        return Checking.TYPECD;
    }
    
    @Override
    public String getTypeDesc() {
        return Checking.TYPEDESC;
    }
    
}//end of checking
