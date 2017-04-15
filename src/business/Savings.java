
package business;

/**
 *
 * @author
 */
public class Savings extends AssetAccount {
    public static final String TYPECD = "SV";
    public static final String TYPEDESC = "Passbook Savings";
    
    // in class constructor
    public Savings(int acctno) {
        super(Savings.TYPECD, acctno);
    }

    
    public Savings(String nm, double sbal){
        super(Savings.TYPECD, nm, sbal);
    }
    
    
    @Override
    public String getTypeCd() {
        return Savings.TYPECD;
    }
    
    @Override
    public String getTypeDesc() {
        return Savings.TYPEDESC;
    }
    
}//end of savings
