// $Id$
// Copyright (c) 1996-2002 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation without fee, and without a written
// agreement is hereby granted, provided that the above copyright notice
// and this paragraph appear in all copies.  This software program and
// documentation are copyrighted by The Regents of the University of
// California. The software program and documentation are supplied "AS
// IS", without any accompanying services from The Regents. The Regents
// does not warrant that the operation of the program will be
// uninterrupted or error-free. The end-user understands that the program
// was developed for research purposes and is advised not to rely
// exclusively on the program for any reason.  IN NO EVENT SHALL THE
// UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT,
// SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS,
// ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
// THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
// SUCH DAMAGE. THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY
// WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
// PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
// CALIFORNIA HAS NO OBLIGATIONS TO PROVIDE MAINTENANCE, SUPPORT,
// UPDATES, ENHANCEMENTS, OR MODIFICATIONS.

package org.argouml.uml.ui;

import org.apache.log4j.Logger;
import org.argouml.model.ModelFacade;
import org.argouml.model.uml.behavioralelements.activitygraphs.ActivityGraphsFactory;
import org.argouml.ui.targetmanager.TargetManager;
import org.argouml.uml.diagram.activity.ui.UMLActivityDiagram;
import org.argouml.uml.diagram.ui.UMLDiagram;

import ru.novosoft.uml.behavior.activity_graphs.MActivityGraph;
import ru.novosoft.uml.foundation.core.MModelElement;
import ru.novosoft.uml.foundation.core.MNamespace;


/** Action to trigger creation of a new activity diagram.
 *  @stereotype singleton
 */
public class ActionActivityDiagram extends ActionStateDiagram {

    public static ActionActivityDiagram SINGLETON = new ActionActivityDiagram();

    protected static Logger cat =
	Logger.getLogger(org.argouml.uml.ui.ActionActivityDiagram.class);

    private ActionActivityDiagram() {
        super("action.activity-diagram");
    }

    /**
     * @see org.argouml.uml.ui.ActionAddDiagram#createDiagram(MNamespace, Object)
     */
    public UMLDiagram createDiagram(Object ns) {
        Object target = TargetManager.getInstance().getModelTarget();
        MActivityGraph graph =
	    ActivityGraphsFactory.getFactory().buildActivityGraph((MModelElement) target);
        if (org.argouml.model.ModelFacade.isABehavioralFeature(target)) {
            ns = ModelFacade.getNamespace(target);
        }
        UMLActivityDiagram d = new UMLActivityDiagram((MNamespace) ns, graph);
        return d;
    }

    /**
     * @see org.argouml.uml.ui.UMLAction#shouldBeEnabled()
     */
    public boolean shouldBeEnabled() {
        return super.shouldBeEnabled()
	    || org.argouml.model.ModelFacade.isAPackage(TargetManager.getInstance().getModelTarget());
    }
    /**
     * @see org.argouml.uml.ui.ActionAddDiagram#isValidNamespace(MNamespace)
     */
    public boolean isValidNamespace(Object handle) {
        return super.isValidNamespace(handle) || ModelFacade.isAPackage(handle);
    }

} /* end class ActionActivityDiagram */