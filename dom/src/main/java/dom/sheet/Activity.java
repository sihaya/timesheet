/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dom.sheet;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import org.apache.isis.applib.annotation.Bounded;
import org.apache.isis.applib.annotation.Title;

/**
 *
 * @author sihaya
 */
@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Bounded
public class Activity {
    private String name;

    @Title
    @Column(allowsNull = "false")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }    
}
