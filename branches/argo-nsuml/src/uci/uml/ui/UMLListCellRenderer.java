// Copyright (c) 1996-99 The Regents of the University of California. All
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




package uci.uml.ui;

//import jargo.kernel.*;
import java.awt.*;
import java.awt.event.*;
import com.sun.java.util.collections.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.*;

import uci.gef.*;
import uci.uml.visual.*;
import ru.novosoft.uml.foundation.core.*;
import ru.novosoft.uml.foundation.data_types.*;
import ru.novosoft.uml.behavior.state_machines.*;
import ru.novosoft.uml.model_management.*;

public class UMLListCellRenderer extends DefaultListCellRenderer {
  ////////////////////////////////////////////////////////////////
  // class variables
//   protected ImageIcon _AttributeIcon = loadIconResource("MAttribute");
//   protected ImageIcon _OperationIcon = loadIconResource("MOperation");
//   protected ImageIcon _ClassIcon = loadIconResource("Class");
//   protected ImageIcon _PackageIcon = loadIconResource("Package");
//   protected ImageIcon _AssociationIcon = loadIconResource("MAssociation");
//   protected ImageIcon _AssociationIcon2 = loadIconResource("Association2");
//   protected ImageIcon _AssociationIcon3 = loadIconResource("Association3");
//   protected ImageIcon _AssociationIcon4 = loadIconResource("Association4");
//   protected ImageIcon _AssociationIcon5 = loadIconResource("Association5");
//   protected ImageIcon _GeneralizationIcon = loadIconResource("MGeneralization");
//   protected ImageIcon _RealizationIcon = loadIconResource("Realization");
//   protected ImageIcon _ClassDiagramIcon = loadIconResource("ClassDiagram");
//   protected ImageIcon _UseCaseDiagramIcon = loadIconResource("UseCaseDiagram");
//   protected ImageIcon _StateDiagramIcon = loadIconResource("StateDiagram");

//   protected ImageIcon _StateIcon = loadIconResource("MState");
//   protected ImageIcon _StartStateIcon = loadIconResource("StartState");
//   protected ImageIcon _DeepIcon = loadIconResource("DeepHistory");
//   protected ImageIcon _ShallowIcon = loadIconResource("ShallowHistory");
//   protected ImageIcon _ForkIcon = loadIconResource("Fork");
//   protected ImageIcon _JoinIcon = loadIconResource("Join");
//   protected ImageIcon _BranchIcon = loadIconResource("Branch");
//   protected ImageIcon _FinalStateIcon = loadIconResource("FinalState");

//   protected ImageIcon _StateMachineIcon = loadIconResource("MStateMachine");
//   protected ImageIcon _CompositeStateIcon = loadIconResource("MCompositeState");
//   protected ImageIcon _TransitionIcon = loadIconResource("MTransition");

  public Component getListCellRendererComponent( JList list, Object value, 
						 int index, boolean isSelected,
						 boolean cellHasFocus) {
    JLabel lab;
    lab = (JLabel) super.getListCellRendererComponent(list, value, index,
					     isSelected, cellHasFocus);
    if ((value instanceof String) && ((String)value).equals("")) {
      lab.setText("\"\"");
      return lab;
    }
    if (!(value instanceof MModelElement)) return lab;
    String name = ((MModelElement) value).getName();
    if (name == null) {
      lab.setText("(null anon)");
      return lab;
    }
    String nameStr = name;
    if (nameStr.length() == 0) nameStr = "(anon)";
    lab.setText(nameStr);
    lab.setToolTipText(nameStr + " ");
    list.setToolTipText(nameStr + " ");
    // icons?
    return lab;
  }



} /* end class UMLListCellRenderer */
