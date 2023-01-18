package com.geektrust.family.model;

import java.util.ArrayList;
import java.util.List;

public class Member {

  String name;
  Gender gender;

  Member mother;
  Member father;

  Member spouse;

  List<Member> children;

  public String getName() {
    return name;
  }

  public Gender getGender() {
    return gender;
  }

  public Member getMother() {
    return mother;
  }

  public Member getFather() {
    return father;
  }

  public Member getSpouse() {
    return spouse;
  }

  public List<Member> getChildren() {
    return children;
  }

  public Member(String name, Gender gender, Member father, Member mother) {
    this.name = name;
    this.gender = gender;
    this.mother = mother;
    this.father = father;
    this.spouse = null;
    this.children = new ArrayList<Member>();
  }

  /**
   * Add child.
   *
   * @param child
   */
  public void addChild(Member child) {
    this.children.add(child);
  }

  public void setSpouse(Member spouse) {
    this.spouse = spouse;
  }

  /**
   * Return list of daughters or sons.
   *
   * @param gender
   * @return
   */
  public String findAllChildrenWithGender(Gender gender) {
    StringBuilder sb = new StringBuilder("");
    List<Member> children;
    if (this.gender == Gender.Male)
      children = this.spouse.children;
    else
      children = this.children;
    for (Member m : children) {
      if (m.gender == gender) {
        sb.append(m.name).append(" ");
      }
    }
    return sb.toString().trim();
  }

  /**
   * Return list of all Siblings.
   *
   * @return
   */
  public String findSiblings() {
    StringBuilder sb = new StringBuilder("");
    if (this.mother != null) {
      this.mother.children.stream()
          .filter(m -> !this.name.equals(m.name))
          .forEach(m -> sb.append(m.name).append(" "));
    }

    return sb.toString().trim();
  }

  /**
   * Return list of all Siblings; male or female.
   *
   * @param gender of the sibling
   * @return
   */
  public String findSiblingsWithGender(Gender gender) {

    StringBuilder sb = new StringBuilder("");

    if (this.mother != null) {
      for (Member m : this.mother.children) {
        if (!this.name.equals(m.name) && m.gender == gender) {
          sb.append(m.name).append(" ");
        }
      }
    }

    return sb.toString().trim();
  }


  /**
   * Return list of children - daughters or son , name other than personName.
   *
   * @param gender
   * @param personName
   * @return
   */
  public String findChildrenWithGender(Gender gender, String personName) {
    StringBuilder sb = new StringBuilder("");

    for (Member m : this.children) {
      if (!personName.equals(m.name) && m.gender == gender) {
        sb.append(m.name).append(" ");
      }
    }

    return sb.toString().trim();
  }

  public String findChildrenSpouseWithGender(Gender gender, String personName) {
    StringBuilder sb = new StringBuilder("");

    for (Member m : this.children) {
      if (!personName.equals(m.name) && m.spouse != null && m.spouse.gender == gender) {
        sb.append(m.spouse.name).append(" ");
      }
    }

    return sb.toString().trim();
  }


}
