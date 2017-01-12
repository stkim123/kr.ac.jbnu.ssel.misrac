package kr.ac.jbnu.ssel.misrac.rule;

import java.util.ArrayList;

import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

public class Rule08_2_req extends AbstractMisraCRule {

	private static ArrayList<String> list = new ArrayList<String>();
	
	public Rule08_2_req(IASTTranslationUnit ast) {
		super("Rule08_2_req", false, ast);
		shouldVisitDeclarations = true;
		shouldVisitDeclarators = true;
		shouldVisitComment = false;
	}

	@Override
	protected int visit(IASTSimpleDeclaration simpleDeclaration) {
		
//		System.out.println("test :: "+simpleDeclaration.getRawSignature()); //extern int16_t x;
//		System.out.println("Declarators :: "+simpleDeclaration.getDeclarators()[0].getRawSignature()); //x
//		System.out.println("DeclSpecifier :: "+simpleDeclaration.getDeclSpecifier().getRawSignature()); //extern int16_t
//		System.out.println("Children 0 :: "+simpleDeclaration.getChildren()[0].getRawSignature());
//		System.out.println("Children 1 :: "+simpleDeclaration.getChildren()[1].getRawSignature());
//		System.out.println("Children 2 :: "+simpleDeclaration.getChildren()[2].getRawSignature());
		
		/*
		String[] temp = simpleDeclaration.getRawSignature().split(" ");
		
		for(int i=0; i<temp.length; i++){
			if(!temp[i].equals("")){
				list.add(temp[i]);
			}
		}
		
		String string = "";
		
		for(int i=2; i<list.size(); i++){
			
			string = string + list.get(i);
			list.add(2, string);
		}
		
		string = "";
		
//		if(simpleDeclaration.getDeclarators()[0].getRawSignature().equals(list.get(2)))
		
		System.out.println("list : "+list);
//		System.out.println("test : "+simpleDeclaration.getDeclarators()[0].getRawSignature());
		
		list.clear();
		
		*/
		
//		String string = simpleDeclaration.getRawSignature();
//		String[] words = string.split(" ");
//		ArrayList<String> list = new ArrayList<String>();
//		
//		for (int i = 0; i < words.length; i++) {
//			if (!words[i].equals("")) {
//				list.add(words[i]);
//			}
//		}
//		System.out.println(list.get(1));
//		System.out.println(simpleDeclaration.getDeclarators()[0].getRawSignature());
//		if(list.get(1).equals(simpleDeclaration.getDeclarators()[0].getRawSignature())){
//			String message1 = MessageFactory.getInstance().getMessage(2050);
//			violationMsgs.add(
//					new ViolationMessage(this, getRuleID() + ":" + message1 + "--" + string, simpleDeclaration));
//			String message2 = MessageFactory.getInstance().getMessage(2051);
//			violationMsgs.add(
//					new ViolationMessage(this, getRuleID() + ":" + message2 + "--" + string, simpleDeclaration));
//			isViolated=true;
//		}
//		
//		list.clear();
		return super.visit(simpleDeclaration);
	}
}
