package com.structurizr.onpremises.web.workspace;

import com.structurizr.onpremises.component.workspace.WorkspaceComponentException;
import com.structurizr.onpremises.component.workspace.WorkspaceMetaData;
import com.structurizr.onpremises.web.AbstractController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UnshareWorkspaceController extends AbstractController {

    private static final Log log = LogFactory.getLog(UnshareWorkspaceController.class);

    @RequestMapping(value="/workspace/{workspaceId}/unshare", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public String unshareWorkspace(@PathVariable("workspaceId")long workspaceId, ModelMap model) {
        try {
            WorkspaceMetaData workspace = workspaceComponent.getWorkspaceMetaData(workspaceId);
            if (workspace != null) {
                if (workspace.isOpen() || workspace.isWriteUser(getUser())) {
                    workspaceComponent.unshareWorkspace(workspaceId);
                }
            } else {
                return show404Page(model);
            }
        } catch (WorkspaceComponentException e) {
            log.error(e);
        }

        return "redirect:/workspace/" + workspaceId + "/settings";
    }

}