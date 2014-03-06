/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dom.sheet;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import org.apache.isis.applib.annotation.Title;

/**
 *
 * @author sihaya
 */
@PersistenceCapable(identityType = IdentityType.DATASTORE)
public class DayEntry {        
    private int day;
    private int hours;

    public DayEntry() {
    }

    public DayEntry(SheetEntry sheetEntry, int day) {        
        this.day = day;
    }

    @Title
    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }    
}
