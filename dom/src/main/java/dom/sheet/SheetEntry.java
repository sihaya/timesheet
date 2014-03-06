/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dom.sheet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Order;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Bulk;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Title;

/**
 *
 * @author sihaya
 */
@PersistenceCapable(identityType = IdentityType.DATASTORE)
public class SheetEntry {                   
    private Activity activity;
            
    private List<DayEntry> entries;
    private DomainObjectContainer container;
    
    

    public SheetEntry() {
    }
        
    /**
     * @return the activity
     */
    @Column(allowsNull = "false")
    @Title
    @MemberOrder(sequence = "1")
    public Activity getActivity() {
        return activity;
    }

    /**
     * @param activity the activity to set
     */
    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    /**
     * @return the entry
     */
    @Column(allowsNull = "false")
    @Persistent(dependentElement = "true", defaultFetchGroup = "true" )
    @Order(mappedBy = "day")
    public List<DayEntry> getEntries() {
        return entries;
    }

    /**
     * @param entry the entry to set
     */
    public void setEntries(List<DayEntry> entries) {
        this.entries = entries;
    }
    
    void resetEntries() {
        entries = new ArrayList<DayEntry>();
        
        for(int i = 1; i <= 7; i++) {
            DayEntry entry = container.newTransientInstance(DayEntry.class);          
            entries.add(entry);
        }
    }
    
    @MemberOrder(sequence = "2")
    public DayEntry getSun() {
        return getEntries().get(0);
    }
    
    @MemberOrder(sequence = "3")
    public DayEntry getMon() {
        return getEntries().get(1);
    }
    
    @MemberOrder(sequence = "4")
    public DayEntry getTue() {
        return getEntries().get(2);
    }
    
    @MemberOrder(sequence = "5")
    public DayEntry getWed() {
        return getEntries().get(3);
    }
    
    @MemberOrder(sequence = "6")
    public DayEntry getThu() {
        return getEntries().get(4);
    }
    
    @MemberOrder(sequence = "7")
    public DayEntry getFri() {
        return getEntries().get(5);
    }
    
    @MemberOrder(sequence = "8")
    public DayEntry getSat() {
        return getEntries().get(6);
    }
    
    public void injectDomainObjectContainer(final DomainObjectContainer container) {
        this.container = container;
    }    
    
    @MemberOrder(sequence = "9")
    public int getTotalHours() {
        int total = 0;
        
        for(DayEntry entry : getEntries()) {
            total += entry.getHours();
        }
        
        return total;
    }
    
    @Bulk
    public void delete() {
        container.removeIfNotAlready(this);                
    }
}
