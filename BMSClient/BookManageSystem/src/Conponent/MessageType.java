package Conponent;

public class MessageType {
    private static MessageType ourInstance = new MessageType();

    public static MessageType getInstance() {
        return ourInstance;
    }

    private MessageType() {
    }

    public static final int regReq = 0, regAdmit = 1, regDeny = 2,
    fndReq = 3, fndCodeReq = 4, fndAdmit = 5, fndDeny = 6,
    userLoginReq = 7, adminLoginReq = 8, userLoginAdmit = 9, adminLoginAdmit = 10, loginDeny = 11,
    error = 12, altPwd = 13, altDeny = 14, altAdmit = 15;
}
