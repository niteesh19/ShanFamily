package com.geektrust.family.service;

import com.geektrust.family.util.Constants;
import com.geektrust.family.model.Gender;
import com.geektrust.family.model.Member;

import java.util.ArrayList;
import java.util.List;

public class Family {

	private static final String FEMALE = "Female";

	private Member familyHead;

	/**
	 * Add head of the family.
	 * 
	 * @param name   - name of the member
	 * @param gender - gender
	 */
	public void addFamilyHead(String name, String gender) {
		Gender g = (FEMALE.equals(gender)) ? Gender.Female : Gender.Male;
		this.familyHead = new Member(name, g, null, null);
	}

	/**
	 * Add spouse to a member if {@link Member} is not null and do not have spouse
	 * already.
	 * 
	 * @param memberName - member whose spouse to be added
	 * @param spouseName
	 * @param gender
	 */
	public void addSpouse(String memberName, String spouseName, String gender) {
		Member member = searchMember(familyHead, memberName);
		if (member != null && member.getSpouse() == null) {
			Gender g = (FEMALE.equals(gender)) ? Gender.Female : Gender.Male;
			Member sp = new Member(spouseName, g, null, null);
			sp.setSpouse(member);
			member.setSpouse(sp);
		}
	}

	/**
	 * Add child to a member iff {@link Member} is not null and member is female.
	 * 
	 * @param motherName - member whose child to be added
	 * @param childName
	 * @param gender
	 * @return
	 */
	public String addChildFamily(String motherName, String childName, String gender) {
		String result;
		Member member = searchMember(familyHead, motherName);
		if (member == null) {
			result = Constants.Message.PERSON_NOT_FOUND;
		} else if (childName == null || gender == null) {
			result = Constants.Message.CHILD_ADDITION_FAILED;
		} else if (member.getGender() == Gender.Female) {
			Gender g = (FEMALE.equals(gender)) ? Gender.Female : Gender.Male;
			Member child = new Member(childName, g, member.getSpouse(), member);
			member.addChild(child);
			result = Constants.Message.CHILD_ADDITION_SUCCEEDED;
		} else {
			result = Constants.Message.CHILD_ADDITION_FAILED;
		}
		return result;
	}

	/**
	 * Search a {@link Member} with name as person. Find member's name corresponding
	 * to given relationship
	 * 
	 * @param person
	 * @param relationship
	 * @return
	 */
	public String getRelationship(String person, String relationship) {

		String relations;
		Member member = searchMember(familyHead, person);
		if (member == null) {
			relations = Constants.Message.PERSON_NOT_FOUND;
		} else if (relationship == null) {
			relations = Constants.Message.PROVIDE_VALID_RELATION;
		} else {
			relations = getRelationship(member, relationship);
		}

		return relations;

	}

	/**
	 * Find members name corresponding to given relationship to given {@link Member}
	 * 
	 * @param member       : whose relatives to be found
	 * @param relationship : relation to be foind
	 * @return list of names of relatives
	 */
	private String getRelationship(Member member, String relationship) {
		String relations = "";
		switch (relationship) {

		case Constants.Relationship.DAUGHTER:
			relations = member.findAllChildrenWithGender(Gender.Female);
			break;

		case Constants.Relationship.SON:
			relations = member.findAllChildrenWithGender(Gender.Male);
			break;

		case Constants.Relationship.SIBLINGS:
			relations = member.findSiblings();
			break;

		case Constants.Relationship.SISTER_IN_LAW:
			relations = searchInLaws(member, Gender.Female);
			break;

		case Constants.Relationship.BROTHER_IN_LAW:
			relations = searchInLaws(member, Gender.Male);
			break;

		case Constants.Relationship.MATERNAL_AUNT:
			if (member.getMother() != null)
				relations = member.getMother().findSiblingsWithGender(Gender.Female);
			break;

		case Constants.Relationship.PATERNAL_AUNT:
			if (member.getFather() != null)
				relations = member.getFather().findSiblingsWithGender(Gender.Female);
			break;

		case Constants.Relationship.MATERNAL_UNCLE:
			if (member.getMother() != null)
				relations = member.getMother().findSiblingsWithGender(Gender.Male);
			break;

		case Constants.Relationship.PATERNAL_UNCLE:
			if (member.getFather() != null)
				relations = member.getFather().findSiblingsWithGender(Gender.Male);
			break;

		default:
			relations = Constants.Message.NOT_YET_IMPLEMENTED;
			break;
		}

		return ("".equals(relations)) ? Constants.Message.NONE : relations;

	}

	/**
	 * Search {@link Member} sister-in law or brother -in law.
	 *
	 * @param member
	 * @param gender
	 * @return
	 */
	private String searchInLaws(Member member, Gender gender) {
		String personName = member.getName();
		StringBuilder sb = new StringBuilder("");
		String res = "";

		// search spouse mother children
		if (member.getSpouse() != null && member.getSpouse().getMother() != null) {
			res = member.getSpouse().getMother().findChildrenWithGender(gender, member.getSpouse().getName());
		}
		sb.append(res);

		// search mother children spouse
		res = "";
		if (member.getMother() != null) {
			res = member.getMother().findChildrenSpouseWithGender(gender, personName);
		}
		sb.append(res);

		return sb.toString().trim();
	}

	/**
	 * Search {@link Member} object with name as memberName. Returns null in case
	 * not found.
	 * 
	 * @param head
	 * @param memberName
	 * @return {@link Member} object
	 */
	private Member searchMember(Member head, String memberName) {
		if (memberName == null || head == null) {
			return null;
		}

		Member member = null;
		if (memberName.equals(head.getName())) {
			return head;
		} else if (head.getSpouse() != null && memberName.equals(head.getSpouse().getName())) {
			return head.getSpouse();
		}

		List<Member> childlist = new ArrayList<>();
		if (head.getGender() == Gender.Female) {
			childlist = head.getChildren();
		} else if (head.getSpouse() != null) {
			childlist = head.getSpouse().getChildren();
		}

		for (Member m : childlist) {
			member = searchMember(m, memberName);
			if (member != null) {
				break;
			}
		}
		return member;
	}

}