/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dom.sheet;

import java.util.List;
import org.apache.isis.applib.AbstractViewModel;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Render.Type;

/**
 *
 * @author sihaya
 */
public class SheetDashboard extends AbstractViewModel {

    private String memento;

    @Override
    public String viewModelMemento() {
        return memento;
    }

    @Override
    public void viewModelInit(String memento) {
        this.memento = memento;
    }

    public String title() {
        return "Timesheet dashboard";
    }

    @Named("Draft sheets")
    @Render(Type.EAGERLY)
    public List<Sheet> getDraftSheets() {
        return sheets.listDraft();
    }
    
    @Named("Submitted sheets")
    @Render(Type.EAGERLY)
    public List<Sheet> getSubmittedSheets() {
        return sheets.listSubmitted();
    }

    public void injectSheets(Sheets sheets) {
        this.sheets = sheets;
    }
    private Sheets sheets;
}
