// Copyright (c) 1996-98 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation for educational, research and non-profit
// purposes, without fee, and without a written agreement is hereby granted,
// provided that the above copyright notice and this paragraph appear in all
// copies. Permission to incorporate this software into commercial products may
// be obtained by contacting the University of California. David F. Redmiles
// Department of Information and Computer Science (ICS) University of
// California Irvine, California 92697-3425 Phone: 714-824-3823. This software
// program and documentation are copyrighted by The Regents of the University
// of California. The software program and documentation are supplied "as is",
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

//import jargo.kernel.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import com.sun.java.swing.*;
import com.sun.java.swing.event.*;
import com.sun.java.swing.tree.*;
//import com.sun.java.swing.border.*;

import uci.util.*;
import uci.ui.*;
import uci.argo.kernel.*;


public class ToDoPane extends JPanel
implements ItemListener, TreeSelectionListener, MouseListener, ToDoListListener {
  ////////////////////////////////////////////////////////////////
  // constants
  
  public static int WIDTH = 690;
  public static int HEIGHT = 520;
  public static int INITIAL_WIDTH = 400;
  public static int INITIAL_HEIGHT = 200;

  ////////////////////////////////////////////////////////////////
  // instance variables

  protected ProjectBrowser _pb = null;

  // vector of TreeModels
  protected Vector _perspectives = new Vector();

  protected ToolBar _toolbar = new ToolBar();
  protected JComboBox _combo = new JComboBox();
  protected ToDoList _root = null;
  protected ToDoPerspective _curPerspective = null;
  protected JTree _tree = new DisplayTextTree();

  ////////////////////////////////////////////////////////////////
  // constructors

  public ToDoPane() {
    setLayout(new BorderLayout());
    _toolbar.add(new JLabel("Group by "));
    _toolbar.add(_combo);
    add(_toolbar, BorderLayout.NORTH);
    add(new JScrollPane(_tree), BorderLayout.CENTER);
    _combo.addItemListener(this);
    
    _tree.setRootVisible(false);
    _tree.setShowsRootHandles(true);
    _tree.addTreeSelectionListener(this);
    _tree.setCellRenderer(new ToDoTreeRenderer());

    _tree.addMouseListener(this);

    Designer.TheDesigner.getToDoList().addToDoListListener(this);
  }

  ////////////////////////////////////////////////////////////////
  // accessors

  public void setRoot(ToDoList r) {
    _root = r;
    updateTree();
  }
  public ToDoList getRoot() { return _root; }

  public Vector getPerspectives() { return _perspectives; }
  public void setPerspectives(Vector pers) {
    _perspectives = pers;
    if (pers.isEmpty()) _curPerspective = null;
    else _curPerspective = (ToDoPerspective) pers.elementAt(0);
    _combo.removeAllItems();
    Enumeration persEnum = _perspectives.elements();
    while (persEnum.hasMoreElements()) 
      _combo.addItem(persEnum.nextElement());
    setCurPerspective((TreeModel)_perspectives.elementAt(0));
    updateTree();
  }

  public ToDoPerspective getCurPerspective() { return _curPerspective; }
  public void setCurPerspective(TreeModel per) {
    if (_perspectives == null || !_perspectives.contains(per)) return;
    _combo.setSelectedItem(per);
  }

  public Object getSelectedObject() {
    return _tree.getLastSelectedPathComponent();
  }

  public Dimension getPreferredSize() { return new Dimension(200, 100); }
  public Dimension getMinimumSize() { return new Dimension(100, 100); }

  ////////////////////////////////////////////////////////////////
  // event handlers

  /** called when the user selects a perspective from the perspective
   *  combo. */
  public void itemStateChanged(ItemEvent e) {
    updateTree();
  }

  /** called when the user selects an item in the tree, by clicking or
   *  otherwise. */
  public void valueChanged(TreeSelectionEvent e) {
    System.out.println("ToDoPane valueChanged");
    //needs-more-work: should fire its own event and ProjectBrowser
    //should register a listener
    ProjectBrowser.TheInstance.setToDoItem(getSelectedObject());
  }


  public void mousePressed(MouseEvent e) { }
  public void mouseReleased(MouseEvent e) { }
  public void mouseEntered(MouseEvent e) { }
  public void mouseExited(MouseEvent e) { }

  public void mouseClicked(MouseEvent e) {
    int row = _tree.getRowForLocation(e.getX(), e.getY());
    TreePath path = _tree.getPathForLocation(e.getX(), e.getY());
    if (row != -1) {
      if (e.getClickCount() == 1) mySingleClick(row, path);
      else if (e.getClickCount() >= 2) myDoubleClick(row, path);
    }
  }



  // needs-more-work: what should the difference be between a single
  // and double click?

  /** called when the user clicks once on an item in the tree. */ 
  public void mySingleClick(int row, TreePath path) {
    if (getSelectedObject() == null) return;
    //needs-more-work: should fire its own event and ProjectBrowser
    //should register a listener
    //System.out.println("1: " + getSelectedObject().toString());
  }

  /** called when the user clicks once on an item in the tree. */ 
  public void myDoubleClick(int row, TreePath path) {
    if (getSelectedObject() == null) return;
    //needs-more-work: should fire its own event and ProjectBrowser
    //should register a listener
    //System.out.println("2: " + getSelectedObject().toString());
  }

  ////////////////////////////////////////////////////////////////
  // DecisionModelListener implementation


  ////////////////////////////////////////////////////////////////
  // GoalListener implementation


  ////////////////////////////////////////////////////////////////
  // ToDoListListener implementation

  public void toDoItemAdded(ToDoListEvent tde) {
    if (_curPerspective instanceof ToDoListListener) 
      ((ToDoListListener)_curPerspective).toDoItemAdded(tde);
  }

  public void toDoItemRemoved(ToDoListEvent tde) {
    if (_curPerspective instanceof ToDoListListener)
      ((ToDoListListener)_curPerspective).toDoItemRemoved(tde);
  }
  
  public void toDoListChanged(ToDoListEvent tde) { 
    if (_curPerspective instanceof ToDoListListener)
      ((ToDoListListener)_curPerspective).toDoListChanged(tde);
  }
  


  ////////////////////////////////////////////////////////////////
  // internal methods

  protected void updateTree() {
    ToDoPerspective tm = (ToDoPerspective) _combo.getSelectedItem();
    _curPerspective = tm;
    if (_curPerspective == null) _tree.hide();
    else {
      System.out.println("ToDoPane setting tree model");
      _curPerspective.setRoot(_root);
      _tree.setModel(_curPerspective);
      _tree.show(); // blinks?
    }
  }

} /* end class ToDoPane */
