/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dom.sheet;

import dom.simple.SimpleObject;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.Bookmarkable;
import org.apache.isis.applib.annotation.Mask;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.query.QueryDefault;

/**
 *
 * @author sihaya
 */
public class Sheets extends AbstractFactoryAndRepository {

    @Override
    public String getId() {
        return "sheet";
    }

    public String iconName() {
        return "Sheet";
    }

    @Bookmarkable
    @ActionSemantics(ActionSemantics.Of.SAFE)
    @MemberOrder(sequence = "6")
    public List<Sheet> listAll() {
        return allMatches(new QueryDefault<Sheet>(Sheet.class,
                "sheet_all",
                "user", getUser().getName()));
    }

    @Bookmarkable
    @MemberOrder(sequence = "2")
    public Sheet createSheet(
            final @Named("Start date") Date date) {
        final Sheet obj = newTransientInstance(Sheet.class);

        obj.initStartDate(date);

        obj.setUser(getUser().getName());

        persistIfNotAlready(obj);
        return obj;
    }

    public String validateCreateSheet(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        if (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            return "The start date must be on a Sunday";
        }

        return null;
    }

    @Bookmarkable
    @MemberOrder(sequence = "3")
    public Activity createActivity(
            final @Named("Name") String name) {
        final Activity activity = newTransientInstance(Activity.class);
        activity.setName(name);
        persistIfNotAlready(activity);

        return activity;
    }

    @MemberOrder(sequence = "4")
    public List<Sheet> listDraft() {
        return allMatches(new QueryDefault<Sheet>(Sheet.class,
                "sheet_all_status",
                "status", Status.DRAFT,
                "user", getUser().getName()));
    }

    @MemberOrder(sequence = "5")
    public List<Sheet> listSubmitted() {
        return allMatches(new QueryDefault<Sheet>(Sheet.class,
                "sheet_all_status",
                "status", Status.SUBMITTED,
                "user", getUser().getName()));
    }
}
