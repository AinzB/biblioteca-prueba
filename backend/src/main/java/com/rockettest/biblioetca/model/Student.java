package com.rockettest.biblioetca.model;

import java.util.Date;

public class Student {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STUDENTS.ID
     *
     * @mbg.generated Wed May 28 21:17:34 CST 2025
     */
    private Short id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STUDENTS.STUDENT_CODE
     *
     * @mbg.generated Wed May 28 21:17:34 CST 2025
     */
    private String studentCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STUDENTS.FIRST_NAME
     *
     * @mbg.generated Wed May 28 21:17:34 CST 2025
     */
    private String firstName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STUDENTS.LAST_NAME
     *
     * @mbg.generated Wed May 28 21:17:34 CST 2025
     */
    private String lastName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STUDENTS.EMAIL
     *
     * @mbg.generated Wed May 28 21:17:34 CST 2025
     */
    private String email;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STUDENTS.PHONE
     *
     * @mbg.generated Wed May 28 21:17:34 CST 2025
     */
    private String phone;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STUDENTS.STATUS
     *
     * @mbg.generated Wed May 28 21:17:34 CST 2025
     */
    private String status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STUDENTS.CREATED_AT
     *
     * @mbg.generated Wed May 28 21:17:34 CST 2025
     */
    private Date createdAt;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STUDENTS.ID
     *
     * @return the value of STUDENTS.ID
     *
     * @mbg.generated Wed May 28 21:17:34 CST 2025
     */
    public Short getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STUDENTS.ID
     *
     * @param id the value for STUDENTS.ID
     *
     * @mbg.generated Wed May 28 21:17:34 CST 2025
     */
    public void setId(Short id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STUDENTS.STUDENT_CODE
     *
     * @return the value of STUDENTS.STUDENT_CODE
     *
     * @mbg.generated Wed May 28 21:17:34 CST 2025
     */
    public String getStudentCode() {
        return studentCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STUDENTS.STUDENT_CODE
     *
     * @param studentCode the value for STUDENTS.STUDENT_CODE
     *
     * @mbg.generated Wed May 28 21:17:34 CST 2025
     */
    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STUDENTS.FIRST_NAME
     *
     * @return the value of STUDENTS.FIRST_NAME
     *
     * @mbg.generated Wed May 28 21:17:34 CST 2025
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STUDENTS.FIRST_NAME
     *
     * @param firstName the value for STUDENTS.FIRST_NAME
     *
     * @mbg.generated Wed May 28 21:17:34 CST 2025
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STUDENTS.LAST_NAME
     *
     * @return the value of STUDENTS.LAST_NAME
     *
     * @mbg.generated Wed May 28 21:17:34 CST 2025
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STUDENTS.LAST_NAME
     *
     * @param lastName the value for STUDENTS.LAST_NAME
     *
     * @mbg.generated Wed May 28 21:17:34 CST 2025
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STUDENTS.EMAIL
     *
     * @return the value of STUDENTS.EMAIL
     *
     * @mbg.generated Wed May 28 21:17:34 CST 2025
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STUDENTS.EMAIL
     *
     * @param email the value for STUDENTS.EMAIL
     *
     * @mbg.generated Wed May 28 21:17:34 CST 2025
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STUDENTS.PHONE
     *
     * @return the value of STUDENTS.PHONE
     *
     * @mbg.generated Wed May 28 21:17:34 CST 2025
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STUDENTS.PHONE
     *
     * @param phone the value for STUDENTS.PHONE
     *
     * @mbg.generated Wed May 28 21:17:34 CST 2025
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STUDENTS.STATUS
     *
     * @return the value of STUDENTS.STATUS
     *
     * @mbg.generated Wed May 28 21:17:34 CST 2025
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STUDENTS.STATUS
     *
     * @param status the value for STUDENTS.STATUS
     *
     * @mbg.generated Wed May 28 21:17:34 CST 2025
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STUDENTS.CREATED_AT
     *
     * @return the value of STUDENTS.CREATED_AT
     *
     * @mbg.generated Wed May 28 21:17:34 CST 2025
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STUDENTS.CREATED_AT
     *
     * @param createdAt the value for STUDENTS.CREATED_AT
     *
     * @mbg.generated Wed May 28 21:17:34 CST 2025
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}