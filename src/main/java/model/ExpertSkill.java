package model;

public class ExpertSkill {
    private int user_id;
    private int skill_id;
    private User user;
    private Skill skill;
    private String user_name;
    private String skill_name;

    public ExpertSkill() {
    }

    public ExpertSkill(int user_id, int skill_id, String user_name, String skill_name) {
        this.user_id = user_id;
        this.skill_id = skill_id;
        this.user_name = user_name;
        this.skill_name = skill_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getSkill_name() {
        return skill_name;
    }

    public void setSkill_name(String skill_name) {
        this.skill_name = skill_name;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getSkill_id() {
        return skill_id;
    }

    public void setSkill_id(int skill_id) {
        this.skill_id = skill_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }
}
