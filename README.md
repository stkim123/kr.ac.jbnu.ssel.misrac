# kr.ac.jbnu.ssel.misrac
## OpenMRC: Open Source MISRA-C Rule Checker based on Eclipse CDT(C/C++ Development Tooling)
OpenMRC is an open source MISRA(Motor Industry Software Reliability Association)-C rule checker, developed as an Eclipse CDT plugin. 
The following is an overall steps to use OpenMRC. 

![alt text](https://github.com/stkim123/kr.ac.jbnu.ssel.misrac/blob/master/ScreenShot1.PNG)

OpenMRC has a two-step usage:
- At the first step, OpenMRC obtains the C source code for for Vehicle SW, and then it builds the AST(Abstract Syntax Tree) by using C-AST parser bundled in Eclipse CDT.
- At the second step, OpenMRC traverses all of the AST elements in order to check violations of MISRA-C: 2004 Guideline Rules, and produces violation messages. The messages are shown in the Eclipse CDT views (see the tableViewer page).

Based on the violation messages, a vehicle software developer can update their source continuously to achieve the vehicle software's functional safety. 

## the tableViewer Page
- In the tableViewer, OpenMRC shows which rules has been violated in the C source code. When the developer selects one of the violation messages, OpenMRC automatically hightlights the violation part in the source code as shown below: 

![alt text](https://github.com/stkim123/kr.ac.jbnu.ssel.misrac/blob/master/ScreenShot3.PNG)

## the Preference Page
- OpenMRC provides the Preference Page that can selectively check the MISRA-C Rules. In the Preference page, all rules are categorized and a developer can select/deselect the rules. 

![alt text](https://github.com/stkim123/kr.ac.jbnu.ssel.misrac/blob/master/ScreenShot2%20.PNG)

