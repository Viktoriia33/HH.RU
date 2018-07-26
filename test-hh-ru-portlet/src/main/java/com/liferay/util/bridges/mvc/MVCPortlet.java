package com.liferay.util.bridges.mvc;

import javax.portlet.*;
import java.io.IOException;

public class MVCPortlet extends GenericPortlet {

    private static String VIEW_JSP = "/view.jsp";

    @Override
    public void init(PortletConfig portletConfig) throws PortletException {
        super.init(portletConfig);
    }

    @Override
    public void doView(RenderRequest request, RenderResponse response)
            throws PortletException, IOException {

        response.setContentType(request.getResponseContentType());
        PortletContext context = getPortletConfig().getPortletContext();
        context.getRequestDispatcher(VIEW_JSP).include(request, response);
    }

    @Override
    public void processAction(ActionRequest request, ActionResponse response)
            throws PortletException, java.io.IOException {

        response.setRenderParameter("textBox", "textBox");
    }
}