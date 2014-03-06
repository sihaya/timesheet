/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dom.sheet;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Bookmarkable;
import org.apache.isis.applib.annotation.Bulk;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Title;

/**
 *
 * @author sihaya
 */
@Bookmarkable
@PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "sheet_all", language = "JDOQL",
            value = "SELECT "
            + "FROM dom.sheet.Sheet "
            + "WHERE user == :user"),
    @javax.jdo.annotations.Query(
            name = "sheet_all_status", language = "JDOQL",
            value = "SELECT "
            + "FROM dom.sheet.Sheet "
            + "WHERE user == :user && status == :status")
})
public class Sheet {

    private Date startDate;
    private Date submittedOn;
    private int week;
    private List<SheetEntry> entries;
    private String user;
    private DomainObjectContainer container;
    private Status status;

    public Sheet() {
        this.status = Status.DRAFT;
    }

    @Column(allowsNull = "false")
    @Title
    @MemberOrder(sequence = "1")
    public Date getStartDate() {
        return startDate;
    }

    @Hidden
    @Column(allowsNull = "false")
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Render(Render.Type.EAGERLY)
    @Persistent(dependentElement = "true")
    public List<SheetEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<SheetEntry> entries) {
        this.entries = entries;
    }

    private SheetEntry addActivity(@Named("Activity") Activity activity) {
        SheetEntry sheetEntry = container.newTransientInstance(SheetEntry.class);
        sheetEntry.setActivity(activity);
        sheetEntry.resetEntries();

        getEntries().add(sheetEntry);

        return sheetEntry;
    }

    @MemberOrder(sequence = "2")
    public int getTotal() {
        int sum = 0;
        for (SheetEntry entry : getEntries()) {
            sum += entry.getTotalHours();
        }

        return sum;
    }

    public Sheet registerActivity(@Named("activity") Activity activity,
            @Named("sun") int sun, @Named("mon") int mon, @Named("tue") int tue,
            @Named("wed") int wed, @Named("thu") int thu, @Named("fri") int fri,
            @Named("sat") int sat) {
        SheetEntry entry = null;
        for (SheetEntry e : getEntries()) {
            if (activity.getName().equals(e.getActivity().getName())) {
                entry = e;
                break;
            }
        }

        if (entry == null) {
            entry = addActivity(activity);
        }

        entry.getSun().setHours(sun);
        entry.getMon().setHours(mon);
        entry.getTue().setHours(tue);
        entry.getWed().setHours(wed);
        entry.getThu().setHours(thu);
        entry.getFri().setHours(fri);
        entry.getSat().setHours(sat);

        if (getTotal() > 40) {
            container.warnUser("Total hours is higher than 40, please ensure this is correct");
        }

        return this;
    }

    public void injectDomainObjectContainer(final DomainObjectContainer container) {
        this.container = container;
    }

    @Hidden
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @MemberOrder(sequence = "3")
    public Status getStatus() {
        return status;
    }

    public boolean hideRegisterActivity() {
        return hideSubmit();
    }

    public boolean hideSubmit() {
        return status != Status.DRAFT;
    }

    public Sheet submit() {
        status = Status.SUBMITTED;
        submittedOn = new Date();

        return this;
    }

    public Date getSubmittedOn() {
        return submittedOn;
    }

    public int getWeek() {
        return week;
    }

    @Programmatic
    public void initStartDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        this.week = cal.get(Calendar.WEEK_OF_YEAR);

        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR, 0);

        this.startDate = cal.getTime();
    }
}
