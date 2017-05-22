# OpenMRC: Open Source MISRA-C Rule Checker based on Eclipse CDT(C/C++ Development Tooling)
OpenMRC is an open source MISRA (Motor Industry Software Reliability Association)-C rule checker, developed as an Eclipse CDT plugin. 
The following is an overall steps to use OpenMRC. 

![alt text](https://github.com/stkim123/kr.ac.jbnu.ssel.misrac/blob/master/ScreenShot1.PNG)

OpenMRC has a two-step usage:
- At the first step, OpenMRC obtains the C source code for Vehicle software, and then it builds the AST (Abstract Syntax Tree) by using the C-AST parser bundled in Eclipse CDT.
- At the second step, OpenMRC traverses all of the AST elements in order to check violations of MISRA-C: 2004 Guideline Rules, and produces violation messages. The messages are shown in the Eclipse CDT views (see the TableViewer page below).

Based on the violation messages, a vehicle software developer continuously updates their source to achieve the vehicle software's functional safety. 

## TableViewer for Showing Violation Messages
- In TableViewer, OpenMRC shows which rules has been violated in the C source code. When the developer selects one of the violation messages in the viewer, OpenMRC automatically hightlights the violated part in the code as shown in the figure below: 

![alt text](https://github.com/stkim123/kr.ac.jbnu.ssel.misrac/blob/master/ScreenShot3.PNG)

## Preference Setting for (De)Selecting MISRA-C Rules
- OpenMRC provides the Preference page that can selectively check the MISRA-C Rules. In the Preference page, all rules are categorized and a developer can select/deselect the rules. 

![alt text](https://github.com/stkim123/kr.ac.jbnu.ssel.misrac/blob/master/ScreenShot2%20.PNG)

# Developers and Sponsor(Supporter)
## Developers
 - Tae-young Kim   (Chonbuk National University, Master Student, rlaxodud1200@gmail.com)
 - Seunghyeon Kang (Chonbuk National University, Undergraduate Student, cat1817@jbnu.ac.kr)
 - Sangjin Nam     (Chonbuk National University, Undergraduate Student, potter930@jbnu.ac.kr)
 - Suntae Kim      (Chonbuk National University, Advisor Professor, stkim@jbnu.ac.kr) - please contact here !
 
## Sponsor(Supporter)
This research was supported by the MSIP(Ministry of Science, ICT and Future Planning), Korea, under the ITRC(Information Technology Research Center) support program (IITP-2017-2016-0-00313) supervised by the IITP(Ins! titute for Information & communications Technology Promotion)
