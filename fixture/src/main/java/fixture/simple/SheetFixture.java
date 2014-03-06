/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fixture.simple;

import dom.sheet.Sheet;
import dom.sheet.Sheets;
import java.util.Calendar;
import java.util.Date;
import org.apache.isis.applib.fixtures.AbstractFixture;

/**
 *
 * @author sihaya
 */
public class SheetFixture extends AbstractFixture {

    @Override
    public void install() {
        sheets.createActivity("SP1");
        sheets.createActivity("Kapper");
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);        
        
        
        Sheet sheet = sheets.createSheet((Date)cal.getTime().clone());
        sheet.submit();
        
        cal.add(Calendar.YEAR, -1);
        
        sheets.createSheet((Date)cal.getTime().clone());
    }
    
    private Sheets sheets;
    
    public void injectSheets(Sheets sheets) { 
        this.sheets = sheets;
    }
}
