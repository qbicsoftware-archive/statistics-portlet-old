package life.qbic.presenter.utils;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;

public final class CustomNotification {

    public static void error(String caption, String description){
        Notification notification = new Notification(caption,
                                                    description,
                                                    Notification.Type.ERROR_MESSAGE,
                                                    true);
        notification.setDelayMsec(-1); //infinity
        notification.show(Page.getCurrent());
    }
}
