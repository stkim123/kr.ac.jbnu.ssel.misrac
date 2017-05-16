# kr.ac.jbnu.ssel.misrac
OpenMRC: Open Source MISRA-C Rule Checker based on Eclipse CDT(C/C++ Development Tooling).
that's our program's architecture at below this sentence
![alt text](https://github.com/stkim123/kr.ac.jbnu.ssel.misrac/blob/master/ScreenShot1.PNG)

As we can see our OpenMRC is composed two step (Parsing AST in the C code, Checking Rule depends on the Misra-C:2004 Rules)

In the first step you input the C code for Vehicle SW and then, we build the ast tree based on the C-AST.

Second step we check the source code that was built by C-AST depends on the MISRA-C: 2004 Guide Line Rules
And we show the result of Checking rules with EClipse plugin

Also I said it at the previous sentence To show the result of Checking rules We implemented with eclipse plugin
And our plugin is also composed two page(preferencePage tableViewer)

In the Preference Page, you can check what kind of rules are you going to use it and also you can see the samples and description about each of rules.

![alt text](https://github.com/stkim123/kr.ac.jbnu.ssel.misrac/blob/master/ScreenShot2%20.PNG)
