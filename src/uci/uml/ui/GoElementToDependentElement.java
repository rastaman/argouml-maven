// Copyright (c) 1996-98 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation for educational, research and non-profit
// purposes, without fee, and without a written agreement is hereby granted,
// provided that the above copyright notice and this paragraph appear in all
// copies. Permission to incorporate this software into commercial products
// must be negotiated with University of California. This software program and
// documentation are copyrighted by The Regents of the University of
// California. The software program and documentation are supplied "as is",
// without any accompanying services from The Regents. The Regents do not
// warrant that the operation of the program will be uninterrupted or
// error-free. The end-user understands that the program was developed for
// research purposes and is advised not to rely exclusively on the program for
// any reason. IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY
// PARTY FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES,
// INCLUDING LOST PROFITS, ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS
// DOCUMENTATION, EVEN IF THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE
// POSSIBILITY OF SUCH DAMAGE. THE UNIVERSITY OF CALIFORNIA SPECIFICALLY
// DISCLAIMS ANY WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
// WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE
// SOFTWARE PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
// CALIFORNIA HAS NO OBLIGATIONS TO PROVIDE MAINTENANCE, SUPPORT, UPDATES,
// ENHANCEMENTS, OR MODIFICATIONS.




package uci.uml.ui;

import java.util.*;
import com.sun.java.swing.*;
import com.sun.java.swing.event.*;
import com.sun.java.swing.event.*;
import com.sun.java.swing.tree.*;

import uci.uml.Model_Management.*;
import uci.uml.Foundation.Core.*;
import uci.uml.Behavioral_Elements.State_Machines.*;

public class GoElementToDependentElement implements TreeModelPrereqs {

  public String toString() { return "Element->Dependent Element"; }

  public Object getRoot() {
    System.out.println("getRoot should never be called");
    return null;
  }
  public void setRoot(Object r) { }

  public Object getChild(Object parent, int index) {
    if (parent instanceof ModelElement) {
      Vector deps = ((ModelElement)parent).getProvision();
      if (deps == null) return null;
      Vector clients = ((Dependency)deps.elementAt(index)).getClient();
      return clients.elementAt(0);
    }
    System.out.println("getChild should never be get here "+
		       "GoElementToDependentElement");
    return null;
  }

  public int getChildCount(Object parent) {
    if (parent instanceof ModelElement) {
      Vector deps = ((ModelElement)parent).getProvision();
      return (deps == null) ? 0 : deps.size();
    }
    return 0;
  }

  public int getIndexOfChild(Object parent, Object child) {
    if (parent instanceof ModelElement) {
      Vector deps = ((ModelElement)parent).getProvision();
      return (deps == null) ? -1 : deps.indexOf(child);
    }
    return -1;
  }

  public boolean isLeaf(Object node) {
    return !(node instanceof ModelElement && getChildCount(node) > 0);
  }

  public void valueForPathChanged(TreePath path, Object newValue) { }
  public void addTreeModelListener(TreeModelListener l) { }
  public void removeTreeModelListener(TreeModelListener l) { }

  public Vector getPrereqs() {
    Vector pros = new Vector();
    pros.addElement(ModelElement.class);
    return pros;
  }
  public Vector getProvidedTypes() {
    Vector pros = new Vector();
    pros.addElement(ModelElement.class);
    return pros;
  }


}
