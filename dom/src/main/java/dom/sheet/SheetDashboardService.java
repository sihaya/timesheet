/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dom.sheet;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.HomePage;

/**
 *
 * @author sihaya
 */
@Hidden
public class SheetDashboardService {

    private static final String ID = "dashboard";

    public String getId() {
        return ID;
    }

    public String iconName() {
        return ID;
    }

    @ActionSemantics(Of.SAFE)
    @HomePage
    public SheetDashboard lookup() {
        return container.newViewModelInstance(SheetDashboard.class, ID);
    }

    public void injectDomainObjectContainer(final DomainObjectContainer container) {
        this.container = container;
    }
    private DomainObjectContainer container;
}
