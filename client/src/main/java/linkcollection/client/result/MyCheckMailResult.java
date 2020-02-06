package linkcollection.client.result;

import linkcollection.client.ui.frame.LoginFrame;
import linkcollection.client.ui.frame.MainFrame;
import linkcollection.client.ui.widgets.Toast;
import linkcollection.common.interfaces.CheckMailResult;

public class MyCheckMailResult implements CheckMailResult {
    @Override
    public void checkEmailError(String s) {

    }

    @Override
    public void checkEmailSuccess() {
        new LoginFrame(MainFrame.context, true);
        Toast.makeText(LoginFrame.context, "请登录").show(Toast.LONG);
    }
}
