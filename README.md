# kr.ac.jbnu.ssel.misrac
## OpenMRC: Open Source MISRA-C Rule Checker based on Eclipse CDT(C/C++ Development Tooling).
that's our program's architecture of below this sentence
![alt text](https://github.com/stkim123/kr.ac.jbnu.ssel.misrac/blob/master/ScreenShot1.PNG)

As we can see, our OpenMRC is composed two steps (Parsing AST in the C code, Checking Rule depends on the Misra-C:2004 Rules)

- In the first step, you input the C code for Vehicle SW and then, we build the ast tree based on the C-AST.

- Second step, we check the source code that was built by C-AST depends on the MISRA-C: 2004 Guide Line Rules
And we show the result of Checking rules with EClipse plugin

Also I said it at the previous sentence, OpenMRC shows the result of Checking rules that We implemented with eclipse plugin
And our plugin is also composed two pages(preferencePage tableViewer)

## the Preference Page
- In the Preference Page, you can check what kind of rules are you going to use it depends on the MISRA-C Rules category and also you can see the samples and description of each of these rules.

![alt text](https://github.com/stkim123/kr.ac.jbnu.ssel.misrac/blob/master/ScreenShot2%20.PNG)

## the tableViewer Page
- In the tableViewer, OpenMRC shows you what kind of rules does it violate in the C code for vehicle SW, which part in the sourceCode is violated When you click the each raw of the table and If you want run checkingRules you just push the button of pin shape in above the picture.

![alt text](https://github.com/stkim123/kr.ac.jbnu.ssel.misrac/blob/master/ScreenShot3.PNG)
