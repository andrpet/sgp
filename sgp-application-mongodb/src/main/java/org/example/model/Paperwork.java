package org.example.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Paperwork {

  @Id
  private String id;
  private String identifier;
  private String name;
  private String surname;
  private String taxCode;
  private Instant dateOfBirth;
  private String docPath;
  private List<PaperworkStatusInfo> paperworkStatusInfos = new ArrayList<>();

  public Paperwork() {
  }

  public Paperwork(String identifier, String name, String surname, String taxCode, Instant dateOfBirth, String docPath,
    PaperworkStatus paperworkStatus) {
    this.identifier = identifier;
    this.name = name;
    this.surname = surname;
    this.taxCode = taxCode;
    this.dateOfBirth = dateOfBirth;
    this.docPath = docPath;
    this.paperworkStatusInfos.add(new PaperworkStatusInfo(paperworkStatus));
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getTaxCode() {
    return taxCode;
  }

  public void setTaxCode(String taxCode) {
    this.taxCode = taxCode;
  }

  public Instant getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(Instant dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public String getDocPath() {
    return docPath;
  }

  public void setDocPath(String docPath) {
    this.docPath = docPath;
  }

  public List<PaperworkStatusInfo> getPaperworkStatusInfos() {
    return paperworkStatusInfos;
  }

  public void addPaperworkInfo(PaperworkStatus paperworkStatus) {
    this.paperworkStatusInfos.add(new PaperworkStatusInfo(paperworkStatus));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Paperwork paperwork = (Paperwork) o;
    return Objects.equals(id, paperwork.id) && Objects.equals(identifier, paperwork.identifier) && Objects.equals(name, paperwork.name) && Objects.equals(surname, paperwork.surname) && Objects.equals(taxCode, paperwork.taxCode) && Objects.equals(dateOfBirth, paperwork.dateOfBirth) && Objects.equals(docPath, paperwork.docPath) && Objects.equals(paperworkStatusInfos, paperwork.paperworkStatusInfos);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, identifier, name, surname, taxCode, dateOfBirth, docPath, paperworkStatusInfos);
  }

  @Override
  public String toString() {
    return "Paperwork{" +
            "id='" + id + '\'' +
            ", identifier='" + identifier + '\'' +
            ", name='" + name + '\'' +
            ", surname='" + surname + '\'' +
            ", taxCode='" + taxCode + '\'' +
            ", dateOfBirth=" + dateOfBirth +
            ", docPath='" + docPath + '\'' +
            ", paperworkStatusInfos=" + paperworkStatusInfos +
            '}';
  }
}
