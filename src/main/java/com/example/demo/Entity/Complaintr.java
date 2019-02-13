package com.example.demo.Entity;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Cloie Andrea on 23/11/2018.
 */
@Entity
@Table(name="complaintreply")
public class Complaintr {


    @GeneratedValue
    @Id
    private Long reply_id;

    private Long complaint_id;
    private String complaint_reply;
    private String date;
    private String time;
    private String agency;
    private Long userid;

    public Long getReply_id() {
        return reply_id;
    }

    public void setReply_id(Long reply_id) {
        this.reply_id = reply_id;
    }

    public Long getComplaint_id() {
        return complaint_id;
    }

    public void setComplaint_id(Long complaint_id) {
        this.complaint_id = complaint_id;
    }

    public String getComplaint_reply() {
        return complaint_reply;
    }

    public void setComplaint_reply(String complaint_reply) {
        this.complaint_reply = complaint_reply;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }
}
