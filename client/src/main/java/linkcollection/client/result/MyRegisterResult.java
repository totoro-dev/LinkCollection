package linkcollection.client.result;

import linkcollection.client.ui.frame.CheckMailFrame;
import linkcollection.client.ui.frame.RegisterFrame;
import linkcollection.client.ui.widgets.Toast;
import linkcollection.common.interfaces.RegisterResult;

public class MyRegisterResult implements RegisterResult {
    @Override
    public void registerError(String s) {
        Toast.makeText(RegisterFrame.context, s).show(Toast.LONG);
    }

    @Override
    public void registerSuccess() {
        new CheckMailFrame(RegisterFrame.context);
    }
}
