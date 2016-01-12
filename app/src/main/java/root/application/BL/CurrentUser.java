package root.application.BL;

/**
 * Created by root on 28.12.15.
 */
public class CurrentUser {

    private static CurrentUser insctance;

    public static CurrentUser getInsctance() {
        if(insctance==null)
            insctance=new CurrentUser();
        return insctance;
    }
    private String login;
    private String mindMapId;
    CurrentUser(){}

    public void init(String login)
    {
        this.login=login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMindMapId() {
        return mindMapId;
    }

    public void setMindMapId(String mindMapId) {
        this.mindMapId = mindMapId;
    }
}
