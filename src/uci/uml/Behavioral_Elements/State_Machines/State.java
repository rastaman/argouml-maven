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


// Source file: Behavioral_Elements/State_Machines/State.java

package uci.uml.Behavioral_Elements.State_Machines;

import java.util.*;
import java.beans.*;

import uci.uml.Behavioral_Elements.Common_Behavior.ActionSequence;
import uci.uml.Foundation.Core.Attribute;
import uci.uml.Foundation.Data_Types.Name;

public class State extends StateVertex {
  public ActionSequence _entry;
  public ActionSequence _exit;
  //% public Event _deferredEvent[];
  public Vector _deferredEvent;
  public StateMachine _stateMachine;
  //% public Attribute _stateVariable[];
  public Vector _stateVariable;
  //% public ClassifierInState _classifierInState[];
  public Vector _classifierInState;
  //% public Transition _internalTransition[];
  public Vector _internalTransition;
    
  public State() { }
  public State(Name name) { super(name); }
  public State(String nameStr) { super(new Name(nameStr)); }

  public ActionSequence getEntry() { return _entry; }
  public void setEntry(ActionSequence x) throws PropertyVetoException {
    fireVetoableChange("entry", _entry, x);
    _entry = x;
  }
  
  public ActionSequence getExit() { return _exit; }
  public void setExit(ActionSequence x) throws PropertyVetoException {
    fireVetoableChange("exit", _exit, x);
    _exit = x;
  }

  public Vector getDeferredEvent() { return _deferredEvent; }
  public void setDeferredEvent(Vector x) throws PropertyVetoException {
    if (_deferredEvent == null) _deferredEvent = new Vector();
    fireVetoableChange("deferredEvent", _deferredEvent, x);
    _deferredEvent = x;
  }
  public void addDeferredEvent(Event x) throws PropertyVetoException {
    if (_deferredEvent == null) _deferredEvent = new Vector();
    fireVetoableChange("deferredEvent", _deferredEvent, x);
    _deferredEvent.addElement(x);
  }
  public void removeDeferredEvent(Event x) throws PropertyVetoException {
    if (_deferredEvent == null) return;
    fireVetoableChange("deferredEvent", _deferredEvent, x);
    _deferredEvent.removeElement(x);
  }

  public StateMachine getStateMachine() { return _stateMachine; }
  public void setStateMachine(StateMachine x) throws PropertyVetoException {
    if (_stateMachine == x) return;
    fireVetoableChange("stateMachine", _stateVariable, x);
    StateMachine oldStateMachine = _stateMachine;
    _stateMachine = x;
    if (oldStateMachine != null && oldStateMachine.getTop() == this) {
      oldStateMachine.setTop(null);
    }
  }

  public Vector getStateVariable() { return _stateVariable; }
  public void setStateVariable(Vector x) throws PropertyVetoException {
    if (_stateVariable == null) _stateVariable = new Vector();
    fireVetoableChange("stateVariable", _stateVariable, x);
    _stateVariable = x;
  }
  public void addStateVariable(Attribute x) throws PropertyVetoException {
    if (_stateVariable == null) _stateVariable = new Vector();
    fireVetoableChange("stateVariable", _stateVariable, x);
    _stateVariable.addElement(x);
  }
  public void removeStateVariable(Attribute x) throws PropertyVetoException {
    if (_stateVariable == null) return;
    fireVetoableChange("stateVariable", _stateVariable, x);
    _stateVariable.removeElement(x);
  }

  public Vector getClassifierInState() { return _classifierInState; }
  public void setClassifierInState(Vector x) throws PropertyVetoException {
    fireVetoableChange("classifierInState", _classifierInState, x);
    if (_classifierInState == null) _classifierInState = new Vector();
    _classifierInState = x;
  }
  public void addClassifierInState(ClassifierInState x)
       throws PropertyVetoException {
    if (_classifierInState == null) _classifierInState = new Vector();
    fireVetoableChange("classifierInState", _classifierInState, x);
    _classifierInState.addElement(x);
  }
  public void removeClassifierInState(ClassifierInState x)
       throws PropertyVetoException {
    if (_classifierInState == null) return;
    fireVetoableChange("classifierInState", _classifierInState, x);
    _classifierInState.removeElement(x);
  }

  public Vector getInternalTransition() { return _internalTransition; }
  public void setInternalTransition(Vector x) throws PropertyVetoException {
    if (_internalTransition == null) _internalTransition = new Vector();
    fireVetoableChange("internalTransition", _internalTransition, x);
    _internalTransition = x;
  }
  public void addInternalTransition(Transition x) throws PropertyVetoException {
    if (_internalTransition == null) _internalTransition = new Vector();
    fireVetoableChange("internalTransition", _internalTransition, x);
    _internalTransition.addElement(x);
  }
  public void removeInternalTransition(Transition x) throws PropertyVetoException {
    if (_internalTransition == null) return;
    fireVetoableChange("internalTransition", _internalTransition, x);
    _internalTransition.removeElement(x);
  }

  ////////////////////////////////////////////////////////////////
  // debugging

  public String dbgString() {
    String s = "State " + (getName() == null?"(anon)":getName().getBody());
    s += "     {\n";
    if (_entry != null) s += "    Entry:" + _entry.toString() + "\n";
    if (_exit != null) s += "    Exit:" + _exit.toString() + "\n";
    if (_internalTransition != null) {
      java.util.Enumeration interns = _internalTransition.elements();
      while (interns.hasMoreElements()) {
	Transition t = (Transition) interns.nextElement();
	s += "    " + t.dbgString() + "\n";
      }
    }
    s += "    }\n";
    return s;
  }
  
}
