//package com.example.demo.Entity;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//import javax.persistence.Entity;
//import javax.persistence.EntityListeners;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Table;
//import java.io.Serializable;
//
///**
// * Created by Cloie Andrea on 23/11/2018.
// */
//
//@Entity
//@Table(name="complaintreply")
//@EntityListeners(AuditingEntityListener.class)
//@JsonIgnoreProperties(value={"createdAt","updatedAt"},allowGetters=true)
//public class Reply implements Serializable {
//    @Id
//    @GeneratedValue
//    private Long reply_id;
//    private Long complaint_id;
//    private String complaint_reply;
//    private String date;
//    private String time;
//    private String agency;
//    private Long userid;
//
//    public Long getReply_id() {
//        return reply_id;
//    }
//
//    public void setReply_id(Long reply_id) {
//        this.reply_id = reply_id;
//    }
//
//    public Long getComplaint_id() {
//        return complaint_id;
//    }
//
//    public void setComplaint_id(Long complaint_id) {
//        this.complaint_id = complaint_id;
//    }
//
//    public String getComplaint_reply() {
//        return complaint_reply;
//    }
//
//    public void setComplaint_reply(String complaint_reply) {
//        this.complaint_reply = complaint_reply;
//    }
//
//    public String getDate() {
//        return date;
//    }
//
//    public void setDate(String date) {
//        this.date = date;
//    }
//
//    public String getTime() {
//        return time;
//    }
//
//    public void setTime(String time) {
//        this.time = time;
//    }
//
//    public String getAgency() {
//        return agency;
//    }
//
//    public void setAgency(String agency) {
//        this.agency = agency;
//    }
//
//    public Long getUserid() {
//        return userid;
//    }
//
//    public void setUserid(Long userid) {
//        this.userid = userid;
//    }
//}
