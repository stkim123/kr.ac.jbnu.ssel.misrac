package kr.ac.jbnu.ssel.misrac.ui.preference;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Rule {

	@Element(name = "description", required = false)
	protected String description;
	@Element(name = "RuleName", required = false)
	protected String ruleName;
	@Element(name = "ClassName", required = false)
	protected String className;
	@Element(name = "SourceCode", required = false)
	protected String sourceCode;
	@Attribute(name = "shouldCheck", required = false)
	protected Boolean shouldCheck;
	@Attribute(name = "type", required = false)
	protected String type;
	@Attribute(name = "majorNum", required = false)
	protected String majorNum;
	@Attribute(name = "minerNum", required = false)
	protected String minerNum;
	@Attribute(name = "category", required = false)
	protected String category;

	public String getDescription() {
		return description;
	}

	public void setDescription(String value) {
		this.description = value;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String value) {
		this.ruleName = value;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String value) {
		this.className = value;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String value) {
		this.sourceCode = value;
	}

	public Boolean isShouldCheck() {
		return shouldCheck;
	}

	public void setShouldCheck(Boolean value) {
		this.shouldCheck = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String value) {
		this.type = value;
	}

	public String getMajorNum() {
		return majorNum;
	}

	public void setMajorNum(String value) {
		this.majorNum = value;
	}

	public String getMinerNum() {
		return minerNum;
	}

	public void setMinerNum(String value) {
		this.minerNum = value;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String value) {
		this.category = value;
	}

}
